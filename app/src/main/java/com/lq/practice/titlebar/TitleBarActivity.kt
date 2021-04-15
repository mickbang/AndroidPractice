package com.lq.practice.titlebar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.lq.practice.R
import com.lq.titlebar.TitleBar

private const val TAG = "TitleBarActivity"

class TitleBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_bar)

        val titlebar1 = findViewById<TitleBar>(R.id.titleBar1)
        titlebar1.onLeftClickListener = View.OnClickListener {
            Log.d(TAG, "点击左边的图标")
            Toast.makeText(this, "点击左边的图标", Toast.LENGTH_SHORT).show()
        }
        titlebar1.onRightTextClickListener =
            View.OnClickListener {
                Log.d(TAG, "点击右边的文字")
                Toast.makeText(this, "点击右边的文字", Toast.LENGTH_SHORT).show()
            }
        titlebar1.onRightImageClickListener =
            View.OnClickListener {
                Log.d(TAG, "点击右边的图标")
                Toast.makeText(this, "点击右边的图标", Toast.LENGTH_SHORT).show()
            }


        val titlebar2 = findViewById<TitleBar>(R.id.titleBar2)
        titlebar2.onLeftClickListener =
            View.OnClickListener {
                Log.d(TAG, "点击左边的图标")
                Toast.makeText(this, "点击左边的图标", Toast.LENGTH_SHORT).show()
            }
        titlebar2.onRightTextClickListener =
            View.OnClickListener {
                Log.d(TAG, "点击右边的文字")
                Toast.makeText(this, "点击右边的文字", Toast.LENGTH_SHORT).show()
            }
        titlebar2.onRightImageClickListener =
            View.OnClickListener {
                Log.d(TAG, "点击右边的图标")
                Toast.makeText(this, "点击右边的图标", Toast.LENGTH_SHORT).show()
            }


    }
}