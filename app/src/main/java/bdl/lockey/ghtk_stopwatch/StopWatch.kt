package bdl.lockey.ghtk_stopwatch
import kotlinx.coroutines.Job

data class StopWatch(
    var isRunning: Boolean = false,
    var elapsedTime: Long = 0L,
    var job: Job? = null
)
