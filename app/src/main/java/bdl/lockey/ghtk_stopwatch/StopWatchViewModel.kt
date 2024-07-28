package bdl.lockey.ghtk_stopwatch

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopWatchViewModel: ViewModel() {

    private var _elapsedTime = MutableLiveData<String>()
    val elapsedTime: LiveData<String>
        get() = _elapsedTime

    private var _stopWatchList = MutableLiveData<MutableList<StopWatch>>()
    val stopWatchList: LiveData<MutableList<StopWatch>>
        get() = _stopWatchList

    fun setElapsedTime(timeMillis: Long) {
        _elapsedTime.value = formatTime(timeMillis)
    }

    fun setStopWatchList(stopWatchList: MutableList<StopWatch>) {
        _stopWatchList.value = stopWatchList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addStopwatch() {
        stopWatchList.value?.add(StopWatch())
    }


    @SuppressLint("DefaultLocale")
    private fun formatTime(timeMillis: Long): String {
        val seconds = (timeMillis / 1000) % 60
        val minutes = (timeMillis / (1000 * 60)) % 60
        val hours = (timeMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}