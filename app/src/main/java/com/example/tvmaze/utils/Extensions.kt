package com.example.tvmaze.utils

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun String?.validValue(): String {
    if(this.isNullOrBlank())
        return "unknown"
    return this
}

fun Int?.validValue(): Int {
    if(this == null)
        return -1
    return this
}

fun List<String>?.joinToStringValidValue(): String {
    if(this != null)
        return this.joinToString(",")
    return "unknown"
}

fun String.separateToList(): List<String>? {
    if (this == "unknown")
        return null
    return this.split(",")
}

var View.visible : Boolean
    get() = visibility == View.VISIBLE
    set(value){
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null

        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    afterTextChanged.invoke(editable.toString())
                }
            }.start()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
