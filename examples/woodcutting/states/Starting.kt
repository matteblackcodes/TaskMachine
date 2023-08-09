package com.matteblack.fsm.examples.woodcutting.states

import com.matteblack.fsm.GenericTransition
import com.matteblack.fsm.TaskMachine
import com.matteblack.fsm.TaskState
import com.matteblack.fsm.annotations.TransitionField
import com.matteblack.fsm.di.injected
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance

class Starting : TaskState() {

    var player = Players.getLocal()

    val bot: WoodcuttingExample by injected()


    //Check if we are at the tree area.
    @TransitionField(order = 1)
    val toChopping = GenericTransition({
        Distance.between(player, WoodcuttingExample.treeArea) <= 5
                || Inventory.containsOnly("Bronze axe")
    },
        { Chopping() })

    //Otherwise bank
    @TransitionField(order = 2)
    val toBanking = GenericTransition({ true }, { Banking() })


    override fun onStart() {
        println("SECRET MESSAGE" + bot.secretMessage)
    }

}