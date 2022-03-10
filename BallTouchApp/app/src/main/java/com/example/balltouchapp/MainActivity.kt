package com.example.balltouchapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // 新しいクラスを表示させる
        val ballView = BallView(this)
        ballView.init()
        setContentView(ballView)
    }
}
