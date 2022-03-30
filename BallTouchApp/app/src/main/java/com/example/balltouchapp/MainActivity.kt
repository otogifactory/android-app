package com.example.balltouchapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        var wm = windowManager.currentWindowMetrics

        // 新しいクラスを表示させる
        val ballView = BallView(this)
        ballView.init()
        ballView.setDisplyaSize(wm.bounds.width(), wm.bounds.height())
        setContentView(ballView)

//        var iv :ImageView = findViewById(R.id.iv)
//        iv.setImageResource(R.drawable.cat01)
    }
}
