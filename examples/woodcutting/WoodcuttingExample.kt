package com.matteblack.fsm.examples.woodcutting

import com.matteblack.fsm.TaskMachine
import com.matteblack.fsm.di.DIContainer
import com.matteblack.fsm.examples.woodcutting.states.Starting
import com.runemate.game.api.hybrid.location.Area.Rectangular
import com.runemate.game.api.hybrid.location.Coordinate

class WoodcuttingExample:TaskMachine() {
    companion object {
        val treeArea = Rectangular(Coordinate(3104, 3228, 0), Coordinate(3108, 3233, 0))
    }

    override fun setDefaultState(): com.matteblack.fsm.State {
        return Starting()
    }
}