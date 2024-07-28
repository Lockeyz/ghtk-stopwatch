package bdl.lockey.ghtk_stopwatch

interface IClickItemTimerListener {
    fun onClickPause(position: Int)
    fun onClickRestart(position: Int)
    fun onClickContinue(position: Int)
    fun onClickDelete(position: Int)
}