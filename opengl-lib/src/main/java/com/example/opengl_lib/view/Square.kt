package com.example.opengl_lib.view

import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * @author yudongliang
 * create time 2021-08-27
 * describe : 二维矩形
 */
class Square {
    private val vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // The matrix must be included as a modifier of gl_Position.
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"

    private val vertexBuffer: FloatBuffer? = null
    private val drawListBuffer: ShortBuffer? = null
    private val mProgram = 0
    private val mPositionHandle = 0
    private val mColorHandle = 0
    private val mMVPMatrixHandle = 0

    // number of coordinates per vertex in this array
    private val COORDS_PER_VERTEX = 3

    var squareCoords = floatArrayOf(
            -0.5f, 0.5f, 0.0f,  // top left
            -0.5f, -0.5f, 0.0f,  // bottom left
            0.5f, -0.5f, 0.0f,  // bottom right
            0.5f, 0.5f, 0.0f) // top right

    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex
    var color = floatArrayOf(0.2f, 0.709803922f, 0.898039216f, 1.0f)

    init {
//        ByteBuffer.allocateDirect()
    }










}