package com.rxyzent.weatherapp.ui

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rxyzent.weatherapp.core.Db.Cache
import com.rxyzent.weatherapp.core.adapter.DialogAdapter
import com.rxyzent.weatherapp.databinding.CityChangeLayoutBinding

class CityChangeDialog(context: Context) : BottomSheetDialog(context) {
    val adapter = DialogAdapter()
    val binding = CityChangeLayoutBinding.inflate(layoutInflater)


    init {
        loadData()
        setContentView(binding.root)
    }

    private fun loadData() {
        binding.dialogList.adapter = adapter
        adapter.data = Cache.cache.getData()
    }

}