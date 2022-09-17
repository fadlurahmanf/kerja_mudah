package com.app.kerja_mudah.ui.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.*
import com.app.kerja_mudah.core.receiver.CoreReceiver
import com.app.kerja_mudah.data.mapper.ChatMapper
import com.app.kerja_mudah.data.model.chat.ChatModel
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.databinding.ActivityChatRoomBinding
import com.app.kerja_mudah.di.component.ChatComponent
import com.app.kerja_mudah.ui.chat.adapter.ChatAdapter
import com.app.kerja_mudah.ui.chat.viewmodel.ChatRoomViewModel
import com.app.kerja_mudah.ui.core.CameraActivity
import com.app.kerja_mudah.ui.core.GalleryActivity
import com.app.kerja_mudah.ui.core.adapter.PreviewSelectedMediaAdapter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(ActivityChatRoomBinding::inflate) {

    private var to:Int = -1

    companion object{
        val TAG = ChatRoomActivity::class.java.simpleName
        const val TO = "TO"
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    override fun initSetup() {
        supportActionBar?.hide()
        to = intent.getIntExtra(TO, -1)
        Log.i(TAG, "TO: $to")

        if (to == -1){
            showMissingParameterDialog()
            return;
        }

        initAdapter()
        initAction()
        initData()
        initObserver()
    }

    private val chatReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.getStringExtra(CoreReceiver.NEW_CHAT) != null){
                if (to != -1){
                    viewModel.getDetailChatRoom(to)
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        registerReceiver(chatReceiver, IntentFilter(CoreReceiver.CORE_RECEIVER))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(chatReceiver)
    }

    private fun showMissingParameterDialog(){
        showOkDialog(
            title = "Oops..",
            content = "Seems there is problem in fetching data, please try again later!",
            cancelable = false
        ){
            dismissOkDialog()
            finish()
        }
    }

    @Inject
    lateinit var chatMapper: ChatMapper

    private var uniqueRoomId:String = ""

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.chatRoomState == BaseState.SUCCESS){
                uniqueRoomId = it.chatRoom?.uniqueRoomId ?: ""
                chats.clear()
                chats.addAll(chatMapper.fromListChatResponseToChatModel(it.chatRoom?.chats?: arrayListOf()))
                chatAdapter.setListChat(chats)
            }else if (it.chatRoomState == BaseState.FAILED){
                showSnackBar(it.errorChatRoom?:"")
            }

            if (it.sendChatState == BaseState.SUCCESS){
                viewModel.getDetailChatRoom(opponentId = to)
            } else if (it.sendChatState == BaseState.FAILED){
                it.chatError?.let { chat ->
                    val chatModel = ChatModel(chat = chat.apply {
                        status = "failed"
                    })
                    chatAdapter.refreshDataByIdLocal(chatModel)
                }
            } else if (it.sendChatState == BaseState.IDLE){

            }
        }
    }

    private fun initData() {
        Log.d(TAG, "initData: $to | ${authRepository.myProfile?.id}")
        if (to != -1 && authRepository.myProfile?.id != null){
            viewModel.getDetailChatRoom(to)
        }
    }

    private var tempText:String? = ""
    private var tempImagesOrVideos:ArrayList<String>? = arrayListOf()
    private var tempChat:ChatResponse ?= null

    @Inject
    lateinit var viewModel:ChatRoomViewModel

    @Inject
    lateinit var authRepository: AuthRepository

    private var chatSelected:ArrayList<Int> = arrayListOf<Int>()

    private lateinit var adapterMediaSelected:PreviewSelectedMediaAdapter
    private var listFilePathSelected:ArrayList<String> = arrayListOf()

    private lateinit var chatAdapter:ChatAdapter
    private var chats:ArrayList<ChatModel> = arrayListOf()

    private fun initAdapter() {
        chatAdapter = ChatAdapter()
        chatAdapter.setCallBack(chatCallBack)
        chatAdapter.setMyUserId(authRepository.myProfile?.id?:-1)
        chatAdapter.setListChat(chats)
        binding?.rvChat?.adapter = chatAdapter
    }

    private val chatCallBack = object : ChatAdapter.CallBack{
        override fun onClicked(chat: ChatModel) {
            if (chat.chat?.status == "failed"){
                resendChat(chat)
            }
        }
    }

    private fun initAction() {
        binding?.ivClose?.setOnClickListener {
        }

        binding?.ivSend?.setOnClickListener {
            hideKeyboard(currentFocus?: View(this))
            binding?.etChat?.clearFocus()
            sendChat()
        }

        binding?.ivAttachhment?.setOnClickListener {
            externalStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun resendChat(chat:ChatModel){
        chat.chat?.apply {
            status = "loading"
        }.let {
            chatAdapter.refreshDataByIdLocal(chat)
        }
    }

    private fun sendChat(){
        if (!binding?.etChat?.text.isNullOrEmpty() && authRepository.myProfile?.id != null && to != -1) {
            val content = binding?.etChat?.text?.toString() ?: ""
            val chat = ChatResponse(
                status = "loading",
                idLocal = System.currentTimeMillis(),
                uniqueRoomId = uniqueRoomId,
                sendBy = ChatResponse.SendBy(
                    id = authRepository.myProfile?.id,
                    fullName = authRepository.myProfile?.email,
                    photo = authRepository.myProfile?.photo
                ),
                chat = content,
                createdAt = Calendar.getInstance().time.formatDate5(),
                updatedAt = Calendar.getInstance().time.formatDate5(),
            )
            val chatModel = ChatModel(chat = chat)
            chats.add(chatModel)
            val newList = chatMapper.refreshListChatModel(chats)
            chatAdapter.setListChat(newList)
            binding?.etChat?.text?.clear()
            viewModel.sendChat(to = to, chatResponse = chat)
        }else{
            showSnackBar("Oops. Seems there is missing variable. Try to relogin")
        }
    }

    private val externalStoragePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(this, GalleryActivity::class.java)
                intent.putExtra(GalleryActivity.IS_MULTIPLE_SELECT, true)
                galleryActivityLauncher.launch(intent)
            }else{
                showOkDialog("Request Permission", content = "kerjamudah. need your permission to access photo/video in your gallery")
            }
        }else{
            showOkDialog("Request Permission", content = "kerjamudah. need your permission to access photo/video in your gallery")
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                audioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
            }else{
                showOkDialog("Request Permission", "kerjamudah. need your permission to access camera")
            }
        }else{
            showOkDialog("Request Permission", "kerjamudah. need your permission to access camera")
        }
    }

    private val audioPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(this, CameraActivity::class.java)
                cameraActivityLauncher.launch(intent)
            }else{
                showOkDialog("Request Permission", "kerjamudah. need your permission to record audio")
            }
        }else{
            showOkDialog("Request Permission", "kerjamudah. need your permission to record audio")
        }
    }

    private val cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val path = it.data?.getStringExtra(CameraActivity.PATH_RESULT)
            if (path != null){
                listFilePathSelected.add(path)
                adapterMediaSelected.notifyDataSetChanged()
                if (listFilePathSelected.isEmpty()){
                    binding?.rlPreviewSelectedMedia?.visibility = View.GONE
                }else{
                    binding?.rlPreviewSelectedMedia?.visibility = View.VISIBLE
                }
            }
        }
    }

    private val galleryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            if (it.data?.getStringExtra(GalleryActivity.ACTION) == "camera"){
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }else if (it.data?.getStringExtra(GalleryActivity.ACTION) == "gallery"){
                val listPath = it.data?.getStringArrayListExtra(GalleryActivity.PATH_RESULT)
                listFilePathSelected.addAll(listPath?: arrayListOf())
                adapterMediaSelected.notifyDataSetChanged()
                if (listFilePathSelected.isEmpty()){
                    binding?.rlPreviewSelectedMedia?.visibility = View.GONE
                }else{
                    binding?.rlPreviewSelectedMedia?.visibility = View.VISIBLE
                }
            }
        }
    }

    private lateinit var component: ChatComponent
    override fun inject() {
        component = appComponent.chatComponent().create()
        component.inject(this)
    }

    override fun onDestroy() {
        viewModel.disconnectWebSocket()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if (state.first() == ChatAdapter.IDLE){
//            super.onBackPressed()
//        }else{
//            state.clear()
//            state.add(ChatAdapter.IDLE)
//            chatDateAdapter.notifyDataSetChanged()
//        }
    }

}