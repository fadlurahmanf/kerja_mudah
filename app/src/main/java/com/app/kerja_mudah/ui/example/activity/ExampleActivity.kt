package com.app.kerja_mudah.ui.example.activity

import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.repository.example.ExampleRepository
import com.app.kerja_mudah.data.room.example.ExampleEntity
import com.app.kerja_mudah.data.room.example.ExampleRoomRepository
import com.app.kerja_mudah.databinding.ActivityExampleBinding
import com.app.kerja_mudah.di.component.ExampleComponent
import com.app.kerja_mudah.ui.example.viewmodel.ExampleViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class ExampleActivity : BaseActivity<ActivityExampleBinding>(ActivityExampleBinding::inflate) {
    lateinit var component:ExampleComponent

    @Inject
    lateinit var viewModel:ExampleViewModel

    @Inject
    lateinit var exampleRepository: ExampleRepository

    @Inject
    lateinit var exampleRoomRepository: ExampleRoomRepository

    override fun initSetup() {
        initObserver()

        binding?.button1?.setOnClickListener {
            viewModel.getTestimonialState()
        }

        binding?.button2?.setOnClickListener {
            GlobalScope.launch {
                var data = exampleRoomRepository.getAll()
                println("MASUK ${data?.size}")
                data?.forEach {
                    println("MASUK $it")
                }
            }
        }

        binding?.button3?.setOnClickListener {
            GlobalScope.launch {
                exampleRoomRepository.insertData(ExampleEntity(text = "${Random.nextInt(9999)}"))
            }
        }
    }


    private fun initObserver() {
//        viewModel.testimonialLoading.observe(this, { loading ->
//            if (loading){
//                showLoadingDialog()
//            }else{
//                dismissDialog()
//            }
//        })
//
//        viewModel.testimonial.observe(this, {
//            Snackbar.make(binding!!.root, "RESULT : ${it.message}", Snackbar.LENGTH_LONG).show()
//        })
//
//        viewModel.testimonialError.observeOnce(this, {
//            Snackbar.make(binding!!.root, "RESULT : ${it?:""}", Snackbar.LENGTH_LONG).show()
//        })

        viewModel.exampleState.observe(this) {
            when (it.BaseState) {
                BaseState.LOADING -> {
                    showLoadingDialog()
                }
                BaseState.SUCCESS -> {
                    dismissLoadingDialog()
                    Snackbar.make(
                        binding!!.root,
                        "RESULT : ${it.data?.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                BaseState.FAILED -> {
                    dismissLoadingDialog()
                    Snackbar.make(binding!!.root, "RESULT : ${it.error}", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


    override fun inject() {
        component = (applicationContext as BaseApp).applicationComponent.exampleComponent().create()
        component.inject(this)
    }

}
