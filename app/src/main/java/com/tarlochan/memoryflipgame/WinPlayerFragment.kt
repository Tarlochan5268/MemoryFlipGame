package com.tarlochan.memoryflipgame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_win_player.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WinPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WinPlayerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootview =  inflater.inflate(R.layout.fragment_win_player, container, false)
        val btnHome : Button = rootview.findViewById<View>(R.id.btnHome) as Button
        btnHome.setOnClickListener{
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.layoutFragment, Home())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        //play sound
        MusicPlayer(context).playSound("winner.mp3")
        //val txtlevel= rootview.findViewById<View>(R.id.txtLevel) as TextView
        //val tvmatch =  rootview.findViewById<View>(R.id.tvMatchFound) as TextView
        val txtmessage = rootview.findViewById<View>(R.id.txtMessage) as TextView
        var level : String = ""
        var winner : String = ""
        //using models to get data from previous fragments
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
/*
        model.level.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                level = o!!.toString()
                Log.d("level : ",level)
                txtlevel.text = "Level : "+o!!.toString()
            }
        })

 */
        model.winner.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                winner = o!!.toString()
                Log.d("level : ",level)

                txtmessage.text = "Congratulations \n"+winner+" \nwon the Game"
            }
        })
        /*
        model.matchCount.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                //level = o!!.toString()
                Log.d("level : ",level)
                tvMatchFound.text = "Match Found : "+o!!.toString()
            }
        })

         */

        return rootview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WinPlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WinPlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
