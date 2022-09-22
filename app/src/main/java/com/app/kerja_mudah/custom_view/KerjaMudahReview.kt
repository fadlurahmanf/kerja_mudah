package com.app.kerja_mudah.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.app.kerja_mudah.R

class KerjaMudahReview @JvmOverloads constructor(
    context: Context, attrs:AttributeSet
) : LinearLayout(context, attrs) {
    private var attributes : TypedArray


    private var oneStar:ProgressBar
    private var twoStar:ProgressBar
    private var threeStar:ProgressBar
    private var fourStar:ProgressBar
    private var fiveStar:ProgressBar

    private var totalOne:TextView
    private var totalTwo:TextView
    private var totalThree:TextView
    private var totalFour:TextView
    private var totalFive:TextView

    private var highestStar:Int = 0

    init {
        inflate(context, R.layout.kerja_mudah_review, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.ReviewKerjaMudah)

        oneStar = findViewById(R.id.pb_one)
        twoStar = findViewById(R.id.pb_two)
        threeStar = findViewById(R.id.pb_three)
        fourStar = findViewById(R.id.pb_four)
        fiveStar = findViewById(R.id.pb_five)

        totalOne = findViewById(R.id.tv_total_one)
        totalTwo = findViewById(R.id.tv_total_two)
        totalThree = findViewById(R.id.tv_total_three)
        totalFour = findViewById(R.id.tv_total_four)
        totalFive = findViewById(R.id.tv_total_five)
    }

    fun setProgress(one:Int, two:Int, three:Int, four:Int, five:Int){
        setHighestStar(one, two, three, four, five)
        oneStar.max = highestStar
        oneStar.progress = one
        totalOne.text = one.toString()

        twoStar.max = highestStar
        twoStar.progress = two
        totalTwo.text = two.toString()

        threeStar.max = highestStar
        threeStar.progress = three
        totalThree.text = three.toString()

        fourStar.max = highestStar
        fourStar.progress = four
        totalFour.text = four.toString()

        fiveStar.max = highestStar
        fiveStar.progress = five
        totalFive.text = five.toString()

    }

    private fun setHighestStar(one:Int, two:Int, three:Int, four:Int, five:Int){
        highestStar = one
        if (two > highestStar){
            highestStar = two
        }
        if (three > highestStar){
            highestStar = three
        }
        if (four > highestStar){
            highestStar = four
        }
        if (five > highestStar){
            highestStar = five
        }
    }
}