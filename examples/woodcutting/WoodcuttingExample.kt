package com.matteblack.fsm.examples.woodcutting

import com.matteblack.fsm.BotState
import com.matteblack.fsm.StateBot
import com.matteblack.fsm.examples.woodcutting.states.Starting
import com.runemate.game.api.hybrid.location.Area.Rectangular
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.ui.DefaultUI
import java.awt.geom.Area

class WoodcuttingExample:StateBot() {
    companion object {
        val treeArea = Rectangular(Coordinate(3104, 3228, 0), Coordinate(3108, 3233, 0))
    }

    override fun onStart(vararg arguments: String?) {
        DefaultUI.setStatus("Wow")

        super.onStart(*arguments)
    }


    override fun setDefaultState(): BotState {
        return Starting()
    }
}