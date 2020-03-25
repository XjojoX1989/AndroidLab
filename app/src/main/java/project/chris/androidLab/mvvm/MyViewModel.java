package project.chris.androidLab.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.chris.androidLab.User;

public class MyViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;

    public LiveData<List<User>> getUsers(){
        if (users==null)
            users = new MutableLiveData<List<User>>();
        return users;
    }

    public void loadUsers(){

    }
}
