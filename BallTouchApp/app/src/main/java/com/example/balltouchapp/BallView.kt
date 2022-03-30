package com.example.balltouchapp

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.Exception
import kotlin.math.sqrt


class BallView(context: Context?) : View(context){
    private val util: Util = Util()
    private var backColor = Color.WHITE
    private var contacting = false
    private var ballList = mutableListOf<Ball>()
    private val ballCount = 100
    private var ballNow = 0
    private var ballNext = 0
    private var paint: Paint = Paint()
    private var isCircle = true
    private var bmp: Bitmap? = BitmapFactory.decodeResource(resources, R.drawable.cat01)
    private var widthCenter = 0F
    private var heightCenter = 0F
    private var showCat = false

    fun init() {
        ballList.clear()
        for (i in 0 until ballCount) {
            val ball = Ball(i, util.getFloat(), 10000F, 10000F, backColor, util.getText())
            ballList.add(ball)
        }
    }

    fun setDisplyaSize(width: Int, height: Int) {
        widthCenter = (width / 2).toFloat()
        heightCenter = (height / 2).toFloat()
    }

    private fun allReset() {
        for (ball in ballList) {
            ball.reset()
        }
    }

    // onDrawで描画の準備
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(backColor) //カンバス（背景）色

        // ペイントする色の指定と、丸い図形
        for (ball in ballList) {
            paint.color = ball.color
            if (isCircle) {
                canvas?.drawCircle(ball.x, ball.y, ball.r, paint)
            } else {
                paint.textSize = ball.r
                canvas?.drawText(ball.kana, ball.x, ball.y, paint)
                // canvas?.drawRect(ball.x, ball.y, (ball.x + ball.r), (ball.y + ball.r), paint)
            }
        }

        // ボールリセット時の画像表示
        if (showCat) {
            showCat = !showCat
            canvas?.drawBitmap(bmp!!, widthCenter, heightCenter, paint)
        }
    }

    // 画面タッチ
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var ball: Ball
        try{
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(contacting) return true
                    if(isExist(event)) return true

                    setBallNext()
                    ball = ballList[ballNext]
                    ball.setNewBall(event!!.x, event.y)
                    Log.d("INFO", "setNewBall")

                    ballNow = ball.id
                    ballNext++
                    if (ballNext == ballCount) {
                        ballNext = 0
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if(contacting) {
                        return true
                    }
                    ball = ballList[ballNow]
                    ball.x = event!!.x
                    ball.y = event.y
                    unionBall()
                }
                MotionEvent.ACTION_UP -> {
                    contacting = false
                }
                else -> {}
            }
            invalidate()
        } catch (e: Exception) {
            Log.d("ERROR", "onTouchEvent()")
            Log.d("ERROR", e.toString())
            return false;
        } finally {
            //
        }
        //return super.onTouchEvent(event)
        return true
    }

    private fun setBallNext() {
        for (ball in ballList) {
            if(ball.x == 10000F) {
                ballNext = ball.id
                break
            }
        }
    }

    private fun unionBall() {
        var ball = ballList[ballNow]
        try {
            for (target in ballList) {
                if (target.id != ballNow) {
                    val dx = ball.x - target.x
                    val dy = ball.y - target.y
                    val len = sqrt(dx*dx + dy*dy)
                    if (len < ball.r + target.r) {
                        contacting = true
                        ball.x = (ball.x + target.x) / 2
                        ball.y = (ball.y + target.y) / 2
                        ball.r = (ball.r + target.r)
                        ball.color = util.getAddColor2(ball, target)
                        ball.kana = (ball.kana.toInt() + target.kana.toInt()).toString()
                        target.reset()
                        Log.d("INFO", "半径：" + ball.r.toString())
                    }
                }
            }
            if(ball.r > 800F) {
                init()
                isCircle = !isCircle
                showCat = !showCat

            }
        } catch (e: Exception) {
            Log.d("ERROR", "unionBall()")
            Log.d("ERROR", e.toString())
        }
    }

    private fun isExist(event: MotionEvent): Boolean {
        for (ball in ballList) {
            if (ball.x-ball.r <= event!!.x
                && event!!.x <= ball.x+ball.r
                && ball.y-ball.r <= event.y
                && event.y <= ball.y+ball.r) {
                ballNow = ball.id
                return true
            }
        }
        return false
    }
}