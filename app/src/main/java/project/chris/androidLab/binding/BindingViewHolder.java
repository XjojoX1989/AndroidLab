package project.chris.androidLab.binding;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
