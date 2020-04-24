package com.example.mvvmApp.ui.mainpage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mvvmApp.R

class HomeDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val viewModel = (it as HomeActivity).obtainViewModel()
            val itemIndex = arguments!!.getInt(ARG_ITEM_INDEX)
            return AlertDialog.Builder(it, R.style.AppTheme_AlertDialog)
                .setMessage("Message dialog")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    viewModel.onSelectedIndex(itemIndex)
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.onSelectedIndex(itemIndex)
                }
                .create()
        } ?: throw IllegalAccessError("Activity cannot be null")
    }

    override fun onCancel(dialog: DialogInterface) {
        activity?.also {
            val viewModel = (it as HomeActivity).obtainViewModel()
            val itemIndex = arguments!!.getInt(ARG_ITEM_INDEX)
            viewModel.onCancleSelectedIndex(itemIndex)
        }
    }

    companion object {

        const val ARG_ITEM_INDEX = "item_index"

        fun newInstance(itemIndex: Int): HomeDialog {
            val instance = HomeDialog()
            val args = Bundle()
            args.putInt(ARG_ITEM_INDEX, itemIndex)
            instance.arguments = args
            return instance
        }
    }
}