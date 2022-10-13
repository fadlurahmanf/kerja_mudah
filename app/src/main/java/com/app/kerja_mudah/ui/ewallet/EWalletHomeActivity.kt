package com.app.kerja_mudah.ui.ewallet

import android.content.Intent
import android.provider.Settings
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.utilities.PermissionUtilities
import com.app.kerja_mudah.data.response.ewallet.IncomeOutcomeResponse
import com.app.kerja_mudah.databinding.ActivityEwalletHomeBinding

class EWalletHomeActivity : BaseActivity<ActivityEwalletHomeBinding>(ActivityEwalletHomeBinding::inflate) {

    override fun initSetup() {
        initAction()
        initAdapter()
    }

    private lateinit var adapter: IncomeOutcomeAdapter
    private var list:ArrayList<IncomeOutcomeResponse> = arrayListOf(
        IncomeOutcomeResponse(
            id = 1,
            type = "income",
            total = 10000,
            note = "Transfer"
        ),
        IncomeOutcomeResponse(
            type = "outcome",
            total = 50000,
            note = "Tes 2 untuk beli bensin"

        ),
        IncomeOutcomeResponse(
            id = 1,
            type = "income",
            total = 10000,
            note = "Transfer"
        ),
        IncomeOutcomeResponse(
            type = "outcome",
            total = 50000,
            note = "Tes 2 untuk beli bensin"

        ),
        IncomeOutcomeResponse(
            id = 1,
            type = "income",
            total = 10000,
            note = "Transfer"
        ),
        IncomeOutcomeResponse(
            type = "outcome",
            total = 50000,
            note = "Tes 2 untuk beli bensin"

        ),
        IncomeOutcomeResponse(
            id = 1,
            type = "income",
            total = 10000,
            note = "Transfer"
        ),
        IncomeOutcomeResponse(
            type = "outcome",
            total = 50000,
            note = "Tes 2 untuk beli bensin"

        ),
        IncomeOutcomeResponse(
            id = 1,
            type = "income",
            total = 10000,
            note = "Transfer"
        ),
        IncomeOutcomeResponse(
            type = "outcome",
            total = 50000,
            note = "Tes 2 untuk beli bensin"

        )
    )
    private fun initAdapter() {
        adapter = IncomeOutcomeAdapter()
        adapter.setList(list)
        binding?.rvIncomeOutcome?.adapter = adapter
    }

    private fun initAction() {
//        binding?.mCheckEMoney?.setOnClickListener {
//            val granted = PermissionUtilities.checkNfcEnabled(this)
//            if (!granted){
//                showConfirmDialog(
//                    title = "NFC Permission",
//                    content = "You need to enable NFC to use this feature",
//                    negativeText = "Cancel",
//                    positiveText = "App Setting",
//                    positiveListener = {
//                        dismissConfirmDialog()
//                        startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
//                    }
//                )
//            }else{
//                val intent = Intent(this, ReadNfcActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }

    override fun inject() {

    }
}