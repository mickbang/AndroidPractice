package com.lq.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lq.practice.titlebar.TitleBarActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn_button).setOnClickListener {
            startActivity(Intent(this, TitleBarActivity::class.java))
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.baidu.com")
            .build()
        val userService = retrofit.create(UserService::class.java)
        val repos = userService.getUser()
        repos.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


    fun request(){
        val client = OkHttpClient.Builder().build()

        val request  = Request.Builder().build()

        client.newCall(request).enqueue(object :okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                TODO("Not yet implemented")
            }
        })



        val list = LinkedList<Any>()

    }
}