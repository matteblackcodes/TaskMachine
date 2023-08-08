package com.matteblack.fsm

interface Condition: () -> Boolean {
    override operator fun invoke(): Boolean
}