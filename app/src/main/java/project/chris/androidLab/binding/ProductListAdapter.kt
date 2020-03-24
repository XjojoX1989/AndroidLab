package project.chris.androidLab.binding

import android.content.ClipData.Item
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import project.chris.androidLab.BR
import project.chris.androidLab.R
import project.chris.androidLab.databinding.MyItemBinding


abstract class ProductListAdapter() : RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.my_item, p0, false)
        return MyViewHolder(DataBindingUtil.bind(view)!!)
    }

    override fun onBindViewHolder(bindingHolder: MyViewHolder, position: Int) {
        val myItemBinding = bindingHolder.myItemBinding
        bindingHolder.bind(myItemBinding, position)

    }

    abstract override fun getItemCount(): Int

    abstract fun getData(): java.util.ArrayList<ItemModel?>

    inner class MyViewHolder(binding: MyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val myItemBinding: MyItemBinding = binding

        fun bind(binding: MyItemBinding, position: Int){
//            binding.item1 = getData()[position]
            binding.setVariable(BR.item1, getData()[position])
            binding.executePendingBindings()
        }

    }

}