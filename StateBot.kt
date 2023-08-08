package com.matteblack.fsm

import com.runemate.game.api.script.framework.LoopingBot
import java.util.*

abstract class StateBot : LoopingBot() {

    private var currentState: BotState? = null


    fun getCurrentState(): BotState? {
        return currentState
    }

    fun setCurrentState(value: BotState?) {
        currentState?.onExit()
        eventDispatcher.getListeners().stream()
            .filter { listener: EventListener? -> listener is BotState }
            .forEach { listener: EventListener? ->
                if (listener != null) {
                    eventDispatcher.removeListener(listener)
                }
            }
        currentState = value

        if (value is EventListener) eventDispatcher.addListener(value)

        currentState?.defineTransitions()
        (currentState as? TaskState)?.defineTasks()
        currentState?.onStart()
    }

    open override fun onLoop() {
        if (currentState == null) {
            setCurrentState(setDefaultState())
        }

        val transitions: List<Transition> = currentState!!.getTransitions()


        for (transition in transitions) {
            if (transition.validate()) {
                setCurrentState(transition.transitionTo())
                return
            }
        }

        currentState!!.execute()
    }

    open override fun onStart(vararg arguments: String?) {
        super.onStart(*arguments)
    }


    abstract fun setDefaultState(): BotState
}