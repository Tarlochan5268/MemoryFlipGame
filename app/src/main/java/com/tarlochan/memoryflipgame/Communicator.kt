package com.tarlochan.memoryflipgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class Communicator : ViewModel(){

    val yourScore = MutableLiveData<Any>()
    val HighScore = MutableLiveData<Any>()
    val level = MutableLiveData<Any>()

    fun setMsgCommunicator(iyourScore:String,iHighScore:String,ilevel:String){
        yourScore.setValue(iyourScore)
        HighScore.setValue(iHighScore)
        level.setValue(ilevel)
    }
}