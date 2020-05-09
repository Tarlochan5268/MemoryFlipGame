package com.tarlochan.memoryflipgame

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarlochan.memoryflipgame.adapters.SuperHardLevelAdapter
import com.wajahatkarim3.easyflipview.EasyFlipView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoPlayer.newInstance] factory method to
 * create an instance of this fragment.
 */
public class TwoPlayer : Fragment() {
    public lateinit var TwoPlayerLevelRecyclerView: RecyclerView
    var cards: ArrayList<Int>? = null
    var CARDS = intArrayOf(
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6,
        R.drawable.card7,
        R.drawable.card8,
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6,
        R.drawable.card7,
        R.drawable.card8
    )
    var time = 0
    var player1:TextView? = null
    var player2:TextView? = null
    var player1levelscore:TextView? = null
    var player2levelscore:TextView? = null

    private var model: Communicator?=null
    var flippedCard: EasyFlipView? = null
    var RemainingTime: Long = 0
    var isPaused = false
    var isCancelled = false
    lateinit var b: Bundle
    public lateinit var pref: SharedPreferences
    var pos = 0
    var count = 0
    var player1count = 0
    var player1turn = true
    var player2turn = false
    var player2count = 0
    var flipcount = 0
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
            inflater.inflate(R.layout.fragment_two_player, container, false)
        player1 = rootView.findViewById<View>(R.id.player1) as TextView
        player2 = rootView.findViewById<View>(R.id.player2) as TextView
        player1levelscore = rootView.findViewById<View>(R.id.player1levelScore) as TextView
        player2levelscore = rootView.findViewById<View>(R.id.player2levelScore) as TextView

        TwoPlayerLevelRecyclerView = rootView.findViewById(R.id.twoplayerlevelview)

        b = Bundle()
        //b!!.putInt("level", Constants.LEVEL_SUPER_HARD)

        pref = context!!.getSharedPreferences(Constants.PREF_NAME, 0)
        bestScore = pref.getString(Constants.PLAYER_TWO_HIGH_KEY,"40")!!.toInt()
        //bestScore = pref.getInt(Constants.SUPER_HARD_HIGH_KEY,(Constants.SUPER_HARD_TIME / Constants.TIMER_INTERVAL).toInt())
        //(rootView.findViewById<View>(R.id.bestSuperHard) as TextView).append(bestScore.toString() + "")

        val lm: RecyclerView.LayoutManager = GridLayoutManager(context, 5, LinearLayoutManager.VERTICAL, false)
        TwoPlayerLevelRecyclerView.setLayoutManager(lm)
        cards = ArrayList()
        // TODO: card shuffle here
        shuffle(CARDS, Constants.PLAYER_TWO_NO_OF_CARDS)
        shuffle(CARDS, Constants.PLAYER_TWO_NO_OF_CARDS) // double shuffle
        for (card in CARDS) {
            cards!!.add(card)
        }
        TwoPlayerLevelRecyclerView.setAdapter(SuperHardLevelAdapter(cards))
        isPaused = false
        isCancelled = false

        object : CountDownTimer(Constants.PLAYER_TWO_TIME, Constants.TIMER_INTERVAL.toLong()) {

            override fun onTick(millisUntilFinished: Long) {
                if (isPaused || isCancelled) {
                    cancel()
                } else {

                    (rootView.findViewById<View>(R.id.twoplayerlevelcounter) as TextView).text =
                        "Time : " + millisUntilFinished / Constants.TIMER_INTERVAL
                    RemainingTime = millisUntilFinished
                    if (count == Constants.PLAYER_TWO_NO_OF_CARDS) {
                        //b!!.putString("Data", "win")
                        time = ((Constants.PLAYER_TWO_TIME - millisUntilFinished) / Constants.TIMER_INTERVAL).toInt()
                        //b!!.putInt("Time", time.toInt())

                        if(player1count>player1count)
                        {
                            model!!.setMsgCommunicator(player1count.toString(),"Player 1",time.toString(),"Two Player")
                        }
                        else if(player1count<player2count)
                        {
                            model!!.setMsgCommunicator(player2count.toString(),"Player 2",time.toString(),"Two Player")
                        }
                        //model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Two Player")
                        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.layoutFragment, WinPlayerFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        cancel()
                        onFinish()
                    }
                }
            }

