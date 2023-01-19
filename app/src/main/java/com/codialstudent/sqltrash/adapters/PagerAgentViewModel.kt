package com.codialstudent.sqltrash.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.codialstudent.sqltrash.models.MyContact


class PagerAgentViewModel(state: SavedStateHandle) : ViewModel() {
    val messageContainerA: MutableLiveData<MyContact>
    val messageContainerB: MutableLiveData<MyContact>

    init {
        messageContainerA = state.getLiveData("Default Message")
        messageContainerB = state.getLiveData("Default Message")
    }

    fun sendMessageToB(msg: MyContact) {
        messageContainerB.value = msg
    }

    fun sendMessageToA(msg: MyContact) {
        messageContainerA.value = msg
    }

    fun getMessageContainerA(): LiveData<MyContact> {
        return messageContainerA
    }

    fun getMessageContainerB(): LiveData<MyContact> {
        return messageContainerB
    }
}