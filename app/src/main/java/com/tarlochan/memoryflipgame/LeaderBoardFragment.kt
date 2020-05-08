package com.tarlochan.memoryflipgame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [LeaderBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardFragment : Fragment() {
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

        val rootview = inflater.inflate(R.layout.fragment_leader_board, container, false)

        val tveasyHS = rootview.findViewById<View>(R.id.easylead) as TextView
        val tvhardHS = rootview.findViewById<View>(R.id.hardlead) as TextView
        val tvsuperhardHS = rootview.findViewById<View>(R.id.superhardlead) as TextView
        val btnBack : Button = rootview.findViewById<View>(R.id.btnBack) as Button

        var pref : SharedPreferences = context!!.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var easyBS = pref.getString(Constants.EASY_HIGH_KEY,"32")
        tveasyHS.text = easyBS.toString()+" seconds"
        var hardBS = pref.getString(Constants.HARD_HIGH_KEY,"42")
        tvhardHS.text = hardBS.toString()+" seconds"
        var superhardBS = pref.getString(Constants.SUPER_HARD_HIGH_KEY,"52")
        tvsuperhardHS.text = superhardBS.toString()+" seconds"

        btnBack.setOnClickListener{
            val fm: FragmentManager? = fragmentManager
            if (fm != null) {
                fm.popBackStack()
            }
        }
        return rootview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderBoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
