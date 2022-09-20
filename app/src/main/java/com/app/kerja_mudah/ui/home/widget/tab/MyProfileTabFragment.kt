package com.app.kerja_mudah.ui.home.widget.tab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.BuildConfig
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseFragment
import com.app.kerja_mudah.core.receiver.CoreReceiver
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.databinding.FragmentMyProfileTabBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.core.FaqActivity
import com.app.kerja_mudah.ui.auth.LoginActivity
import com.app.kerja_mudah.ui.chat.AllChatRoomActivity
import com.app.kerja_mudah.ui.core.AboutActivity
import com.app.kerja_mudah.ui.core.LegalActivity
import com.app.kerja_mudah.ui.core.dialog.AppVersionDialog
import com.app.kerja_mudah.ui.freelancer.SelectFreelancerCategoryActivity
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.service.ServiceOrderActivity
import com.bumptech.glide.Glide
import javax.inject.Inject

class MyProfileTabFragment : BaseFragment<FragmentMyProfileTabBinding>(FragmentMyProfileTabBinding::inflate) {
    private lateinit var component: HomeComponent

    override fun setup(savedInstanceState: Bundle?) {
        binding?.tvVersionApp?.text = BuildConfig.VERSION_NAME
        initView()
        initAction()
    }

    private var receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val totalUnread = intent?.extras?.getInt(CoreReceiver.TOTAL_UNREAD_ORDER_SERVICE, 0)
            if (totalUnread?:0 > 0){
                binding?.tvTotalUnreadOrderService?.visibility = View.VISIBLE
            }else{
                binding?.tvTotalUnreadOrderService?.visibility = View.GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(receiver, IntentFilter(CoreReceiver.CORE_RECEIVER))
    }

    private fun initAction() {
        binding?.tvLogout?.setOnClickListener {
            (requireActivity() as HomeActivity).showConfirmDialog(
                title = "Logout?",
                content = "Are you sure you want to logout?",
                negativeText = "Yes, Logout",
                positiveText = "Cancel"
            )
//            authRepository.accessToken = null
//            authRepository.myProfile = null
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            requireActivity().finish()
        }
        binding?.llChat?.setOnClickListener {
            val intent = Intent(requireContext(), AllChatRoomActivity::class.java)
            startActivity(intent)
        }

        binding?.llRegisterAsFreelancer?.setOnClickListener {
            val intent = Intent(requireContext(), SelectFreelancerCategoryActivity::class.java)
            startActivity(intent)
        }

        binding?.llServiceOrder?.setOnClickListener {
            val intent = Intent(requireContext(), ServiceOrderActivity::class.java)
            startActivity(intent)
        }

        binding?.llTermsCondition?.setOnClickListener {
            val intent = Intent(requireContext(), LegalActivity::class.java)
            intent.apply {
                putExtra(LegalActivity.ID_LEGAL, 1)
            }
            requireContext().startActivity(intent)
        }

        binding?.llPrivacyPolicy?.setOnClickListener {
            val intent = Intent(requireContext(), LegalActivity::class.java)
            intent.apply {
                putExtra(LegalActivity.ID_LEGAL, 2)
            }
            requireContext().startActivity(intent)
        }

        binding?.llVersion?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(AppVersionDialog.FORCED, false)
            bundle.putBoolean(AppVersionDialog.UPDATE, true)
            bundle.putString(AppVersionDialog.NEW_VERSION, "1.0.1")
            appVersionDialog = AppVersionDialog()
            appVersionDialog?.setCallBack(appVersionCallBack)
            appVersionDialog?.arguments = bundle
            appVersionDialog?.show(requireActivity().supportFragmentManager, AppVersionDialog::class.java.simpleName)
        }

        binding?.llFaq?.setOnClickListener {
            val intent = Intent(requireContext(), FaqActivity::class.java)
            startActivity(intent)
        }

        binding?.llAboutKm?.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private var appVersionCallBack = object : AppVersionDialog.CallBack{
        override fun onLaterClick() {
            appVersionDialog?.dismiss()
        }

        override fun onUpdateClick() {
            appVersionDialog?.dismiss()
        }
    }

    private var appVersionDialog:AppVersionDialog ?= null

    @Inject
    lateinit var authRepository:AuthRepository

    private fun initView() {
        Glide.with(binding!!.ivMyProfilePicture)
            .load(authRepository.myProfile?.photo?:"")
            .error(ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_person))
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.light_grey_solid))
            .centerCrop()
            .into(binding!!.ivMyProfilePicture)
        binding?.tvFullName?.text = authRepository.myProfile?.email

        if ((authRepository.totalUnreadOrderService?:0) > 0){
            binding?.tvTotalUnreadOrderService?.visibility = View.VISIBLE
        }else{
            binding?.tvTotalUnreadOrderService?.visibility = View.GONE
        }
    }

    override fun inject() {
        component = appComponent.homeComponent().create()
        component.inject(this)
    }

    private fun testBranch(){
        // TES BRANCH Main 19:35
        // TES BRANCH Main 19:35
        // TES BRANCH Main 19:35
    }
}