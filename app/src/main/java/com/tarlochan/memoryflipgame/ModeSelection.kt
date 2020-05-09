package com.tarlochan.memoryflipgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ModeSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
public class ModeSelection : Fragment() {
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
        val rootView =  inflater.inflate(R.layout.fragment_mode_selection, container, false)
        val btnBack : Button = rootView.findViewById<View>(R.id.btnBack) as Button
        btnBack.setOnClickListener{
            val fm: FragmentManager? = fragmentManager
            if (fm != null) {
                fm.popBackStack()
            }
        }
        var btneasy: Button =  rootView.findViewById<Button>(R.id.btneasy)
        btneasy.setOnClickListener{
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.layoutFragment, EasyLevelFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        var btnhard: Button =  rootView.findViewById<Button>(R.id.btnhard)
        btnhard.setOnClickListener{
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.layoutFragment, HardLevelFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        var btnsuperhard: Button =  rootView.findViewById<Button>(R.id.btnsuperhard)
        btnsuperhard.setOnClickListener{
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.layoutFragment, SuperHardLevelFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        var btntwoplayer: Button =  rootView.findViewById<Button>(R.id.btntwoplayer)
        btntwoplayer.setOnClickListener{
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.layoutFragment, TwoPlayer())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModeSelection.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModeSelection().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
