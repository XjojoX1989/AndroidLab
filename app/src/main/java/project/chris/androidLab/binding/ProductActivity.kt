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
        binding.rv.adapter = ProductListAdapter(list)
    }

    private fun getFakeData(): java.util.ArrayList<ItemModel?> {
        val list = ArrayList<ItemModel?>()

        val itemModel: ItemModel = ItemModel()
        for (i in 0..100) {
            itemModel.itemLabel = i.toString()
            list.add(i, itemModel)
        }
        return list
    }
}