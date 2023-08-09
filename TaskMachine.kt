package com.matteblack.fsm

import com.matteblack.fsm.di.DIContainer
import com.runemate.game.api.script.framework.LoopingBot
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class TaskMachine : LoopingBot() {

    val secretMessage: String = "TASKMACHINE"

    private var currentState: com.matteblack.fsm.State? = null
    fun getCurrentState(): com.matteblack.fsm.State? {
        return currentState
    }

    fun setCurrentState(value: com.matteblack.fsm.State?) {
        currentState?.onExit()
        eventDispatcher.getListeners().stream()
            .filter { listener: EventListener? -> listener is com.matteblack.fsm.State }
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
        println {"Register this"}
        DIContainer.register(this)
        super.onStart(*arguments)
    }


    abstract fun setDefaultState(): com.matteblack.fsm.State
}
