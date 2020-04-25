package com.rx.rxretroapp.retrorx.viewmodel


import androidx.lifecycle.*
import com.rx.rxretroapp.di.DaggerRetroRxComponent
import com.rx.rxretroapp.retrorx.model.RetroRxModel
import com.rx.rxretroapp.retrorx.repository.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class RetroRXViewModel : ViewModel(), LifecycleObserver {

    var postInfoLiveData: LiveData<List<RetroRxModel>> = MutableLiveData()
    var postLoadError : LiveData<Boolean> = MutableLiveData()
    var loading : LiveData<Boolean> = MutableLiveData()
    var compositeDisposable:CompositeDisposable = CompositeDisposable()
    var  job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler{
            _, throwable ->

        onError("Exception : ${throwable.localizedMessage}")
    }
    @Inject
    lateinit var apiService: APIService


    init {
        DaggerRetroRxComponent.create().inject(this)
    }
  ///  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
     fun fetchRetroInfo(){
        compositeDisposable.add(apiService.makeRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<RetroRxModel>>(){
                override fun onSuccess(t: List<RetroRxModel>) {
                    val mutableLiveData:MutableLiveData<List<RetroRxModel>> = MutableLiveData()
                    mutableLiveData.value =  t
                    postInfoLiveData = mutableLiveData
                }

                override fun onError(e: Throwable) {
                       e.printStackTrace()
                }
            })
                                  )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun fetchRetroResponseThroughCoroutine(){
      job =   CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = apiService.fetchPosts()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val mutableLiveData:MutableLiveData<List<RetroRxModel>> = MutableLiveData()
                    mutableLiveData.value =  response.body()
                    postInfoLiveData = mutableLiveData
                }else{
                    onError("Response Error : ${response.message()}")
                }
            }
        }

    }

    private fun onError(message:String){
        println(message)
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        job?.cancel()
    }



        /*MutableLiveData().also {
                    loadUsers()
                }*/


}