package com.matteblack.fsm.examples.woodcutting.states.tasks

import com.matteblack.fsm.Task
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.script.Execution
import com.runemate.ui.DefaultUI

class OpenBank: Task {
    override fun validate(): Boolean {
        return !Bank.isOpen()
    }

    override fun execute() {
        DefaultUI.setStatus("Opening Bank")
        Bank.open()
        //Wait until bank is open or timeout.
        Execution.delayUntil({Bank.isOpen() }, 15000)
    }
}