            override fun onFinish() {
                if (count < Constants.PLAYER_TWO_NO_OF_CARDS) {
                    //b!!.putString("Data", "lost")
                    //b!!.putInt("Time", (Constants.SUPER_HARD_TIME / Constants.TIMER_INTERVAL).toInt())
                    if(player1count>player1count)
                    {
                        model!!.setMsgCommunicator(player1count.toString(),"Player 1",time.toString(),"Two Player")
                    }
                    else if(player1count<player2count)
                    {
                        model!!.setMsgCommunicator(player2count.toString(),"Player 2",time.toString(),"Two Player")
                    }
                    //model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Two Player")
                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.layoutFragment, WinPlayerFragment())
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
                                (rootView.findViewById<View>(R.id.superhardlevelcounter) as TextView).text =
                                    "Time : " + millisUntilFinished / Constants.TIMER_INTERVAL
                                RemainingTime = millisUntilFinished
                                if (count == Constants.PLAYER_TWO_NO_OF_CARDS) {
                                    //b!!.putString("Data", "win")
                                    time = ((Constants.PLAYER_TWO_TIME - millisUntilFinished) / Constants.TIMER_INTERVAL).toInt()
                                    //b!!.putInt("Time", time)

                                    if(player1count>player1count)
                                    {
                                        model!!.setMsgCommunicator(player1count.toString(),"Player 1",time.toString(),"Two Player")
                                    }
                                    else if(player1count<player2count)
                                    {
                                        model!!.setMsgCommunicator(player2count.toString(),"Player 2",time.toString(),"Two Player")
                                    }
                                    //model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Two Player")
                                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                                    transaction.replace(R.id.layoutFragment, WinPlayerFragment())
                                    transaction.addToBackStack(null)
                                    transaction.commit()
                                    cancel()
                                    onFinish()
                                }
                            }
                        }

                        override fun onFinish() {
                            if (count < Constants.PLAYER_TWO_NO_OF_CARDS) {
                                //b!!.putString("Data", "lost")
                                //b!!.putInt("Time", (Constants.SUPER_HARD_TIME / Constants.TIMER_INTERVAL).toInt())

                                if(player1count>player1count)
                                {
                                    model!!.setMsgCommunicator(player1count.toString(),"Player 1",time.toString(),"Two Player")
                                }
                                else if(player1count<player2count)
                                {
                                    model!!.setMsgCommunicator(player2count.toString(),"Player 2",time.toString(),"Two Player")
                                }
                                //model!!.setMsgCommunicator(time.toString(),bestScore.toString(),"Two Player")
                                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                                transaction.replace(R.id.layoutFragment, WinPlayerFragment())
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
                    transaction.replace(R.id.layoutFragment, TwoPlayer())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                pause.show()
                return@OnKeyListener true
            }
            false
        })

        //

        TwoPlayerLevelRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    Toast.makeText(context,"p1: "+player1count.toString()+" p2: "+player2count.toString(),Toast.LENGTH_SHORT)
                    Log.d("Player1 count : ",player1count.toString())
                    Log.d("Player 2 Count : ",player2count.toString())
                    if(flipcount%2==1)
                    {
                        if(player1turn)
                        {
                            player2!!.text = "Player 2 Turn"
                            player1!!.text = "Player 1"
                            player2turn = true
                            player1turn = false
                        }
                        else if(player2turn)
                        {
                            player1!!.text = "Player 1 Turn"
                            player2!!.text = "Player 2"
                            player1turn = true
                            player2turn = false
                        }
                    }
                    flipcount++
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
                                    EasyFlipView.OnFlipAnimationListener { easyFlipView, newCurrentSide ->
                                        for (i in 0 until TwoPlayerLevelRecyclerView.childCount) {
                                            val child1 =
                                                TwoPlayerLevelRecyclerView.getChildAt(i) as EasyFlipView
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
                                                if(player1turn)
                                                {   player2count++
                                                    player2levelscore!!.text = "Match : "+player2count
                                                }
                                                else if(player2turn)
                                                {

                                                    player1count++
                                                    player1levelscore!!.text = "Match : "+player1count
                                                }
                                                //(rootView.findViewById<View>(R.id.superhardlevelScore) as TextView).text =
                                                    "Match : " + score
                                                for (i in 0 until TwoPlayerLevelRecyclerView.childCount) {
                                                    val child1 =
                                                        TwoPlayerLevelRecyclerView.getChildAt(i) as EasyFlipView
                                                    child1.isEnabled = true
                                                }
                                                (child as EasyFlipView).onFlipListener = null
                                            }, 200)
                                    }
                            } else {
                                (child as EasyFlipView).onFlipListener =
                                    EasyFlipView.OnFlipAnimationListener { easyFlipView, newCurrentSide ->
                                        for (i in 0 until TwoPlayerLevelRecyclerView.childCount) {
                                            val child1 =
                                                TwoPlayerLevelRecyclerView.getChildAt(i) as EasyFlipView
                                            child1.isEnabled = false
                                        }
                                        Handler()
                                            .postDelayed({
                                                flippedCard!!.flipTheView()
                                                (child as EasyFlipView).flipTheView()
                                                flippedCard = null
                                                (child as EasyFlipView).onFlipListener = null
                                                for (i in 0 until TwoPlayerLevelRecyclerView.childCount) {
                                                    val child1 =
                                                        TwoPlayerLevelRecyclerView.getChildAt(i) as EasyFlipView
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
