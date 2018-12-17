package com.justcode.hxl.stack_card

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_stack_card.*
import com.justcode.hxl.viewaction.R
class StackCardActivity : AppCompatActivity() {

    val cardItemBinder by lazy {
        CardItemBinder()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_card)

        lucky_recycle.setLayoutManager(StackCardLayoutManager(5))
        lucky_recycle.register(CardItem::class.java, cardItemBinder)


        val itemDecor = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean = true.also { _ ->
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        lucky_recycle.adapter.notifyItemMoved(fromPos, toPos)
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    }
                })
        itemDecor.attachToRecyclerView(lucky_recycle.recyclerView)

        cardItemBinder.itemclickCard = { cardView, item ->
            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, cardView, cardView.transitionName)
            val intent = Intent(this, CardDetialActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("logo", item.logo)
            intent.putExtra("background", item.background)
            startActivity(intent, compat.toBundle())
        }
        val cardItem1 = CardItem(R.drawable.ic_bitcoin,R.drawable.bitcoin_background,"bitcoin")
        val cardItem2 = CardItem(R.drawable.ic_ethereum,R.drawable.ethereum_background,"ethereum")
        val cardItem3 = CardItem(R.drawable.ic_nem,R.drawable.nem_background,"nem")
        val cardItem4 = CardItem(R.drawable.ic_omg,R.drawable.omg_background,"omg")
        val cardItem5 = CardItem(R.drawable.ic_ripple,R.drawable.ripple_background,"ripple")
        val list = arrayListOf<CardItem>()
        list.add(cardItem1)
        list.add(cardItem2)
        list.add(cardItem3)
        list.add(cardItem4)
        list.add(cardItem5)
        lucky_recycle.items = list.toMutableList()
    }
}
