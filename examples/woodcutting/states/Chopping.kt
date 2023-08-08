package com.matteblack.fsm.examples.woodcutting.states

import com.matteblack.fsm.GenericTransition
import com.matteblack.fsm.TaskState
import com.matteblack.fsm.annotations.TaskItem
import com.matteblack.fsm.annotations.TransitionItem
import com.matteblack.fsm.examples.woodcutting.states.tasks.ChopTrees
import com.matteblack.fsm.examples.woodcutting.states.tasks.MoveToTrees
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class Chopping: TaskState() {
    /** TRANSITIONS */
    @TransitionItem
    val toBanking = GenericTransition(::needToBank) { Banking() }

    /** TASKS */
    @TaskItem(order = 1)
    val walkToTrees = MoveToTrees()

    @TaskItem(order = 2)
    val chopTrees = ChopTrees()

    override fun onStart() {
        super.onStart()
    }

    private fun needToBank() : Boolean {
        return !Inventory.contains("Bronze axe") || Inventory.getEmptySlots() == 0
    }

}