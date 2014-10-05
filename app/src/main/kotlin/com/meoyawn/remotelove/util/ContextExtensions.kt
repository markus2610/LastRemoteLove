package com.meoyawn.remotelove.util

import android.content.Context
import android.widget.Toast
import android.app.Fragment

/**
 * Created by adelnizamutdinov on 10/4/14
 */
public fun Context.shortToast(stringRes: Int): Toast =
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT)

public fun Fragment.shortToast(stringRes: Int): Toast? =
    this.getActivity()?.shortToast(stringRes)