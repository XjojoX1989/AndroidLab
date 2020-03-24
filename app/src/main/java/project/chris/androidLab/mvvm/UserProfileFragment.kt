package project.chris.androidLab.mvvm

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.chris.androidLab.R


/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {
    private val viewModel:UserProfileViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_profile_layout, container, false)
    }

}
