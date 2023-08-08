package com.matteblack.fsm

class GenericTransition(val expression: ()-> Boolean, val state: () -> State): Transition {
    override fun validate(): Boolean {
        return expression()
    }

    override fun transitionTo(): State {
        return state()
    }
}