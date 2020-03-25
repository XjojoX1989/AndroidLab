package project.chris.androidLab;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.lifecycle.MutableLiveData;

public class User {

    private MutableLiveData<String> name = new MutableLiveData();
    private MutableLiveData<Integer> age = new MutableLiveData();

    void setName() {
        name.postValue("GG");
    }

}
