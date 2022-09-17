package com.app.kerja_mudah.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.app.kerja_mudah.databinding.ActivityAllChatRoomBinding
import com.app.kerja_mudah.di.component.ChatComponent
import com.app.kerja_mudah.ui.chat.adapter.ChatRoomAdapter
import com.app.kerja_mudah.ui.chat.viewmodel.AllChatRoomViewModel
import com.app.kerja_mudah.ui.chat.viewmodel.ChatRoomViewModel
import javax.inject.Inject

class AllChatRoomActivity : BaseActivity<ActivityAllChatRoomBinding>(ActivityAllChatRoomBinding::inflate) {

    @Inject
    lateinit var viewModel:AllChatRoomViewModel

    override fun initSetup() {
        initAdapter()
        initObserver()

        viewModel.getAllRoomChat()
    }

    private lateinit var adapter:ChatRoomAdapter
    private var list:ArrayList<ChatRoomResponse> = arrayListOf()
    private fun initAdapter() {
//        adapter = ChatRoomAdapter(list)
//        adapter.setCallBack(object : ChatRoomAdapter.CallBack{
//            override fun onClicked(chatRoom: ChatRoomResponse?) {
//                if (chatRoom != null && chatRoom.otherUser?.id != null){
//                    val intent = Intent(this@AllChatRoomActivity, ChatRoomActivity::class.java)
//                    intent.putExtra(ChatRoomActivity.TO, chatRoom.otherUser?.id)
//                    startActivity(intent)
//                }else{
//                    showSnackBar("Oops... Something happened, please try again later")
//                }
//            }
//        })
//        binding?.rvChatRoom?.adapter = adapter
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.chatRoomState == BaseState.LOADING){
            }else if (it.chatRoomState == BaseState.SUCCESS){
                list.clear()
                list.addAll(it.roomChatList?: arrayListOf())
//                adapter.notifyDataSetChanged()
                binding?.llShimmer?.visibility = View.GONE
                binding?.rvChatRoom?.visibility = View.VISIBLE
            }else if (it.chatRoomState == BaseState.FAILED){
                showSnackBar(it.errorChatRoomState?:"")
            }
        }
    }

    private lateinit var component: ChatComponent
    override fun inject() {
        component = appComponent.chatComponent().create()
        component.inject(this)
    }

}