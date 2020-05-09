package com.tarlochan.memoryflipgame

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarlochan.memoryflipgame.adapters.EasyLevelAdapter
import com.wajahatkarim3.easyflipview.EasyFlipView
import com.wajahatkarim3.easyflipview.EasyFlipView.OnFlipAnimationListener
import java.util.*

public class EasyLevelFragment : Fragment() {
    public lateinit var EasyLevelRecyclerView: RecyclerView
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

    private var model: Communicator?=null
    var flippedCard: EasyFlipView? = null
    var RemainingTime: Long = 0
    var isPaused = false
    var isCancelled = false
    lateinit var b: Bundle
    public lateinit var pref: SharedPreferences
    var pos = 0
    var count = 0
    var score = 0
    var bestScore = 0

    fun shuffle(cards: IntArray, n: Int) {
        val random = Random()
        for (i in 0 until n) {
            val r = random.nextInt(n - i)
            val temp = cards[r]
            cards[r] = cards[i]
            cards[i] = temp
        }
    }

    fun fragmentTransaction(f: Fragment) {
        val transaction = fragmentManager!!.beginTransaction()

        //transaction.replace(this,f)
        transaction.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        val rootView =
            inflater.inflate(R.layout.fragment_easy_level, container, false)
        EasyLevelRecyclerView = rootView.findViewById(R.id.easylevelview)

        b = Bundle()
        //b!!.putInt("level", Constants.LEVEL_EASY)

        pref = context!!.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        bestScore = pref.getString(Constants.EASY_HIGH_KEY,"32")!!.toInt()

        (rootView.findViewById<View>(R.id.bestEasy) as TextView).append(bestScore.toString() + "")

        val lm: RecyclerView.LayoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        EasyLevelRecyclerView.setLayoutManager(lm)
        cards = ArrayList()
        // TODO: card shuffle here
        shuffle(CARDS, Constants.EASY_NO_OF_CARDS)
        shuffle(CARDS, Constants.EASY_NO_OF_CARDS) // double shuffle
        for (card in CARDS) {
            cards!!.add(card)
        }
        EasyLevelRecyclerView.setAdapter(EasyLevelAdapter(cards))
        isPaused = false
        isCancelled = false

        object : CountDownTimer(Constants.EASY_TIME, Constants.TIMER_INTERVAL.toLong()) {

            override fun onTick(millisUntilFinished: Long) {
                if (isPaused || isCancelled) {
                    cancel()
                } else {
                    (rootView.findViewById<View>(R.id.easylevelcounter) as TextView).text =
                        "Time : " + millisUntilFinished / Constants.TIMER_INTERVAL
                    RemainingTime = millisUntilFinished
                    if (count == Constants.EASY_NO_OF_CARDS) {
                        //b!!.putString("Data", "win")
                        val time =
                            (Constants.EASY_TIME - millisUntilFinished) / Constants.TIMER_INTERVAL
                        //b!!.putInt("Time", time.toInt())
                        cancel()
                        onFinish()

                        model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Easy")

                        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.layoutFragment, WinFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }
            }

            override fun onFinish() {
                if (count < Constants.EASY_NO_OF_CARDS) {
                    //b!!.putString("Data", "lost")
                    //b!!.putInt("Time", (Constants.EASY_TIME / Constants.TIMER_INTERVAL).toInt())
                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.layoutFragment, LoseFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                //fragmentTransaction(b)
            }
        }.start()
        rootView.isFocusableInTouchMode = true
        rootView.requestFocus()

        rootView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                isPaused = true
                val pause =
                    AlertDialog.Builder(context)
                pause.setTitle("Game paused")
                pause.setMessage("Do you want to quit ?")
                pause.setCancelable(false)
                pause.setPositiveButton(
                    "Resume"
                ) { dialog, which ->
                    isPaused = false
                    object : CountDownTimer(
                        RemainingTime,
                        Constants.TIMER_INTERVAL.toLong()
                    ) {
                        var time = 0
                        override fun onTick(millisUntilFinished: Long) {
                            if (isPaused || isCancelled) {
                                cancel()
                            } else {
                                (rootView.findViewById<View>(R.id.easylevelcounter) as TextView).text =
                                    "Time : " + millisUntilFinished / Constants.TIMER_INTERVAL
                                RemainingTime = millisUntilFinished
                                if (count == Constants.EASY_NO_OF_CARDS) {
                                    //b!!.putString("Data", "win")
                                    time = ((Constants.EASY_TIME - millisUntilFinished) / Constants.TIMER_INTERVAL).toInt()
                                    //b!!.putInt("Time", time)

                                    model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Easy")
                                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.layoutFragment, WinFragment())
                                    transaction.addToBackStack(null)
                                    transaction.commit()
                                    cancel()
                                    onFinish()
                                }
                            }
                        }

                        override fun onFinish() {
                            if (count < Constants.EASY_NO_OF_CARDS) {
                                //b!!.putString("Data", "lost")
                                //b!!.putInt("Time", (Constants.EASY_TIME / Constants.TIMER_INTERVAL).toInt())

                                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                                transaction.replace(R.id.layoutFragment, LoseFragment())
                                transaction.addToBackStack(null)
                                transaction.commit()
                            }

                            //fragmentTransaction(b)
                        }
                    }.start()
                }
                pause.setNegativeButton(
                    "Quit"
                ) { dialog, which ->
                    isCancelled = true
                    fragmentManager!!.popBackStack()
                }
                pause.setNeutralButton("Shuffle")
                {
                    dialog, which ->
                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.layoutFragment, EasyLevelFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                pause.show()
                return@OnKeyListener true
            }
            false
        })

