package com.matteblack.fsm.annotations

@Target(AnnotationTarget.FIELD)
annotation class TransitionItem(val order: Int = 0)
