package com.matteblack.fsm

interface Transition {
    fun validate(): Boolean
    fun transitionTo(): BotState
}