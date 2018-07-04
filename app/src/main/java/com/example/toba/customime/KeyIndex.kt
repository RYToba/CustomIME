package com.example.toba.customime

object KeyIndex {
    const val NOTHING   = -1
    const val INDICATOR = 0

    const val A1 = 1
    const val B1 = 2
    const val C1 = 3
    const val D1 = 4
    const val E1 = 5


    const val A2 = 7
    const val B2 = 8
    const val C2 = 9
    const val D2 = 10
    const val E2 = 11

    const val A3 = 12
    const val B3 = 13
    const val C3 = 14
    const val D3 = 15
    const val E3 = 16


    const val A4 = 17
    const val B4 = 18
    const val C4 = 19
    const val D4 = 20
    const val E4 = 21

    fun isValid(index: Int): Boolean = index in (A1..E4)
}