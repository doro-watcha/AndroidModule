package com.goddoro.module.presentation.animation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.module.databinding.ImageBinding
import com.goddoro.module.databinding.ItemImageBinding
import com.goddoro.module.presentation.retry.RetryBindingAdapter
import com.goddoro.module.presentation.retry.room.ImageItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 5/18/21
 */

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    private val onClick: PublishSubject<ImageView> = PublishSubject.create()
    val clickItem: Observable<ImageView> = onClick


    var items : List<Unit> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageBinding.inflate(inflater,parent,false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
        return holder.bind( items[position])
    }

    fun submitItems ( items : List<Unit>?){
        this.items = items ?: listOf()
    }

    inner class ImageViewHolder( private val binding : ImageBinding) : RecyclerView.ViewHolder(binding.root){


        init {

            binding.image.setOnClickListener {
                onClick.onNext(binding.image)
            }


        }

        fun bind( item : Unit) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }
    }


}

@BindingAdapter("app_recyclerview_images")
fun RecyclerView.setImageItems(items : List<Unit>?){
    ( adapter as? ImageAdapter)?. run {

        submitItems(items)
    }
}