        //

        EasyLevelRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = rv.getChildAdapterPosition(child)
                    if (flippedCard == null) {
                        flippedCard = child as EasyFlipView
                        pos = position
                    } else {
                        if (pos == position) {
                            flippedCard = null
                        } else {
                            if (cards!![pos] == cards!![position]) {
                                (child as EasyFlipView).onFlipListener =
                                    OnFlipAnimationListener { easyFlipView, newCurrentSide ->
                                        for (i in 0 until EasyLevelRecyclerView.childCount) {
                                            val child1 =
                                                EasyLevelRecyclerView.getChildAt(i) as EasyFlipView
                                            child1.isEnabled = false
                                        }
                                        Handler()
                                            .postDelayed({
                                                flippedCard!!.visibility = View.GONE
                                                child.setVisibility(View.GONE)
                                                child.setEnabled(false)
                                                flippedCard!!.isEnabled = false
                                                flippedCard = null
                                                count += 2
                                                score++
                                                (rootView.findViewById<View>(R.id.easylevelScore) as TextView).text =
                                                    "Match : " + score
                                                for (i in 0 until EasyLevelRecyclerView.childCount) {
                                                    val child1 =
                                                        EasyLevelRecyclerView.getChildAt(i) as EasyFlipView
                                                    child1.isEnabled = true
                                                }
                                                (child as EasyFlipView).onFlipListener = null
                                            }, 200)
                                    }
                            } else {
                                (child as EasyFlipView).onFlipListener =
                                    OnFlipAnimationListener { easyFlipView, newCurrentSide ->
                                        for (i in 0 until EasyLevelRecyclerView.childCount) {
                                            val child1 =
                                                EasyLevelRecyclerView.getChildAt(i) as EasyFlipView
                                            child1.isEnabled = false
                                        }
                                        Handler()
                                            .postDelayed({
                                                flippedCard!!.flipTheView()
                                                (child as EasyFlipView).flipTheView()
                                                flippedCard = null
                                                (child as EasyFlipView).onFlipListener = null
                                                for (i in 0 until EasyLevelRecyclerView.childCount) {
                                                    val child1 =
                                                        EasyLevelRecyclerView.getChildAt(i) as EasyFlipView
                                                    child1.isEnabled = true
                                                }
                                            }, 100)
                                    }
                            }
                        }
                    }
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
        return rootView
    }
}
