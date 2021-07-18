package com.sirvi.xwalls

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val background =object :Thread(){
            override fun run() {
                try {
                    sleep(1000)
                 val intent =Intent(baseContext,MainActivity::class.java)
                    startActivity(intent)
                }

                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
