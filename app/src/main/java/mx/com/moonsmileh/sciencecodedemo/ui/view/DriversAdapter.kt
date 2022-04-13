package mx.com.moonsmileh.sciencecodedemo.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mx.com.moonsmileh.sciencecodedemo.R


class DriversAdapter(private var drivers: List<String>, private val listener: DriversEvents) :
    RecyclerView.Adapter<DriversAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_driver, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDriver = drivers[position]
        holder.tvDriverName.text = currentDriver
        holder.item.setOnClickListener {
            listener.onDriverClickListener(currentDriver)
        }
    }

    override fun getItemCount(): Int = drivers.size

    interface DriversEvents {
        fun onDriverClickListener(driver: String)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDriverName: TextView = view.findViewById(R.id.textView)
        val item: ConstraintLayout = view.findViewById(R.id.item_layout)
    }

}