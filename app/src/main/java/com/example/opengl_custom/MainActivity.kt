package com.example.opengl_custom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opengl_custom.activity.BouncyCubeActivity
import com.example.opengl_custom.activity.OpenGLES30Activity
import com.example.opengl_custom.activity.Simple1Activity
import com.example.opengl_custom.activity.SimpleActivity
import com.example.opengl_custom.adapter.MainAdapter
import com.example.opengl_custom.adapter.MyClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val list = arrayListOf(
        "OpenGLES30Activity",
        "SimpleActivity",
        "Simple1Activity",
        "BouncyCubeActivity"
    )
    private val mainAdapter = MainAdapter(list).apply {
        listener = object : MyClickListener {
            override fun onClick(postion: Int) {
                when(postion){
                    0->{customStartActivity(OpenGLES30Activity::class.java)}
                    1->{customStartActivity(SimpleActivity::class.java)}
                    2->{customStartActivity(Simple1Activity::class.java)}
                    3->{customStartActivity(BouncyCubeActivity::class.java)}
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
    }

    private fun customStartActivity(java: Class<*>) =
            startActivity(Intent(this, java))

}