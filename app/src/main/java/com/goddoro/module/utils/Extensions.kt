package com.goddoro.module.utils

import androidx.fragment.app.Fragment
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlin.reflect.KClass


/**
 * created By DORO 5/7/21
 */

@Suppress("UNCHECKED_CAST")
fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filterNot { it.value == null } as HashMap<String, Any>
}

open class Once<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun <T> LiveData<Once<T>>.observeOnce(lifecycle: LifecycleOwner, listener: (T) -> Unit) {
    this.observe(lifecycle, Observer {
        it?.getContentIfNotHandled()?.let {
            listener(it)
        }
    })
}

inline fun <reified T> AppCompatActivity.startActivity(clazz: KClass<out T>, flags: Int? = null) where T : AppCompatActivity {
    val intent = Intent(this, clazz.java).apply {
        flags?.let { this.flags = it }
    }
    startActivity(intent)
}

inline fun <reified T> Fragment.startActivity(clazz: KClass<out T>, flags: Int? = null) where T : AppCompatActivity {
    val intent = Intent(requireActivity(), clazz.java).apply {
        flags?.let { this.flags = it }
    }
    startActivity(intent)
}

@BindingAdapter("imageUri")
fun ImageView.setImageUri( uri : String) {
    val imageUri = uri.toUri()
    Glide.with(context).load(imageUri).into(this)
}

