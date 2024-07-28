package bdl.lockey.ghtk_stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import bdl.lockey.ghtk_stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), IClickItemTimerListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StopWatchAdapter
    private val stopWatchList = mutableListOf<StopWatch>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StopWatchAdapter(stopWatchList, this)
        binding.recyclerView.adapter = adapter

        getController()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getController() {
        binding.btnAdd.setOnClickListener {
            adapter.addStopwatch()
            adapter.notifyDataSetChanged()
        }

        binding.btnStart.setOnClickListener {
            for (stopWatch  in stopWatchList) {
                // Chi dong ho nao chua chay moi co the khoi dong
                if (stopWatch.elapsedTime == 0L) {
                    onClickContinue(stopWatchList.indexOf(stopWatch))
                }
            }
        }

        binding.btnPause.setOnClickListener {
            for (stopWatch  in stopWatchList) {
                onClickPause(stopWatchList.indexOf(stopWatch))
            }
        }

        binding.btnContinue.setOnClickListener {
            for (stopWatch  in stopWatchList) {
                // Chi dong ho nao da chay va tam dung thi moi chay tiep
                if (!stopWatch.isRunning && stopWatch.elapsedTime != 0L) {
                    onClickContinue(stopWatchList.indexOf(stopWatch))
                }

            }
        }

        binding.btnRestart.setOnClickListener {
            for (stopWatch  in stopWatchList) {
                onClickRestart(stopWatchList.indexOf(stopWatch))
            }
        }
    }

    // Khoi chay hoac tiep tuc
    override fun onClickContinue(position: Int) {
        val stopwatch = stopWatchList[position]
        startStopwatch(stopwatch)
//        if (stopwatch.isRunning) {
//            stopStopwatch(stopwatch)
//        } else {
//            startStopwatch(stopwatch)
//        }
        adapter.notifyItemChanged(position)
    }

    // Tam dung
    override fun onClickPause(position: Int) {
        val stopwatch = stopWatchList[position]
        stopStopwatch(stopwatch)
        adapter.notifyItemChanged(position)
    }

    // Khoi dong lai dong ho
    override fun onClickRestart(position: Int) {
        stopWatchList[position].isRunning = false
        stopWatchList[position].elapsedTime = 0L
        stopWatchList[position].job?.cancel()
        adapter.notifyItemChanged(position)
    }

    // Xoa
    @SuppressLint("NotifyDataSetChanged")
    override fun onClickDelete(position: Int) {
        stopWatchList[position].job?.cancel()
        stopWatchList.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    // Khoi chay dong ho
    @SuppressLint("NotifyDataSetChanged")
    private fun startStopwatch(stopWatch: StopWatch) {
        stopWatch.isRunning = true
        stopWatch.job = coroutineScope.launch {
            val startTime = System.currentTimeMillis() - stopWatch.elapsedTime
            while (stopWatch.isRunning) {
                stopWatch.elapsedTime = System.currentTimeMillis() - startTime
                adapter.notifyItemChanged(stopWatchList.indexOf(stopWatch))
                // Sd notifyDataSetChange() se gay ra chan luong vi ve lai toan bo danh sach
                // adapter.notifyDataSetChanged()
                delay(100L)
            }
        }
    }


    // Dung dong ho
    private fun stopStopwatch(stopwatch: StopWatch) {
        stopwatch.isRunning = false
        stopwatch.job?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}