package id.co.kudoo_app_kotlin.view.common

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T =
    ViewModelProviders.of(this).get(modelClass.java)