package com.rx.rxretroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.logs.loglibrary.LogInfo
import com.rx.rxretroapp.coroutines.view.CoroutineFragment
import com.rx.rxretroapp.coroutines.view.CoroutineImageDownLoadFragment
import com.rx.rxretroapp.extensions.replaceFragmentWithNoHistory
import com.rx.rxretroapp.retrorx.view.RetroRxFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAaarLibrary()
        replaceFragmentWithNoHistory(RetroRxFragment(), R.id.container_fragment)
       // replaceFragmentWithNoHistory(CoroutineFragment(), R.id.container_fragment)
      //  replaceFragmentWithNoHistory(CoroutineImageDownLoadFragment(),R.id.container_fragment)
    }

    fun initAaarLibrary(){
        var logInfo = LogInfo()
        logInfo.displayDebugLog("MainActivity","In MainActivity")

    }
}
