package com.tarlochan.memoryflipgame.DataStorage

public class Products
{
    var id: String? = null
    var title: String? = null
    var productImg: ProductImg? = null

    constructor(id: String?, title: String?, productImg: ProductImg?) {
        this.id = id
        this.title = title
        this.productImg = productImg
    }

    override fun toString(): String {
        return "Products(id=$id, title=$title, productImg=${productImg.toString()})"
    }

}