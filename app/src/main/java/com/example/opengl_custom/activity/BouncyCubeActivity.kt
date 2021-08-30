package com.example.opengl_custom.activity

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.opengl_lib.render.BouncyCubeRenderer

/**
 * @author yudongliang
 * create time 2021-08-30
 * describe : 立体感
 */
class BouncyCubeActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val view = GLSurfaceView(this)
        //fix No config chosen error.  No idea what this actually does though.
        view.setEGLConfigChooser(8, 8, 8, 8, 16, 0)

        view.setRenderer(BouncyCubeRenderer(true))
        setContentView(view)
    }
}