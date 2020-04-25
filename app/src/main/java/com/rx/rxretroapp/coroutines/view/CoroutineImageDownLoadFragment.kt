package com.rx.rxretroapp.coroutines.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rx.rxretroapp.R
import kotlinx.android.synthetic.main.fragment_coroutine_image_down_load.*
import kotlinx.coroutines.*
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoroutineImageDownLoadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoroutineImageDownLoadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
   private  val  imageURL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"
   private  val coroutineScope = CoroutineScope(Dispatchers.Main)
    lateinit  var myview:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview =  inflater.inflate(R.layout.fragment_coroutine_image_down_load, container, false)
        coroutineScope.launch {

               var deferredImage: Deferred<Bitmap> = coroutineScope.async(Dispatchers.IO){
                   fetchImageFromRemote()
               }
            var fetchedBitmap:Bitmap = deferredImage.await()
            imageView2.setImageBitmap(fetchedBitmap)
            progressBar.visibility = View.GONE
        }
        return  myview
    }


    private fun fetchImageFromRemote():Bitmap =
        URL(imageURL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoroutineImageDownLoadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoroutineImageDownLoadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}