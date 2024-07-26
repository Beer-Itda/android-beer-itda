package com.hjiee.presentation.ui.filter.style.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityFilterStyleBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.filter.style.entity.StyleActionEntity
import com.hjiee.presentation.ui.filter.style.entity.StyleClickEntity
import com.hjiee.presentation.ui.filter.style.item.middle.StyleMiddleItemViewHolder
import com.hjiee.presentation.ui.filter.style.view.adapter.StyleMiddleListAdapter
import com.hjiee.presentation.ui.filter.style.view.adapter.StyleSelectedListAdapter
import com.hjiee.presentation.ui.filter.style.view.adapter.StyleSmallListAdapter
import com.hjiee.presentation.ui.filter.style.viewmodel.StyleViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.showToast
import com.google.android.material.tabs.TabLayout
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StyleActivity : BaseActivity<ActivityFilterStyleBinding>(R.layout.activity_filter_style),
//    DiscreteScrollView.OnItemChangedListener<StyleMiddleItemViewHolder>,
    TabLayout.OnTabSelectedListener {

    private var isFirstStart = true
    private val viewModel: StyleViewModel by viewModels()
    private val smallCategoryListAdapter by lazy { StyleSmallListAdapter() }
    private val middleCategoryListAdapter by lazy { StyleMiddleListAdapter() }
    private val selectedStyleAdapter by lazy { StyleSelectedListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.load()
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@StyleActivity.viewModel
//            with(rvFilterMiddleCategory) {
//                adapter = middleCategoryListAdapter
//                lifecycleOwner = this@StyleActivity
//                addOnItemChangedListener(this@StyleActivity)
//                setItemTransformer(
//                    ScaleTransformer.Builder()
//                        .setMinScale(0.7f)
//                        .build()
//                )
//                setOffscreenItems(3)
//                scrollToPosition(0)
//            }
            with(rvFilterStyleSmall) {
                adapter = smallCategoryListAdapter
                lifecycleOwner = this@StyleActivity
            }
            with(rvSelectedStyle) {
                adapter = selectedStyleAdapter
                lifecycleOwner = this@StyleActivity
            }
            with(includeToolbar.toolbar) {
                setOnClickListener {
                    finish()
                }
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.throwable) {
            if(it.first.message.isNullOrEmpty().not()) {
                Toast.makeText(this@StyleActivity, it.first.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is StyleActionEntity.UpdateLarge -> {
                with(binding.tabs) {
                    entity.style.forEach {
                        addTab(newTab().setText(it.largeName))
                    }
                    addOnTabSelectedListener(this@StyleActivity)
                }
            }
            is StyleActionEntity.UpdateMiddle -> {
                middleCategoryListAdapter.clear()
                middleCategoryListAdapter.addAll(entity.style, true)
//                binding.rvFilterMiddleCategory.run {
//                    post { scrollToPosition(0) }
//                }
            }
            is StyleActionEntity.UpdateSmall -> {
                smallCategoryListAdapter.clear()
                smallCategoryListAdapter.addAll(entity.style, true)
            }
            is StyleActionEntity.UpdateSelectedStyleList -> {
                selectedStyleAdapter.clear()
                selectedStyleAdapter.addAll(entity.style, true)
                binding.rvSelectedStyle.run {
                    post { scrollToPosition(0) }
                }
            }
            is StyleActionEntity.ShowToast -> {
                showToast(entity.message)
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is StyleClickEntity.SelectMiddleCategory -> {
            }
            is StyleClickEntity.SelectStyleSet -> {
            }
            is StyleClickEntity.SelectDone -> {
                setResult(RESULT_OK)
                finish()
            }
            is StyleClickEntity.Skip -> {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let { position ->
            viewModel.selectLargeCategory(position)
        }
    }

//    override fun onCurrentItemChanged(
//        viewHolder: StyleMiddleItemViewHolder?,
//        adapterPosition: Int
//    ) {
//        if (!isFirstStart) {
//            viewModel.selectMiddleCategory(adapterPosition)
//        }
//        isFirstStart = false
//    }

    companion object {
        const val REQ_CODE_FILTER = 30

        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, StyleActivity::class.java).apply {
            }
        }
    }
}