package com.rx.rxretroapp.retrorx.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rx.rxretroapp.retrorx.viewmodel.RetroRXViewModel
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.logs.loglibrary.LogInfo
import com.rx.rxretroapp.retrorx.model.RetroRxModel

class RetroRxFragment : Fragment(), LifecycleOwner{

    lateinit var fragmentView: View
    private val viewModel by activityViewModels<RetroRXViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAaarLibrary()
        activity?.lifecycle?.addObserver(viewModel)
    }

    override fun onResume() {
        super.onResume()
         fetchRetroInfo();

    }
    fun initAaarLibrary(){
        var logInfo = LogInfo()
        logInfo.displayDebugLog("RetroRXFragment","In MainActivity")
        logInfo.displayToast(activity!!,"Welcome to RetroFragment")

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    private fun fetchRetroInfo() {

        viewModel.postInfoLiveData.observe(this,object : Observer<List<RetroRxModel>>{
            override fun onChanged(t: List<RetroRxModel>?) {
                Log.d("RetroRXFragment", "RetroInfo::::$t")
            }
        })

    }
}