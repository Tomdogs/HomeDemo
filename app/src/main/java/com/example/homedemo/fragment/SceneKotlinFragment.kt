package com.example.homedemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.homedemo.R
import com.example.homedemo.net.ImageNetworkService
import kotlinx.android.synthetic.main.fragment_scene_kotlin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SceneKotlinFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SceneKotlinFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SceneKotlinFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        Log.i("协程","主线程id：${activity?.mainLooper?.thread?.id}")
//        test2()
//        Log.i("协程","协程执行结束")
    }

 /*   private fun test() = runBlocking{
        repeat(8){
            Log.i("协程","协程执行$it 线程id:${Thread.currentThread().id}")
            kotlinx.coroutines.delay(1000)
        }
    }
    fun coroutineTest(){

        GlobalScope.launch {

            for(i in 1..8){

                Log.i("协程","第一次打印，$i")

            }

            kotlinx.coroutines.delay(500)

            for(i in 1..8){

                Log.i("协程","第二次打印，$i")

            }
        }
    }
    fun test2(){

        val job = GlobalScope.launch(Dispatchers.Main) {
            repeat(8){
                Log.i("协程","协程执行$it 线程id:${Thread.currentThread().id}")
                kotlinx.coroutines.delay(1000)
            }
            Log.i("协程","协程执行结束，线程id ${Thread.currentThread().id}")
        }

        Log.i("协程","主线程执行结束")
    }
*/




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initView()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scene_kotlin, container, false)
    }

    /**
     * 加载图片
     */
    private fun initView(){

        GlobalScope.launch(Dispatchers.Main) {
            val url = async { ImageNetworkService.apiService.getImage() }
            val url2 = async { ImageNetworkService.apiService.getImage() }
            val url3 = async { ImageNetworkService.apiService.getImage() }
            Log.i("协程","url:"+url.await().imgurl)

            Glide.with(this@SceneKotlinFragment).load(url.await().imgurl).into(imageView1)
            Glide.with(this@SceneKotlinFragment).load(url2.await().imgurl).into(imageView2)
            Glide.with(this@SceneKotlinFragment).load(url3.await().imgurl).into(imageView3)
        }
        Log.i("协程","主线程id：${activity?.mainLooper?.thread?.id}")
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SceneKotlinFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SceneKotlinFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
