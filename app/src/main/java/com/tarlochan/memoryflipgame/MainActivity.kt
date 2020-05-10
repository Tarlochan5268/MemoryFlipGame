package com.tarlochan.memoryflipgame

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.tarlochan.memoryflipgame.DataStorage.JsonParser
import com.tarlochan.memoryflipgame.DataStorage.Products
import java.util.*
import kotlin.collections.ArrayList

public class MainActivity : AppCompatActivity() {
    companion object
    {
        public var mProductsList: ArrayList<Products>? = ArrayList()
    }
    //var mProductsList: ArrayList<Products>? = null
    var mJsonParser:JsonParser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mProductsList = ArrayList()
        mJsonParser = JsonParser(this)
        mJsonParser!!.processJSON()
        for (product in mJsonParser!!.mProductsList!!) {
            val mProduct = Products(
                product.id,
                product.title,
                product.productImg
            )
            Log.d("Product obj : ",mProduct.toString())
            mProductsList!!.add(mProduct)
        }

        Log.d("Array List Products : ", mProductsList.toString())

        supportActionBar?.hide()
        //window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layoutFragment, Home())
        transaction.commit()
    }

    // to disable the back button in the fragments to prevent the glitches
    override fun onBackPressed() {

    }
}
