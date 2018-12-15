package com.justcode.hxl.stack_card


import androidx.cardview.widget.CardView
import com.justcode.hxl.lucky_recycleview.luckycycleview.BaseItemBinder
import com.justcode.hxl.lucky_recycleview.luckycycleview.ViewHolder
import kotlinx.android.synthetic.main.item_stack_card.view.*

import com.justcode.hxl.viewaction.R
class CardItemBinder : BaseItemBinder<CardItem>() {

    var itemclickCard: ((cardView: CardView, item: CardItem) -> Unit) = {cardView, item ->  }
    override var layout: Int
        get() = R.layout.item_stack_card
        set(value) {}

    override fun onBindViewHolder(holder: ViewHolder, item: CardItem) {
        with(holder.itemView) {
            relative_layout.setBackgroundResource(item.background!!)
            iv_ripple.setImageResource(item.logo!!)
            title_text_view.text = item.title

            setOnClickListener {
                itemclickCard.invoke(card_view,item)
            }
        }
    }

}