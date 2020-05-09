package com.tarlochan.memoryflipgame.DataStorage

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

public class JsonParser {

    var mProductsList: ArrayList<Products>? = null
    var context: Context? = null

    constructor(context: Context?) {
        this.context = context
    }

    fun loadJSONFromAsset(): String? {
        val json: String
        json = try {
            val `is` = context!!.assets.open("Data.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            val count = `is`.read(buffer)
            `is`.close()
            Log.d("-- COUNT --", String.format("%d Bytes",count));
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun processJSON() {
        val jsonString = loadJSONFromAsset()
        if (jsonString != null) {
            try {
                val mJSONArray = JSONArray(jsonString)
                mProductsList = ArrayList<Products>()
                for (i in 0 until mJSONArray.length()) {
                    val mProduct: Products? = getProductObjectFromJSON(mJSONArray.getJSONObject(i))
                    mProductsList!!.add(mProduct!!)
                    Log.d("-- JSON -- ", mProduct.toString());
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(JSONException::class)
    fun getProductObjectFromJSON(userJsonObject: JSONObject?): Products?
    {
        var id:String? = userJsonObject!!.getString("id")
        var title:String? = userJsonObject!!.getString("title")

        var mProductImgJsonObject:JSONObject = JSONObject(userJsonObject.getJSONObject("image").toString())
        var ImgLink:String? = mProductImgJsonObject!!.getString("src")
        var mProductImg:ProductImg? = ProductImg(ImgLink)

        var product : Products? = Products(id,title,mProductImg)
        return product
    }
}