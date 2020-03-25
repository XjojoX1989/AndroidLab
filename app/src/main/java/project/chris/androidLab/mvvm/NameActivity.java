package project.chris.androidLab.mvvm;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import project.chris.androidLab.R;
import project.chris.androidLab.User;

public class NameActivity extends AppCompatActivity {
    private NameViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                Log.e("GGG", "create: ");
                return null;
            }
        }).get(NameViewModel.class);
//        model = (new ViewModelProvider(this)).get(NameViewModel.class);

        model.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ((TextView )findViewById(R.id.tv)).setText(s);
            }
        });

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        model.getCurrentName().postValue("Chris");
                    }
                });
                executorService.shutdown();

            }
        });
//
//        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
//        myViewModel.getUsers().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//
//            }
//        });
//        ViewModelProviders.of(this, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return null;
//            }
//        }).get(MyViewModel.class);
    }

    class StockLiveData extends LiveData<BigDecimal>{
        @Override
        protected void onActive() {
            super.onActive();
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }
    }
}
