package com.implizstudio.android.app.pitzz.ui.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.implizstudio.android.app.pitzz.R
import kotlinx.android.synthetic.main.sheet_language.*

class LanguageSheet(private val choose: Choose) : RoundedBottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.sheet_language, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cv_english_lang.setOnClickListener {

            choose.isEnglish()
            dismiss()

            activity?.recreate()

        }

        cv_indonesia_lang.setOnClickListener {

            choose.isIndonesia()
            dismiss()

            activity?.recreate()

        }

    }

    interface Choose {
        fun isEnglish()
        fun isIndonesia()
    }

}