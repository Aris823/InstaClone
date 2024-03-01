package com.example.instaclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.AccountSettingActivity
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var view: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = FragmentProfileBinding.inflate(inflater, container, false)
        view.editAccBtn.setOnClickListener{
            startActivity(Intent(context, AccountSettingActivity::class.java))
        }

        return view.root
    }
}
