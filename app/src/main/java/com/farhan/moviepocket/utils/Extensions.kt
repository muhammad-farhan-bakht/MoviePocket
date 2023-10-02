package com.farhan.moviepocket.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.farhan.moviepocket.R
import dagger.hilt.android.internal.Contexts.getApplication

/**
 * Extension [Context]: Get generic error message if given error message is null
 * @param message error message
 */
fun Context.getErrorMessage(message: String?): String {
    var errorMessage = getString(R.string.generic_error_message)
    if (!message.isNullOrEmpty()) {
        errorMessage = message
    }
    return errorMessage
}

/**
 * Extension [Context]: Show Short Length Toast
 * @param message String of Toast
 */
fun Context.toast(message: CharSequence? = getString(R.string.generic_error_message)) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/**
 * Extension [View]: Visible View
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Extension [View]: Invisible View
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Extension [View]: Gone View
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Extension [View]: Close Keyboard
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension [Context]: returns internet connection state
 */
fun Context.hasInternetConnection(): Boolean {
    val connectivityManager = getApplication(this).getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(
        activeNetwork
    ) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}