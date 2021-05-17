package com.goddoro.module.presentation.retry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.module.databinding.ItemImageBinding
import androidx.databinding.library.baseAdapters.BR
import com.goddoro.module.presentation.retry.room.ImageItem

/**
 * Created by goddoro on 2021-05-17.
 */

class RetryBindingAdapter : RecyclerView.Adapter<RetryBindingAdapter.RetryViewHolder>(){


    var items : List<ImageItem> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RetryBindingAdapter.RetryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater,parent,false)

        return RetryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RetryBindingAdapter.RetryViewHolder, position: Int) {
        return holder.bind( items[position])
    }

    fun submitItems ( items : List<ImageItem>?){
        this.items = items ?: listOf()
    }

    inner class RetryViewHolder( private val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){


        init {


        }

        fun bind( item : ImageItem) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }
    }


}

@BindingAdapter("app_recyclerview_image_item")
fun RecyclerView.setImageItems(items : List<ImageItem>?){
    ( adapter as? RetryBindingAdapter)?. run {

        submitItems(items)
    }
}