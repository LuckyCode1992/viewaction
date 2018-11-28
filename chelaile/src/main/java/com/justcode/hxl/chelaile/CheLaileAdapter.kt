package com.justcode.hxl.chelaile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_chelaile.view.*

class CheLaileAdapter(var context: Context, var list: MutableList<Data>) : RecyclerView.Adapter<MyViewHolder>() {
    var itemClick: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chelaile, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder.itemView) {

            list[position]?.let {
                tv_name.text = it.name
                tv_number.text = (position + 1).toString()
                if(it.isLocation!!){
                   iv_location.visibility = View.VISIBLE
                }else{
                    iv_location.visibility = View.GONE
                }
                if(it.isBigCar!!){
                    iv_big_car.visibility = View.VISIBLE
                }else{
                    iv_big_car.visibility = View.GONE
                }
                if(it.isSmallCar!!){
                    iv_small_car.visibility = View.VISIBLE
                }else{
                    iv_small_car.visibility = View.GONE
                }

                if(it.isStart!!|| it.isEnd!!){
                    iv_icon.visibility = View.VISIBLE
                    tv_number.visibility = View.GONE
                }else{
                    iv_icon.visibility = View.GONE
                    tv_number.visibility = View.VISIBLE
                }
                if(it.isStart!!){
                    iv_icon.setImageResource(R.drawable.kaishi)

                    iv_left.visibility = View.GONE
                    iv_right.visibility = View.GONE
                }else{

                    iv_left.visibility = View.VISIBLE
                    iv_right.visibility=View.GONE
                }
                if(it.isEnd!!){
                    iv_icon.setImageResource(R.drawable.zhongdian)
                }
                tv_name.setOnClickListener {
                    itemClick?.invoke(position)
                }

            }
        }
    }
    fun update(list0: MutableList<Data>){
        list = list0
        notifyDataSetChanged()
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)