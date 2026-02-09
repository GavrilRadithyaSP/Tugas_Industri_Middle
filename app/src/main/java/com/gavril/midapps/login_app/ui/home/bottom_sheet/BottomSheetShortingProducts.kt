package com.gavril.midapps.login_app.ui.home.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gavril.midapps.databinding.BottomSheetShortingProductsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetShortingProducts(
    private val onSave: (sortBy: String, order: String) -> Unit
): BottomSheetDialogFragment() {
    private var _binding: BottomSheetShortingProductsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetShortingProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val selectedSort = when (binding.sort.checkedRadioButtonId){
                binding.sortTitle.id -> "title"
                binding.sortDesc.id -> "description"
                else -> ""
            }
            val selectedOrder = when (binding.order.checkedRadioButtonId){
                binding.orderAsc.id -> "asc"
                binding.orderDesc.id -> "desc"
                else -> ""
            }
            onSave(selectedSort, selectedOrder)
            dismiss()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}