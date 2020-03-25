package project.chris.androidLab.mvvm;

import androidx.lifecycle.ViewModel;

import androidx.lifecycle.MutableLiveData;

public class NameViewModel extends ViewModel {
    private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null)
            currentName = new MutableLiveData<String>();
        return currentName;
    }
}
