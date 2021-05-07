package com.lq.module_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "执行前:onCreate: 主线程id:${mainLooper.thread.id}")
        testCoroutine()
        Log.i(TAG, "执行后:onCreate: 主线程id:${mainLooper.thread.id}")
    }


    private fun testCoroutine() {
//        GlobalScope.launch {
//            repeat(8) {
//                Log.i(TAG, "testCoroutine:执行线程id$it:${Thread.currentThread().id}")
//                delay(1000)
//            }
//        }

//        runBlocking {
//            repeat(20) {
//                Log.i(TAG, "testCoroutine:执行线程id$it:${Thread.currentThread().id}")
//                delay(1000)
//            }
//        }

        runBlocking {
//            val job = GlobalScope.launch {
//                println("Throwing exception from launch")
//                throw IndexOutOfBoundsException()
//            }
//
//            job.join()
//            println("Joined failed job")

            val deferred = GlobalScope.async {
                println("Throwing exception from async")
                throw ArithmeticException() // 没有打印任何东西，依赖用户去调用等待
            }
            try {
                deferred.await()
                println("Unreached")
            } catch (e: ArithmeticException) {
                println("Caught ArithmeticException")
            }
        }

    }
}