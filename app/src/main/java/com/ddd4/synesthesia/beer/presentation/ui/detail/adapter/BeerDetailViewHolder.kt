package com.ddd4.synesthesia.beer.presentation.ui.detail.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.info.BeerDetailInfoItemViewViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.related.BeerDetailRelatedItemViewViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.review.BeerDetailReviewItemViewViewHolder

abstract class BeerDetailViewHolder<VM : Any, B : ViewDataBinding>(
    itemView: View
) : BaseBindingViewHolder<VM, B>(itemView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(
            parent: ViewGroup,
            viewType: DetailViewType
        ): BeerDetailViewHolder<IBeerDetailViewModel, ViewDataBinding> {
            return when (viewType) {
                DetailViewType.INFORMATION -> BeerDetailInfoItemViewViewHolder.newInstance(parent)
                DetailViewType.REVIEW -> BeerDetailReviewItemViewViewHolder.newInstance(parent)
                DetailViewType.RELATED -> BeerDetailRelatedItemViewViewHolder.newInstance(parent)
            } as BeerDetailViewHolder<IBeerDetailViewModel, ViewDataBinding>
        }
    }
}