package project.chris.androidLab.binding

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import project.chris.androidLab.R
import project.chris.androidLab.databinding.ActivityProcudtListBinding

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list: ArrayList<ItemModel?> = getFakeData()
        val binding: ActivityProcudtListBinding = DataBindingUtil.setContentView(this, R.layout.activity_procudt_list)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = object : ProductListAdapter() {
            override fun getItemCount(): Int = list.size

            override fun getData(): ArrayList<ItemModel?> {
                return list
            }

        }
    }

    private fun getFakeData(): java.util.ArrayList<ItemModel?> {
        val list = ArrayList<ItemModel?>()

        for (i in 0..100) {
            val itemModel = ItemModel()
            itemModel.itemLabel = i.toString()
            list.add(i, itemModel)
        }
        return list
    }
}