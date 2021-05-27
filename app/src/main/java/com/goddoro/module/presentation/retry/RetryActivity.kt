package com.goddoro.module.presentation.retry

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.goddoro.module.CommonConst.ARG_IMG_URI
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityRetryBinding
import com.goddoro.module.utils.Broadcast
import com.goddoro.module.utils.component.GridSpacingItemDecoration
import com.goddoro.module.utils.component.showTextDoubleDialog
import com.goddoro.module.utils.disposedBy
import com.goddoro.module.utils.observeOnce
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetryActivity : AppCompatActivity() {

    private val TAG = RetryActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivityRetryBinding

    private val mViewModel : RetryViewModel by viewModel()

    private lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRetryBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)


        initView()
        setupRecyclerView()
        observeViewModel()

        setupBroadcast()
        setupSwipeRefreshLayout()
    }

    private fun setupRecyclerView() {

        val imageLayoutManager = GridLayoutManager(this@RetryActivity,3)
        val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
        val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

        val gridSpacing = GridSpacingItemDecoration(3,spacingLeft,spacingTop,0)

        mBinding.imageRecyclerView.apply {

            layoutManager = imageLayoutManager
            addItemDecoration(gridSpacing)
            adapter = RetryBindingAdapter().apply {

                clickItem.subscribe{
                    showTextDoubleDialog(R.string.txt_update,R.string.txt_profile_update, {
                        mViewModel.updateProfileImage(it)
                    },{

                    })
                }.disposedBy(compositeDisposable)

            }
        }
    }

    private fun initView() {
        imageUri = intent.getParcelableExtra(ARG_IMG_URI)!!

        mViewModel.insertImage(imageUri)
    }

    private fun observeViewModel() {

        mViewModel.apply {

            onLoadCompleted.observeOnce(this@RetryActivity){
                mBinding.imageRecyclerView.adapter?.notifyDataSetChanged()

                if ( mBinding.layoutRefresh.isRefreshing) mBinding.layoutRefresh.isRefreshing = false
            }


        }
    }

    private fun setupSwipeRefreshLayout() {
        mBinding.layoutRefresh.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            profileUpdateSuccess.subscribe{
                Toast.makeText(this@RetryActivity,"프로필을 성공적으로 업데이트 했습니다",Toast.LENGTH_SHORT).show()
                mViewModel.refresh()
            }.disposedBy(compositeDisposable)
        }
    }
}