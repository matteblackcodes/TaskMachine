package com.matteblack.fsm.examples.woodcutting.states.tasks

import com.matteblack.fsm.Task
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.hybrid.web.WebPath
import com.runemate.ui.DefaultUI

class MoveToTrees: Task {

    private val player = Players.getLocal()
    private var path: WebPath? = null


    override fun validate(): Boolean {
        return Distance.between(player, WoodcuttingExample.treeArea) > 5
    }

    override fun execute() {
        if(path == null) path =  WebPath.buildTo(WoodcuttingExample.treeArea)
        DefaultUI.setStatus("Walking to tree area")
        path?.step()
    }
}