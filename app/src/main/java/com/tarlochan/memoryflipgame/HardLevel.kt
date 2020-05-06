package com.tarlochan.memoryflipgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HardLevel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_level)
    }
}
/*

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
    var isPaused = false, var isCancelled:kotlin.Boolean = false
    var b: Bundle? = null
    private var pref: SharedPreferences? = null
    var pos = 0, var count:Int = 0, var bestScore:Int = 0


    fun EasyLevel() {
        // Required empty public constructor
    }

    fun shuffle(cards: IntArray, n: Int) {
        val random = Random()
        for (i in 0 until n) {
            val r = random.nextInt(n - i)
            val temp = cards[r]
            cards[r] = cards[i]
            cards[i] = temp
        }
    }
 */