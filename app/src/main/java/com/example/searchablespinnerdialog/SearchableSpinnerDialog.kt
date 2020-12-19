package com.example.searchablespinnerdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import com.example.searchablespinnerdialog.databinding.DialogSearchableSpinnerBinding

class SearchableSpinnerDialog(
        private val mContext: Context,
        private val arrayList: ArrayList<String>,
        private val listener: SearchableSpinnerListListener
) : Dialog(mContext) {

    private lateinit var binding: DialogSearchableSpinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogSearchableSpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val adapter = ArrayAdapter(
                mContext,
                android.R.layout.simple_list_item_1,
                arrayList)

        binding.listData.adapter = adapter

        binding.edtSearch.doOnTextChanged { text, start, before, count ->
            adapter.filter.filter(text)
        }


        binding.listData.setOnItemClickListener { adapterView, view, i, l ->
            adapter.getItem(i)?.let { listener.onItemClickListener(it) }
            dismiss()
        }
    }

    override fun show() {
        super.show()

    }

    interface SearchableSpinnerListListener{
        fun onItemClickListener(name: String)
    }
}