package com.matteblack.fsm.examples.woodcutting.states.tasks

import com.matteblack.fsm.Task
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import com.runemate.ui.DefaultUI

class ChopTrees: Task {

    private val player: Player? = Players.getLocal()
    private var nearestTree: GameObject? = null

    private var interacted: Boolean = false

    override fun validate(): Boolean {
        //Idle Check
        Execution.delayUntil( { player?.animationId != -1 }, 650 )
        if(player?.animationId != -1) return false

        nearestTree = GameObjects.newQuery().within(WoodcuttingExample.treeArea).names("Tree").actions("Chop down").results().nearest()

        return nearestTree != null
    }

    override fun execute() {
        DefaultUI.setStatus("Chopping")
        nearestTree?.interact("Chop down")
        //Wait until player has started the action.
        Execution.delayUntil({ player?.animationId != -1 }, 10000)
    }
}