package com.lq.websocket

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okio.ByteString
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    lateinit var client: OkHttpClient
    lateinit var request: Request
    lateinit var webSocket: WebSocket
    lateinit var tv_text: TextView
    val url = "wss://solarbrain.client.com.au/cable"
//    val url = "ws://echo.websocket.org"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_text = findViewById(R.id.tv_text)

        findViewById<View>(R.id.sendData).setOnClickListener {
            _start()
        }

        findViewById<View>(R.id.toJava).setOnClickListener { v: View? ->
            JavaCableActivity.start(
                this
            )
        }
    }


    private fun _start() {
        client = OkHttpClient.Builder()
            .sslSocketFactory(RxUtils.createSSLSocketFactory(), TrustAllCerts())
            .hostnameVerifier(RxUtils.TrustAllHostnameVerifier())
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()
        request = Request.Builder().url(url)
            .addHeader("X-API-EMAIL", "emily.zheng@client.com.au")
            .addHeader("X-API-TOKEN", "oHvng7XbyXVzktZJ1htz")
            .build()
        webSocket = client.newWebSocket(request, EchoWebSocketListener())
        client.dispatcher().executorService().shutdown()
    }


    inner class EchoWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response?) {
            super.onOpen(webSocket, response)
            val message =
                "{\"command\":\"subscribe\",\"identifier\":\"{\"channel\":\"sign_in_channel.NSW\"}\"}"
            webSocket.send(message)
            output("链接成功!")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            output("receive text:$text")
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            val message =
                "{\"command\":\"subscribe\",\"identifier\":\"{\"channel\":\"sign_in_channel.NSW\"}\"}"
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    webSocket.send(message)
                }
            }, 5000)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            output("receive bytes:" + bytes.hex());
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String?) {
            super.onClosed(webSocket, code, reason)
            output("closed:$reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            super.onClosing(webSocket, code, reason)
            output("closing:$reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable?, response: Response?) {
            super.onFailure(webSocket, t, response)
            output("failure:" + t?.message)
        }
    }

    private fun output(text: String) {
        runOnUiThread {
            tv_text?.setText("" + tv_text?.text?.toString() + "\n\n" + text)
        }
    }

}

