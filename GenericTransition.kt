package com.matteblack.fsm

class GenericTransition(val expression: ()-> Boolean, val state: () -> BotState): Transition {
    override fun validate(): Boolean {
        return expression()
    }

    override fun transitionTo(): BotState {
        return state()
    }
}