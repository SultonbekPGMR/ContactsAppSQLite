package com.codialstudent.sqltrash.fragments

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codialstudent.sqltrash.MainActivity
import com.codialstudent.sqltrash.adapters.PagerAgentViewModel
import com.codialstudent.sqltrash.databinding.FragmentEditBinding
import com.codialstudent.sqltrash.db.MyDbHelper
import com.codialstudent.sqltrash.models.MyContact
import com.google.android.material.snackbar.Snackbar


class EditFragment : Fragment() {


    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(requireActivity())[PagerAgentViewModel::class.java]

        myDbHelper = MyDbHelper(binding.root.context)
        binding.btnSave.setOnClickListener {

            val name = binding.edtName.text.toString().trim()
            val number = binding.edtNumber.text.toString().trim()
            if (name.isEmpty()) {
                binding.edtName.error = "invalid name"
            }

            if (number.length < 9) {
                binding.edtNumber.error = "invalid number"
            } else {
                if (name.isNotEmpty() && number.isNotEmpty()) {
                    val user = MyContact(name, number)
                    myDbHelper.addContact(user)
                    val data = MyContact(name, number)
                    viewModel.sendMessageToB(data)

                    val imm =
                        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                    Snackbar.make(
                        binding.coordinateLayout,
                        "Saved successfully",
                        Snackbar.LENGTH_SHORT
                    )
                        .setBackgroundTint(Color.parseColor("#FF6200EE"))
                        .setAction("SHOW") {
                            (activity as MainActivity?)?.goPage1()
                        }.setActionTextColor(Color.parseColor("#FB8C00")).show()

                    binding.edtName.text.clear()
                    binding.edtNumber.text.clear()
                    binding.edtName.clearFocus()
                    binding.edtNumber.clearFocus()

                }
            }

        }

        binding.clearCacheList.setOnLongClickListener {
            myDbHelper.clearDataBase()
            return@setOnLongClickListener true
        }
        binding.coordinateLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                binding.edtName.clearFocus()
                binding.edtNumber.clearFocus()
                return true
            }

        })

        return binding.root

    }


}