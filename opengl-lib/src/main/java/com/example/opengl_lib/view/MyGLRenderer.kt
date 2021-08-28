package com.example.opengl_lib.view

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author yudongliang
 * create time 2021-08-27
 * describe : 自定义渲染器
 */
class MyGLRenderer :GLSurfaceView.Renderer{

    companion object{
        const val  TAG = "MyGLRenderer"
    }

    private var mAngle = 0f


    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private val mMVPMatrix = FloatArray(16)
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mRotationMatrix = FloatArray(16)
    /**
     * create be called
     */
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        //reset draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        //set camera position (view matrix)
        Matrix.setLookAtM(mViewMatrix,0,
                0f,0f,-3f,
                0f,0f,0f,
                0f,1.0f,0f
        )
        //calculate the projection and transformation (计算投影和转换)
        Matrix.multiplyMM(mMVPMatrix,0,
                mProjectionMatrix,0,
                mViewMatrix, 0)
    }

    /**
     * window size change be called.
     */
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

    }

    /**
     * start draw frame
     */
    override fun onDrawFrame(gl: GL10) {

    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    fun getAngle(): Float {
        return mAngle
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    fun setAngle(angle: Float) {
        mAngle = angle
    }
}