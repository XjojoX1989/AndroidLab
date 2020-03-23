package project.chris.androidLab.binding;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BindingViewHolder<T> extends RecyclerView.ViewHolder {
    public T binding;

    public BindingViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = (T) DataBindingUtil.bind(itemView);
    }

    public T getBinding() {
        return binding;
    }
}
