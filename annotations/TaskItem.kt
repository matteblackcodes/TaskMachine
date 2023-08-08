package com.matteblack.fsm.annotations

@Target(AnnotationTarget.FIELD)
annotation class TaskItem(val order: Int = 0)
