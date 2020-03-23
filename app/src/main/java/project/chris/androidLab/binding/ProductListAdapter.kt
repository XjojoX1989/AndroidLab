package project.chris.androidLab.binding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import project.chris.androidLab.R
import project.chris.androidLab.databinding.MyItemBinding

class ProductListAdapter(val list: ArrayList<ItemModel?>) : RecyclerView.Adapter<BindingViewHolder<MyItemBinding>>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BindingViewHolder<MyItemBinding> {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.my_item, p0, false)
        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(bindingHolder: BindingViewHolder<MyItemBinding>, position: Int) {
        bindingHolder.binding.item = list[position]

    }

    override fun getItemCount(): Int = list.size

}