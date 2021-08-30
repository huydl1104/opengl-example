package com.example.opengl_lib.view

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.opengl_lib.render.MyGLRenderer

/**
 * @author yudongliang
 * create time 2021-08-27
 * describe : 自定义surfaceView
 */
class MyGlSurfaceView @JvmOverloads constructor(context: Context,
                                                attributeSet: AttributeSet? = null) :GLSurfaceView(context, attributeSet) {

   private var mGLRenderer: MyGLRenderer

   private val TOUCH_SCALE_FACTOR = 180.0f / 320



   init {
       //create 3 context
       setEGLContextClientVersion(3)
       //config chooser
       setEGLConfigChooser(8, 8, 8, 8, 16, 0)
       //create  render
       mGLRenderer = MyGLRenderer()
       //set custom GLRender
       setRenderer(mGLRenderer)
       //config GLRender mode
       renderMode = RENDERMODE_WHEN_DIRTY
   }


    var mPreviousX =0f
    var mPreviousY =0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                var dx = x - mPreviousX
                var dy = y - mPreviousY
                // reverse direction of rotation above the mid-line
                if (y > height / 2) {
                    dx *= -1
                }
                // reverse direction of rotation to left of the mid-line
                if (x < width / 2) {
                    dy *= -1
                }
                mGLRenderer.angle = mGLRenderer.angle + (dx + dy) * TOUCH_SCALE_FACTOR // = 180.0f / 320
                requestRender()
            }
        }
        mPreviousX = x
        mPreviousY = y
        return super.onTouchEvent(event)
    }

}