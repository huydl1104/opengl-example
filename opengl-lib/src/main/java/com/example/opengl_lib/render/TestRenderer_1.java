package com.example.opengl_lib.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class TestRenderer_1 implements GLSurfaceView.Renderer {
    private int program;
    private int vPosition;
    private int uColor;
    /**
     * 使用指定代码构建 shader
     * @param shaderType shader 的类型，GLES30.GL_VERTEX_SHADER(顶点着色器)、 GLES30.GL_FRAGMENT_SHADER(片元着色器)
     * @param sourceCode shader 的创建脚本
     * @return 创建的 shader 的索引，创建失败会返回 0
     */
    private int loadShader(int shaderType,String sourceCode) {
        //创建一个空的shader
        int shader = GLES30.glCreateShader(shaderType);
        if (shader != 0) {
            GLES30.glShaderSource(shader, sourceCode);
            GLES30.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e("ES20_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("ES20_ERROR", GLES30.glGetShaderInfoLog(shader));
                GLES30.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * 创建程序
     * @param vertexSource：顶点着色器的资源代码
     * @param fragmentSource：片元着色器的资源代码
     * @return 返回值 0 创建失败
     */
    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        int pixelShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES30.glCreateProgram();
        if (program != 0) {
            //将 顶点着色器 添加到程序中
            GLES30.glAttachShader(program, vertexShader);
            //将 片元着色器 添加到程序中
            GLES30.glAttachShader(program, pixelShader);
            //建立连接
            GLES30.glLinkProgram(program);
            int[] linkStatus = new int[1];
            //可以查询shader的状态信息，
            //GL_SHADER_TYPE：shader type
            //GL_DELETE_STATUS：删除的状态
            //GL_COMPILE_STATUS：shader编译的状态
            //GL_LINK_STATUS：连接的状态
            //GL_INFO_LOG_LENGTH：存储日志信息所有需要的字符缓冲区的大小
            //GL_SHADER_SOURCE_LENGTH：存储着色器源码的字符串缓冲区的大小
            GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES30.GL_TRUE) {
                Log.e("ES20_ERROR", "Could not link program: ");
                Log.e("ES20_ERROR", GLES30.glGetProgramInfoLog(program));
                GLES30.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }
    private FloatBuffer getVertices() {
        float vertices[] = {
                0.0f,   0.5f,0.0f,
                -0.5f, -0.5f,0.0f,
                0.5f,  -0.5f,0.0f,
        };
        //vertices.length*4是因为一个float占四个字节
        //创建ByteBuffer并设置字节的顺序，转换为Float缓冲
        FloatBuffer vertexBuf = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuf.put(vertices).position(0);
        return vertexBuf;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        program = createProgram(verticesShader, fragmentShader);
        //用于获取顶点属性索引(分配的位置)
        vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        uColor = GLES30.glGetUniformLocation(program, "uColor");
        //设置清屏的颜色，真正清屏的是 glClear()
        GLES30.glClearColor(1.0f, 0, 0, 1.0f);
    }
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //window窗口大小的改变
        GLES30.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //获取顶点的坐标
        FloatBuffer vertices = getVertices();
        //绘制前清屏
        //GL_COLOR_BUFFER_BIT: 颜色缓冲
        //GL_DEPTH_BUFFER_BIT: 深度缓冲
        //GL_STENCIL_BUFFER_BIT: 模板缓冲
        //GL_ACCUM_BUFFER_BIT: 累积缓冲(Android 的 OpenGL ES 版本中不存在这种标志位)
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        //使用程序,若program为0，任何getDrawArrays、getDrawElements 都会提示没有定义
        GLES30.glUseProgram(program);
        //为画笔指定顶点位置数据(vPosition)，数据传入 GPU 中。vPosition 可以理解成在 GPU 中的位置，而 vertices 是在 CPU 缓冲区中的数据
        GLES30.glVertexAttribPointer(vPosition, 3, GLES30.GL_FLOAT, false, 0, vertices);
        //设置渲染器允许访问GPU中的数据
        GLES30.glEnableVertexAttribArray(vPosition);
        //使用glUniform4f设置数据，因为c语言中不支持重载，所以会有很多名字相同的后缀不同的函数
        GLES30.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3);
    }

    private static final String verticesShader
            = "attribute vec3 vPosition;            \n"
            + "void main(){                         \n"
            + "   gl_Position = vec4(vPosition,1);  \n"
            + "}";

    private static final String fragmentShader
            = "precision mediump float;         \n"
            + "uniform vec4 uColor;             \n"
            + "void main(){                     \n"
            + "   gl_FragColor = uColor;        \n"
            + "}";
}
/*
 *在 OpenGL ES 中，以下情况链接可能会失败：
 *1、顶点着色器和片元着色器都不在程序对象中
 *2、超过支持的活动属性变量的数量
 *3、超出统一变量(uniform)的存储限制和数量限制
 *4、顶点着色器或片元着色器的主要功能缺失
 *5、在片着色器中实际使用的变量在点着色器中没有以相同方式声明（或者根本没有声明）
 *6、未正确赋值函数或变量名称的引用
 *7、共享的全局声明有两种不同的类型或两种不同的初始值
 *8、一个或多个附加的着色器对象尚未成功编译(glCompileShader 方法)，或者未成功加载预编译的着色器二进制文件(通过 glShaderBinary 方法)
 *9、绑定通用属性矩阵会导致，矩阵的某些行落在允许的最大值 GL_MAX_VERTEX_ATTRIBS 之外，找不到足够的连续顶点属性槽来绑定属性矩阵
 *   默认情况下，处于性能考虑所有顶点着色器的属性都是关闭的，也就是数据在着色器中是不可见的，哪怕数据已经上传到GPU，必须执行 glEnableVertexAttribArray
 *函数启动指定的属性，才可以在着色器中访问顶点的属性和数据，
 *
 * glDrawArrays函数：采用顶点数组的方式绘制图形，根据顶点数组中的坐标数据和指定的模式进行绘制，常用的模式为：
 * 1、GL_POINT：点模式，单独的将顶点画出来
 * 2、GL_LINES：直线模式。单独地将直线画出来
 * 3、GL_LINE_LOOP：环线模式。连贯地将直线画出来，会自动将最后一个顶点和第一个顶点通过直线连接起来
 * 4、GL_LINE_STRIP：连续直线模式。连贯地将直线画出来。即 P0、P1 确定一条直线，P1、P2 确定一条直线，P2、P3 确定一条直线。
 * 5、GL_TRIANGLES：三角形模式。这个参数意味着 OpenGL 使用三个顶点来组成图形。所以，在开始的三个顶点，将用顶点1，顶点2，顶点3来组成一个三角形。完成后，再用下一组的三个顶点(顶点4，5，6)来组成三角形，直到数组结束。
 * 6、GL_TRIANGLE_STRIP：连续三角形模式。用上个三角形开始的两个顶点，和接下来的一个点，组成三角形。也就是说，P0，P1，P2 这三个点组成一个三角形，P1，P2，P3 这三个点组成一个三角形，P2，P3，P4 这三个点组成一个三角形。
 * 7、GL_TRIANGLE_FAN：三角形扇形模式。跳过开始的2个顶点，然后遍历每个顶点，与它们的前一个，以及数组的第一个顶点一起组成一个三角形。也就是说，对于 P0，P1，P2，P3，P4 这 5 个顶点。绘制逻辑如下：
        7.1、跳过P0, P1, 从 P2 开始遍历
        7.2、找到 P2, 与 P2 前一个点 P1，与列表第一个点 P0 组成三角形：P0、P1、P2
        7.3、找到 P3, 与 P3 前一个点 P2，与列表第一个点 P0 组成三角形：P0、P2、P3
        7.4、找到 P4, 与 P4 前一个点 P3，与列表第一个点 P0 组成三角形：P0、P3、P4
 */