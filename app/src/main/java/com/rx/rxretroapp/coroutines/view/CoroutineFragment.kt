package com.rx.rxretroapp.coroutines.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rx.rxretroapp.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoroutineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoroutineFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        asyncDemo()
       launchBasicCoroutine()
        useRunBlockScope()
        launchFunctionalCoroutine()
        dispatcherDemo()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coroutine, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoroutineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoroutineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun launchBasicCoroutine(){

         GlobalScope.launch {
             delay(3000)
             println("World !!!")
         }

        print("Hello,")
        Thread.sleep(4000)
    }

    private fun launchFunctionalCoroutine(){
         GlobalScope.launch(CoroutineName("Suspendable")) {

             sayHello()
         }

        GlobalScope.launch {
            sayWelcom()
        }


    }

    private fun  useRunBlockScope(){
        runBlocking {
           repeat(5){
                launch (CoroutineName("MyCorountine")) {
                      println("My Coroutine Name is ${coroutineContext[CoroutineName.Key]}")
                      println('.')

             }
            }
        }
    }

    private suspend fun sayHello(){
        delay(2000)
        println("Hello Suspended function")
    }

    private suspend fun sayWelcom(){
        delay(2000)
        println("Welcome to Suspended fncns")
    }


    private  fun dispatcherDemo(){

           runBlocking {

                launch(Dispatchers.Main) {
                    println("Main Dispatcher ${Thread.currentThread().name}")
                }

               launch(Dispatchers.IO) {
                   println("IO Dispatcher ${Thread.currentThread().name}")
               }

               launch(Dispatchers.Default) {
                   println("CPU Intense  Dispatcher ${Thread.currentThread().name}")
               }

               launch(Dispatchers.Unconfined) {
                   println("UnConfined Intense  Dispatcher ${Thread.currentThread().name}")
                   delay(1000L)
                   println("UnConfined2 Intense  Dispatcher ${Thread.currentThread().name}")
               }
               launch(newSingleThreadContext("MyThread")) {
                   println("newSingleThreadContext Intense  Dispatcher ${Thread.currentThread().name}")
               }
           }
    }


    private fun asyncDemo(){
   println("Asysnc Demo starts......")
         runBlocking {

              val firstVal : Deferred<Int> = async { fetchFirstVal() }
             val secondVal : Deferred<Int> = async { fetchSecondVal() }

              println("Doing some processing")
             delay(500L)

             println("WAiting for vals")
             println("Sum of Awaited Vals ${firstVal.await()+secondVal.await()}")

         }
    }

    private  suspend fun fetchFirstVal() : Int{
        delay(2000L)
        return  Random.nextInt(500)
    }

    private suspend fun fetchSecondVal() : Int{

        delay(3000L)
        return  Random.nextInt(100)
    }

}