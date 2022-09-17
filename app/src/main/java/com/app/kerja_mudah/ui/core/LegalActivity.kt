package com.app.kerja_mudah.ui.core

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.databinding.ActivityLegalBinding
import com.app.kerja_mudah.di.component.CoreComponent
import com.app.kerja_mudah.ui.core.viewmodel.LegalViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LegalActivity : BaseActivity<ActivityLegalBinding>(ActivityLegalBinding::inflate) {
    companion object{
        const val ID_LEGAL = "ID_LEGAL"
    }

    private var idLegal:Int = -1

    override fun initSetup() {
        idLegal = intent.getIntExtra(ID_LEGAL, -1)
        binding?.llLoading?.visibility = View.GONE
        binding?.tvHtml?.visibility = View.GONE
        binding?.llError?.visibility = View.VISIBLE
        if (idLegal == -1){
            showSnackBar(message = "ID Legal is required", Snackbar.LENGTH_LONG)
            return
        }

        initObserver()
        initAction()
        viewModel.getLegalById(idLegal)
    }

    private fun initAction() {
        binding?.llError?.setOnClickListener {
            viewModel.getLegalById(idLegal)
        }
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.state == BaseState.SUCCESS){
                binding?.llLoading?.visibility = View.GONE
                binding?.tvHtml?.visibility = View.VISIBLE
                binding?.llError?.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding?.tvHtml?.text = Html.fromHtml(it.htmlText?:"", Html.FROM_HTML_MODE_COMPACT)
                }else{
                    binding?.tvHtml?.text = Html.fromHtml(it.htmlText?:"")
                }
            }else if (it.state == BaseState.LOADING){
                binding?.llLoading?.visibility = View.VISIBLE
                binding?.tvHtml?.visibility = View.GONE
                binding?.llError?.visibility = View.GONE
            }else if (it.state == BaseState.FAILED){
                binding?.llLoading?.visibility = View.GONE
                binding?.tvHtml?.visibility = View.GONE
                binding?.llError?.visibility = View.VISIBLE
            }
        }
    }

    @Inject
    lateinit var viewModel:LegalViewModel

    private lateinit var component:CoreComponent
    override fun inject() {
        component = appComponent.coreComponent().create()
        component.inject(this)
    }

}