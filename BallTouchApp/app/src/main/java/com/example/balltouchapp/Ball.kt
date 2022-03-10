package com.example.balltouchapp

import android.graphics.Color

class Ball(var id: Int, var r: Float, var x: Float, var y: Float, var color: Int) {
    private val util: Util = Util()

    fun reset() {
        this.x = 10000F
        this.y = 10000F
    }

    fun setNewBall(x: Float, y: Float) {
        this.r = util.getFloat()
        this.x = x
        this.y = y
        this.color = util.getColor()
    }

}