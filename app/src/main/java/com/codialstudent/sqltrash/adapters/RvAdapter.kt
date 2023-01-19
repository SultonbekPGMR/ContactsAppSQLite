package com.codialstudent.sqltrash.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codialstudent.sqltrash.R
import com.codialstudent.sqltrash.databinding.RvItemBinding
import com.codialstudent.sqltrash.models.MyContact


class RvAdapter(val context: Context, val list: ArrayList<MyContact>, val rvClick: RvClick) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    private var lastPosition = -1

    inner class Vh(val itemRvBinding: RvItemBinding) : ViewHolder(itemRvBinding.root) {

        fun onBind(myContact: MyContact, position: Int) {
            itemRvBinding.tvName.text = myContact.name
            itemRvBinding.tvNumber.text = myContact.number
            itemRvBinding.tvCount.text = (position + 1).toString()


            val rvAnim = if (position > lastPosition) {
                AnimationUtils.loadAnimation(context, R.anim.up_from_bottom)
            } else {
                AnimationUtils.loadAnimation(context, R.anim.down_from_top)
            }
            lastPosition = position

            itemRvBinding.root.startAnimation(rvAnim)


            itemRvBinding.phoneCall.setOnClickListener {
                rvClick.phoneCall(myContact)
            }
            itemRvBinding.sms.setOnClickListener {
                rvClick.sendSMS(myContact, position)
            }
            itemRvBinding.rvMenu.setOnClickListener {
                rvClick.showRvMenu(myContact, position, itemRvBinding.rvMenu)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size

    override fun onViewDetachedFromWindow(holder: Vh) {
        super.onViewDetachedFromWindow(holder)
        holder.itemRvBinding.root.clearAnimation()
    }
}

interface RvClick {
    fun sendSMS(myContact: MyContact, position: Int)
    fun phoneCall(myContact: MyContact)
    fun showRvMenu(myContact: MyContact, position: Int, imageView: ImageView)
}