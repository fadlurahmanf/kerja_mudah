package com.app.kerja_mudah.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.di.component.ApplicationComponent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB:ViewBinding>(
    var inflate:InflateFragment<VB>
):BottomSheetDialogFragment() {

    private lateinit var _appComponent:ApplicationComponent
    val appComponent get() = _appComponent

    private var _binding:VB ?= null
    val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _appComponent = (requireActivity().applicationContext as BaseApp).applicationComponent
        inject()
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun inject()

    abstract fun setup()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }
}