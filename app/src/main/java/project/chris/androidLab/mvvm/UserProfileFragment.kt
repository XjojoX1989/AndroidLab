package project.chris.androidLab.mvvm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import org.koin.android.viewmodel.ext.android.viewModel
import project.chris.androidLab.R


/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.user_profile_layout, container, false)
    }

}
