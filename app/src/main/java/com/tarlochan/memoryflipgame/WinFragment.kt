package com.tarlochan.memoryflipgame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.w3c.dom.Text
import kotlin.math.log
import android.widget.Toast.makeText as makeText1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
public lateinit var pref: SharedPreferences
/**
 * A simple [Fragment] subclass.
 * Use the [WinFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WinFragment : Fragment() {
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


        val rootview = inflater.inflate(R.layout.fragment_win, container, false)
        val btnHome : Button = rootview.findViewById<View>(R.id.btnHome) as Button
        btnHome.setOnClickListener{
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.layoutFragment, Home())
                transaction.addToBackStack(null)
                transaction.commit()
        }

        //play sound
        MusicPlayer(context).playSound("winner.mp3")
        val txtlevel= rootview.findViewById<View>(R.id.txtLevel) as TextView
        val txtyourscore= rootview.findViewById<View>(R.id.txtYourScore) as TextView
        val txthighscore= rootview.findViewById<View>(R.id.txtHighScore) as TextView
        val txtmessage = rootview.findViewById<View>(R.id.txtMessage) as TextView
        var level : String = ""
        //using models to get data from previous fragments
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        model.level.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                level = o!!.toString()
                Log.d("level : ",level)
                txtlevel.text = "Level : "+o!!.toString()
            }
        })
        Log.d("level outside : ",level)
        var yourscoreInt:Int = 20
        var highscoreInt:Int = 20

        model.yourScore.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                //yourscoreInt = 30
                Log.d("Inside u score : ",yourscoreInt.toString())
                txtyourscore.text = o!!.toString()
                Log.d("Before txtyourscore : ",txtyourscore.text.toString())

                yourscoreInt = txtyourscore.text.toString().toInt()
                txtyourscore.text = "Your Score : "+o!!.toString()+" seconds"
                //strange issue conversion of string to int not working weirdly
                Log.d("txtyourscore from o : ",o!!.toString())

            }
        })

        model.HighScore.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                //highscoreInt = 30
                Log.d("Inside high score : ",highscoreInt.toString())
                txthighscore.text = o!!.toString()
                Log.d("Before txthighscore : ",txthighscore.text.toString())

                highscoreInt = txthighscore.text.toString().toInt()
                txthighscore.text = "High Score : "+o!!.toString()+" seconds"
                //strange issue conversion of string to int not working weirdly
                Log.d("txthighscore from o : ",o!!.toString())

                if(yourscoreInt<highscoreInt)
                {
                    pref = context!!.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor = pref.edit()
                    if (txtlevel.text.trim().endsWith("Easy"))
                    {
                        //Log.d("txtLevel Equals Easy :",txtlevel.equals("Easy").toString())
                        editor.putString(Constants.EASY_HIGH_KEY,yourscoreInt.toString())
                    }else if (txtlevel.text.trim().endsWith("Super Hard"))
                    {
                        //Log.d("txtLevel Equals Shard:",txtlevel.equals("Super Hard").toString())
                        editor.putString(Constants.SUPER_HARD_HIGH_KEY,yourscoreInt.toString())
                    }
                    else if (txtlevel.text.trim().endsWith("Hard"))
                    {
                        //Log.d("txtLevel Equals Hard :",txtlevel.equals("hard").toString())
                        editor.putString(Constants.HARD_HIGH_KEY,yourscoreInt.toString())
                    }


                    //Log.d("txtLevel NotworkPrint:",txtlevel.text.toString())
                    editor.apply()
                    txtmessage.text = "Congratulations you won the Game in "+level+" and also broke the previous high score record"
                }
            }
        })

        return rootview

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WinFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
