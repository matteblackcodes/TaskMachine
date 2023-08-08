package com.matteblack.fsm

import com.matteblack.fsm.annotations.TransitionField
import kotlin.collections.ArrayList

abstract class State() {
    private var transitions: List<Transition> = ArrayList()

    open fun defineTransitions() {
        println("Define transitions")
        transitions = ArrayList()

        this::class.java.declaredFields
            .filter { it.isAnnotationPresent(TransitionField::class.java) }
            .sortedBy { it.getAnnotation(TransitionField::class.java).order }
            .forEach { field ->
                field.isAccessible = true
                if (Transition::class.java.isAssignableFrom(field.type)) {
                    transitions += field.get(this) as Transition
                } else {
                    throw IllegalArgumentException("Field ${field.name} is annotated with @TransitionItem but is not of type Transition")
                }
            }

    }


    fun getTransitions(): List<Transition> {
        return transitions
    }

    protected fun addTransition(transition: Transition) {
        this.transitions += transition
    }

    open fun onStart() {}
    open fun execute() {}
    open fun onExit() {}
}