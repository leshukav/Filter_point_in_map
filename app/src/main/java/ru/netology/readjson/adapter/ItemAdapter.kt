package ru.netology.readjson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.readjson.databinding.ItemServiceBinding

class ItemAdapter(private val listener: Listener) : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private val list = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewBinding =
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(viewBinding, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(list[position])
    }

    class ItemHolder(private val viewBinding: ItemServiceBinding, private val listener: Listener) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(services: String) {
            val text = "Services -->   ${services}"
            viewBinding.tvService.text = text
            viewBinding.cardItem.setOnClickListener {
                listener.onClick(services)
            }
        }
    }

    fun updateData(newList: List<String>) {
        list.clear()
        list.addAll(newList)
    }

    interface Listener {
        fun onClick(services: String)
    }
}



