package com.rczubak.refrigerator

data class Product(val id: Int,
                   val name: String,
                   var quantity: Int,
                   val category: String,
                   val expirationDate: String,
                   val image: Int)