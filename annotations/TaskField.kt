package com.matteblack.fsm.annotations

@Target(AnnotationTarget.FIELD)
annotation class TaskField(val order: Int = 0)
