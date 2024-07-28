package bdl.lockey.ghtk_stopwatch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bdl.lockey.ghtk_stopwatch.databinding.ItemStopWatchBinding

class StopWatchAdapter(
    private val dataset: MutableList<StopWatch>,
    private val iClickItemTimerListener: IClickItemTimerListener
) : RecyclerView.Adapter<StopWatchAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemStopWatchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemStopWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = dataset[position]
        val view = holder.binding
        view.tvTime.text = formatTime(item.elapsedTime)

        view.btnContinue.setOnClickListener {
            iClickItemTimerListener.onClickContinue(position)
//            if (item.isRunning) {
//                view.btnContinue.setImageResource(R.drawable.ic_start)
//                iClickItemTimerListener.onClickPause(position)
//            } else {
//                view.btnContinue.setImageResource(R.drawable.ic_pause)
//                iClickItemTimerListener.onClickContinue(position)
//            }
        }

        view.btnPause.setOnClickListener {
            iClickItemTimerListener.onClickPause(position)
        }

        view.btnRestart.setOnClickListener {
            view.tvTime.text = "00:00:00"
            iClickItemTimerListener.onClickRestart(position)
        }

        view.btnDelete.setOnClickListener {
            iClickItemTimerListener.onClickDelete(position)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addStopwatch() {
        dataset.add(StopWatch())
        notifyDataSetChanged()
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(timeMillis: Long): String {
        val seconds = (timeMillis / 1000) % 60
        val minutes = (timeMillis / (1000 * 60)) % 60
        val hours = (timeMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


}