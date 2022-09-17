package com.app.kerja_mudah.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.di.component.ApplicationComponent

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB:ViewBinding>(
    var inflate:InflateFragment<VB>
):Fragment() {

    private lateinit var _appComponent:ApplicationComponent
    val appComponent get() = _appComponent

    private var _binding:VB ?= null
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        initAppComponent()
        inject()
        super.onCreate(savedInstanceState)
    }

    private fun initAppComponent() {
        _appComponent = (activity?.applicationContext as BaseApp).applicationComponent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(savedInstanceState)
    }

    abstract fun setup(savedInstanceState: Bundle?)

    abstract fun inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }
}