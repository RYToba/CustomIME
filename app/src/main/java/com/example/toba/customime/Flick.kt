package com.example.toba.customime

class Flick(val direction: Direction, val distance: Int) {

    enum class Direction { NONE, LEFT, RIGHT, UP, DOWN }

    val isValid = when (direction) {
        Direction.NONE -> distance == 0
        else -> distance > 0
    }

    companion object {
        val NONE = Flick(Direction.NONE, 0)
    }

    override fun toString(): String = "Flick($direction, $distance)"
}