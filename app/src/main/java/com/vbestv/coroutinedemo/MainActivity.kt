package com.vbestv.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.vbestv.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count: Int =1

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    suspend fun performTask(tasknumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5_000)
            return@async "Finished Coroutine ${tasknumber}"
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            launchCoroutines(binding.root)
        }
        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                count = progress
                binding.textView.text = "${count} coroutines"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

    }

    fun launchCoroutines(view: View) {

        (1..count).forEach {
            binding.textView2.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                binding.textView2.text = performTask(it).await()
            }
        }
    }

}