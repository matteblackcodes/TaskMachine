package com.matteblack.fsm

import java.lang.reflect.Method


interface Task {
    fun validate(): Boolean
    fun execute()

}

