package com.app.kerja_mudah.ui.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.mapper.faq.FaqMapper
import com.app.kerja_mudah.data.response.core.FaqResponse
import com.app.kerja_mudah.databinding.ActivityFaqBinding
import com.app.kerja_mudah.ui.core.adapter.FaqAdapter

class FaqActivity : BaseActivity<ActivityFaqBinding>(ActivityFaqBinding::inflate) {
    override fun initSetup() {
        list = arrayListOf<FaqResponse>(
            FaqResponse(
                id = 1,
                category = "general",
                question = getString(R.string.dummy_text_10_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 2,
                category = "general",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 3,
                category = "payment",
                question = getString(R.string.dummy_text_5_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 4,
                category = "payment",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 1,
                category = "general",
                question = getString(R.string.dummy_text_10_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 2,
                category = "general",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 3,
                category = "payment",
                question = getString(R.string.dummy_text_5_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 4,
                category = "payment",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 1,
                category = "general",
                question = getString(R.string.dummy_text_10_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 2,
                category = "security",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 3,
                category = "",
                question = getString(R.string.dummy_text_5_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 4,
                category = "",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 2,
                category = "security",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 3,
                category = "freelancer",
                question = getString(R.string.dummy_text_5_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 4,
                category = "",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 2,
                category = "service",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 3,
                category = "payment",
                question = getString(R.string.dummy_text_5_word),
                answer = getString(R.string.dummy_text_5_word)
            ),
            FaqResponse(
                id = 4,
                category = "",
                question = getString(R.string.dummy_text_15_word),
                answer = getString(R.string.dummy_text_5_word)
            )
        )
        initAdapter()
    }

    private var list = arrayListOf<FaqResponse>()

    private lateinit var adapter:FaqAdapter

    private var mapper:FaqMapper = FaqMapper()

    private fun initAdapter() {
        adapter = FaqAdapter()
        adapter.setCallBack(object : FaqAdapter.CallBack{
            override fun onClicked(faq: FaqResponse) {
                val intent = Intent(this@FaqActivity, FaqDetailActivity::class.java)
                intent.putExtra(FaqDetailActivity.FAQ, faq)
                startActivity(intent)
            }
        })
        adapter.setList(mapper.fromListResponseToListModel(list))
        binding?.rv?.adapter = adapter
    }

    override fun inject() {

    }

}