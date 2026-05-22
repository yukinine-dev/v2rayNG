package com.v2ray.ang.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.v2ray.ang.R
import com.v2ray.ang.databinding.BottomSheetImportBinding
import com.v2ray.ang.enums.EConfigType

class ImportBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetImportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetImportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val host = activity as? MainActivity ?: run { dismiss(); return }

        bindRow(
            binding.rowQr.root,
            R.drawable.ic_qu_scan_24dp,
            R.string.import_sheet_qr_title,
            R.string.import_sheet_qr_subtitle,
        ) {
            dismiss()
            host.openImportQrCode()
        }
        bindRow(
            binding.rowClipboard.root,
            R.drawable.ic_copy,
            R.string.import_sheet_clipboard_title,
            R.string.import_sheet_clipboard_subtitle,
        ) {
            dismiss()
            host.openImportClipboard()
        }
        bindRow(
            binding.rowFile.root,
            R.drawable.ic_file_24dp,
            R.string.import_sheet_file_title,
            R.string.import_sheet_file_subtitle,
        ) {
            dismiss()
            host.openImportLocal()
        }
        bindRow(
            binding.rowSubscription.root,
            R.drawable.ic_subscriptions_24dp,
            R.string.import_sheet_subscription_title,
            R.string.import_sheet_subscription_subtitle,
        ) {
            dismiss()
            host.importConfigViaSub()
        }

        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_vless) {
            host.openImportManually(EConfigType.VLESS.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_vmess) {
            host.openImportManually(EConfigType.VMESS.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_trojan) {
            host.openImportManually(EConfigType.TROJAN.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_ss) {
            host.openImportManually(EConfigType.SHADOWSOCKS.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_socks) {
            host.openImportManually(EConfigType.SOCKS.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_http) {
            host.openImportManually(EConfigType.HTTP.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_wireguard) {
            host.openImportManually(EConfigType.WIREGUARD.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_manually_hysteria2) {
            host.openImportManually(EConfigType.HYSTERIA2.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_policy_group) {
            host.openImportManually(EConfigType.POLICYGROUP.value)
        }
        addManualChip(binding.manualChipGroup, R.string.menu_item_import_config_proxy_chain) {
            host.openImportManually(EConfigType.PROXYCHAIN.value)
        }
    }

    private fun bindRow(
        root: View,
        iconRes: Int,
        titleRes: Int,
        subtitleRes: Int,
        onClick: () -> Unit,
    ) {
        val icon = root.findViewById<android.widget.ImageView>(R.id.row_icon)
        val title = root.findViewById<android.widget.TextView>(R.id.row_title)
        val subtitle = root.findViewById<android.widget.TextView>(R.id.row_subtitle)
        icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), iconRes))
        title.setText(titleRes)
        subtitle.setText(subtitleRes)
        root.setOnClickListener { onClick() }
    }

    private fun addManualChip(group: FlexboxLayout, labelRes: Int, onClick: () -> Unit) {
        val chip = Chip(requireContext()).apply {
            setText(labelRes)
            isCheckable = false
            isClickable = true
            setOnClickListener {
                dismiss()
                onClick()
            }
        }
        val params = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
        ).apply {
            marginEnd = resources.getDimensionPixelSize(R.dimen.padding_spacing_dp8)
            bottomMargin = resources.getDimensionPixelSize(R.dimen.padding_spacing_dp8)
        }
        group.addView(chip, params)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ImportBottomSheet"
    }
}
