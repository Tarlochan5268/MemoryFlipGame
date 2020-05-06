package com.tarlochan.memoryflipgame

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.wajahatkarim3.easyflipview.EasyFlipView
import java.util.*
import kotlin.collections.ArrayList

class EasyLevel : AppCompatActivity() {

    private var EasyLevelRecyclerView: RecyclerView? = null
    var cards: ArrayList<Int>? = null
    var CARDS = intArrayOf(
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6,
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6
    )
    var flippedCard: EasyFlipView? = null
    var RemainingTime: Long = 0
    var isPaused = false
    var isCancelled:Boolean = false
    var b: Bundle? = null
    private var pref: SharedPreferences? = null
    var pos = 0
    var count:Int = 0
    var bestScore:Int = 0

    fun shuffle(cards: IntArray, n: Int) {
        val random = Random()
        for (i in 0 until n) {
            val r = random.nextInt(n - i)
            val temp = cards[r]
            cards[r] = cards[i]
            cards[i] = temp
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_level)
        

    }
}
