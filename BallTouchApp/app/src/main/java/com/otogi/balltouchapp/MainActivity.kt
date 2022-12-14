package com.example.balltouchapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener {
            var wm = windowManager.currentWindowMetrics
            val ballView = BallView(this)
            ballView.init()
            ballView.setDisplyaSize(wm.bounds.width(), wm.bounds.height())
            setContentView(ballView)
        }
    }
}
