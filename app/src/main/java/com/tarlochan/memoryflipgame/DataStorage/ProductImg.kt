package com.tarlochan.memoryflipgame.DataStorage

public class ProductImg
{
    var imagelink : String? = null

    constructor(imagelink: String?) {
        this.imagelink = imagelink
    }

    override fun toString(): String {
        return "ProductImg(imagelink=$imagelink)"
    }

}