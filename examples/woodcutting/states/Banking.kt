package com.matteblack.fsm.examples.woodcutting.states

import com.matteblack.fsm.GenericTransition
import com.matteblack.fsm.TaskState
import com.matteblack.fsm.annotations.TaskField
import com.matteblack.fsm.annotations.TransitionField
import com.matteblack.fsm.di.injected
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.matteblack.fsm.examples.woodcutting.states.tasks.BankItems
import com.matteblack.fsm.examples.woodcutting.states.tasks.MoveToBank
import com.matteblack.fsm.examples.woodcutting.states.tasks.OpenBank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class Banking: TaskState() {

    /** TRANSITIONS */
    @TransitionField
    val toChopping = GenericTransition({ Inventory.containsOnly("Bronze axe")}, { Chopping() })

    /** TASKS */
    @TaskField(order = 1)
    val moveToBank = MoveToBank()

    @TaskField(order = 2)
    val openBank = OpenBank()

    @TaskField(order = 3)
    val bankItems = BankItems()


    override fun onStart() {
        super.onStart()
    }


}