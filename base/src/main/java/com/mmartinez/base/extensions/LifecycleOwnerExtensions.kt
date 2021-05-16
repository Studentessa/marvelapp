package com.mmartinez.feature_character_list.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}

fun <T> LifecycleOwner.observe(stateFlow: StateFlow<T>, observer: Observer<T>) {
    lifecycleScope.launchWhenResumed {
        stateFlow.collect{
            observer.onChanged(it)
        }
    }
}
