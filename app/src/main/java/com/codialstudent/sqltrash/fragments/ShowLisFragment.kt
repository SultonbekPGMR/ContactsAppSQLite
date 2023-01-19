package com.codialstudent.sqltrash.fragments

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codialstudent.sqltrash.R
import com.codialstudent.sqltrash.adapters.PagerAgentViewModel
import com.codialstudent.sqltrash.adapters.RvAdapter
import com.codialstudent.sqltrash.adapters.RvClick
import com.codialstudent.sqltrash.databinding.AlertDialogLayoutBinding
import com.codialstudent.sqltrash.databinding.FragmentShowLisBinding
import com.codialstudent.sqltrash.db.MyDbHelper
import com.codialstudent.sqltrash.models.MyContact


class ShowLisFragment : Fragment(), RvClick {

    private val binding by lazy { FragmentShowLisBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapter: RvAdapter
    private lateinit var viewModel: PagerAgentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[PagerAgentViewModel::class.java]

        myDbHelper = MyDbHelper(requireContext())
        rvAdapter = RvAdapter(requireContext(), myDbHelper.getAllContacts() as ArrayList, this)

        binding.rvMovie.adapter = rvAdapter


        viewModel.messageContainerB.observe(
            viewLifecycleOwner
        ) { data ->
            rvAdapter.list.add(data)
            rvAdapter.notifyItemInserted(rvAdapter.list.size - 1)
            rvAdapter.notifyItemRangeChanged(0, rvAdapter.list.size)
            binding.rvMovie.scrollToPosition(rvAdapter.list.size - 1)
        }

        return binding.root
    }


    override fun sendSMS(myContact: MyContact, position: Int) {
        val smsUri = Uri.parse("smsto:+998${myContact.number}")
        val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
        smsIntent.putExtra("sms_body", "Assalomu alaykum")
        startActivity(smsIntent)
    }


    override fun phoneCall(myContact: MyContact) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:+998${myContact.number}")
        startActivity(callIntent)
    }

    override fun showRvMenu(myContact: MyContact, position: Int, imageView: ImageView) {
        val context = ContextThemeWrapper(requireContext(), R.style.PopupMenuStyle_PopupMenu)
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.menu_popup_item)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    myDbHelper.deleteContact(myContact)
                    rvAdapter.list.removeAt(position)
                    rvAdapter.notifyItemRemoved(position)
                }
                R.id.menu_edit -> {
                    editContact(position, myContact)
                }
            }

            true
        }

    }

    private fun editContact(position: Int, myContact: MyContact) {
        val alertDialogLayoutBinding = AlertDialogLayoutBinding.inflate(layoutInflater)
        alertDialogLayoutBinding.edtName.setText(myContact.name)
        alertDialogLayoutBinding.edtNumber.setText(myContact.number)
        val dialog: AlertDialog = AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
            .setView(alertDialogLayoutBinding.root)
            .setPositiveButton("OK",null)
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .create()
        dialog.setOnShowListener(object : DialogInterface.OnShowListener {
            override fun onShow(p0: DialogInterface?) {
                val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

                button.setOnClickListener{
                    val name = alertDialogLayoutBinding.edtName.text.toString().trim()
                    val number = alertDialogLayoutBinding.edtNumber.text.toString().trim()
                    if (name.isEmpty()) {
                        alertDialogLayoutBinding.edtName.error = "invalid name"
                    }

                    if (number.length < 9) {
                        alertDialogLayoutBinding.edtNumber.error = "invalid number"
                    } else {
                        if (name.isNotEmpty() && number.isNotEmpty()) {
                            dialog.cancel()
                            val user = MyContact(name, number)

                            if (user!=myContact){
                                myDbHelper.editContact(
                                    user
                                )
                                rvAdapter.list[position] = user
                                rvAdapter.notifyItemChanged(position)
                            }

                        }
                    }


                }

            }
        })
        dialog.show()
    }

}