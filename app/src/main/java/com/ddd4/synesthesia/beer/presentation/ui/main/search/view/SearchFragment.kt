package com.ddd4.synesthesia.beer.presentation.ui.main.search.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentSearchBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.search.model.SearchActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.search.model.SearchSelectEvent
import com.ddd4.synesthesia.beer.presentation.ui.main.search.viewmodel.SearchViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showKeyboard
import com.ddd4.synesthesia.beer.util.ext.start
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.util.listener.setOnDebounceClickListener
import com.hjiee.core.util.log.L
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()

    private val titleAdapter by lazy { SearchAdapter() }
    private val imageAdapter by lazy { SearchAdapter() }
    private lateinit var titleEndlessScroll: EndlessRecyclerViewScrollListener
    private lateinit var imageEndlessScroll: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }

    override fun initBind() {
        titleEndlessScroll =
            object : EndlessRecyclerViewScrollListener(binding.rvOnlyTitle.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadMore()
                }
            }
//        imageEndlessScroll =
//            object : EndlessRecyclerViewScrollListener(binding.rvWithImage.layoutManager) {
//                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
//                    viewModel.loadMore()
//                }
//            }

        binding.apply {
            viewModel = this@SearchFragment.viewModel
            ibClear.setOnDebounceClickListener {
                this@SearchFragment.viewModel.clear()
                context?.showKeyboard(etSearch)
            }
            etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
                var handle = false
                when (actionId) {
                    IME_ACTION_SEARCH -> {
                        handle = true
                        this@SearchFragment.viewModel.refresh()
                    }
                }
                handle
            }
//            rvWithImage.addOnScrollListener(imageEndlessScroll)
//            srvSearchImage.setOnRefreshListener { this@SearchFragment.viewModel.refresh() }
//            srvSearchTitle.setOnRefreshListener { this@SearchFragment.viewModel.refresh() }

            with(rvOnlyTitle) {
                adapter = titleAdapter
                addOnScrollListener(titleEndlessScroll)
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is SearchActionEntity.UpdateList -> {
                titleAdapter.clear()
                titleAdapter.addAll(entity.items)
            }
            is SearchActionEntity.Refresh -> {
                titleEndlessScroll.resetState()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is SearchSelectEvent.SelectItem -> {
                start<BeerDetailActivity>(
                    intent = BeerDetailActivity.getIntent(
                        context = requireContext(),
                        beerId = entity.beer.id
                    )
                )
            }
            is SearchSelectEvent.Inquire -> {

            }
        }
    }
}