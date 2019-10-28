package com.rczubak.refrigerator

data class Product(val name: String,
                   var quantity: Int,
                   val category: String,
                   val expirationDate: String){
    constructor() : this("",0,"","")
}