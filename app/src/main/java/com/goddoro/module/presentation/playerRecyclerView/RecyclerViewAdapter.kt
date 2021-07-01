package com.goddoro.module.presentation.playerRecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.module.databinding.ItemImageBinding
import com.goddoro.module.databinding.ItemVideoBinding
import com.goddoro.module.presentation.retry.RetryBindingAdapter
import com.goddoro.module.presentation.retry.room.ImageItem
import com.goddoro.module.utils.setOnDebounceClickListener
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import io.reactivex.Observable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.subjects.PublishSubject


class RecyclerViewAdapter ( val context : Context) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){

    private val onClick : PublishSubject<VideoItem> = PublishSubject.create()
    val clickItem : Observable<VideoItem> = onClick

    lateinit var player : SimpleExoPlayer



    var items : List<VideoItem> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater,parent,false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerViewAdapter.RecyclerViewHolder, position: Int) {
        return holder.bind( items[position])
    }

    fun submitItems ( items : List<VideoItem>?){
        this.items = items ?: listOf()
    }

    inner class RecyclerViewHolder( private val binding : ItemVideoBinding) : RecyclerView.ViewHolder(binding.root){


        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(items[layoutPosition])
            }

        }

        fun bind( item : VideoItem) {

            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            player = SimpleExoPlayer.Builder(context).build()
            binding.player.player = player

            player.setMediaItem(MediaItem.fromUri(item.url ?: ""))

            player.prepare()
            player.play()


        }
    }


}

@BindingAdapter("app:recyclerview_video_items")
fun RecyclerView.setImageItems(items : List<VideoItem>?){
    ( adapter as? RecyclerViewAdapter)?. run {

        submitItems(items)
    }
}