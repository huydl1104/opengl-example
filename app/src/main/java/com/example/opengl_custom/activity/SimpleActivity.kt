package com.example.opengl_custom.activity

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.opengl_lib.render.Simple1Renderer

/**
 * @author yudongliang
 * create time 2021-08-30
 * describe :
 */
class SimpleActivity :AppCompatActivity(){

    private var mGLSurfaceView: GLSurfaceView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        mGLSurfaceView = GLSurfaceView(this)
        // Check if the system supports OpenGL ES 2.0.
        // Check if the system supports OpenGL ES 2.0.
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            mGLSurfaceView!!.setEGLContextClientVersion(2)

            // Set the renderer to our demo renderer, defined below.
            mGLSurfaceView!!.setRenderer(Simple1Renderer())
        } else {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return
        }
        setContentView(mGLSurfaceView)
    }
}