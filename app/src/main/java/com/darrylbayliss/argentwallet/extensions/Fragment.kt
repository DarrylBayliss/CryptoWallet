package com.darrylbayliss.argentwallet.extensions

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}