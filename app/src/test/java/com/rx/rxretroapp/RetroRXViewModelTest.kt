package com.rx.rxretroapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rx.rxretroapp.retrorx.model.RetroRxModel
import com.rx.rxretroapp.retrorx.repository.APIService
import com.rx.rxretroapp.retrorx.viewmodel.RetroRXViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
//https://www.codexpedia.com/android/unit-test-retrofit-2-rxjava-2-and-livedata-in-android/
@RunWith(MockitoJUnitRunner::class)
class RetroRXViewModelTest {

    // The below rule executes the tasks instantly without waiting on BackGround or mainThread
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: APIService

    @InjectMocks
    lateinit  var retroRXViewModel:RetroRXViewModel

    private lateinit var single: Single<List<RetroRxModel>>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchRetroInfoTest_success(){
        var retroRxModel = RetroRxModel("tile","body","1")
        var retroRXModelList = ArrayList<RetroRxModel>()
        retroRXModelList.add(retroRxModel)
        single =  Single.just(retroRXModelList)
        Mockito.`when`(apiService.makeRequest()).thenReturn(single)
        retroRXViewModel.fetchRetroInfo()
        Assert.assertEquals(1,retroRXViewModel.postInfoLiveData.value!!.size)

    }

    @Test
    fun fetchRetroInfoTest_Failure_Scenario(){
        single = Single.error(Throwable())
        Mockito.`when`(apiService.makeRequest()).thenReturn(single)
        retroRXViewModel.fetchRetroInfo()
        //Assert.assertNull(object : Any??)
    }

    @Before
    fun setUpRXSchedulers(){
        retroRXViewModel = RetroRXViewModel()
        var immediateThinScheduler  = object: Scheduler(){

            override fun createWorker(): Worker {
                  return  ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediateThinScheduler }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediateThinScheduler }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediateThinScheduler }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediateThinScheduler }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediateThinScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediateThinScheduler }

    }


}