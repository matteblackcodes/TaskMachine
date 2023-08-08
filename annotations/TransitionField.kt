package com.matteblack.fsm.annotations

@Target(AnnotationTarget.FIELD)
annotation class TransitionField(val order: Int = 0)
