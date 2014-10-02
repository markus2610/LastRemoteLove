package com.meoyawn.remotelove.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import rx.subjects.PublishSubject

/**
 * Created by adelnizamutdinov on 10/3/14
 */
open class RxFrameLayout(ctx: Context, attrs: AttributeSet) : FrameLayout(ctx, attrs) {
    val detaches = PublishSubject.create<FrameLayout>()
}