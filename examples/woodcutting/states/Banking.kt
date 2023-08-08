package com.matteblack.fsm.examples.woodcutting.states

import com.matteblack.fsm.BotState
import com.matteblack.fsm.GenericTransition
import com.matteblack.fsm.TaskState
import com.matteblack.fsm.annotations.TaskItem
import com.matteblack.fsm.annotations.TransitionItem
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.matteblack.fsm.examples.woodcutting.states.tasks.BankItems
import com.matteblack.fsm.examples.woodcutting.states.tasks.MoveToBank
import com.matteblack.fsm.examples.woodcutting.states.tasks.OpenBank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance

class Banking: TaskState() {

    /** TRANSITIONS */
    @TransitionItem
    val toChopping = GenericTransition({ Inventory.containsOnly("Bronze axe")}, { Chopping() })


    /** TASKS */
    @TaskItem(order = 1)
    val moveToBank = MoveToBank()

    @TaskItem(order = 2)
    val openBank = OpenBank()

    @TaskItem(order = 3)
    val bankItems = BankItems()


    override fun onStart() {
        super.onStart()
    }


}