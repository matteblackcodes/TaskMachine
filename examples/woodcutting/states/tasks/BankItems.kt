package com.matteblack.fsm.examples.woodcutting.states.tasks

import com.matteblack.fsm.Task
import com.matteblack.fsm.examples.woodcutting.WoodcuttingExample
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.ui.DefaultUI

class BankItems: Task {
    override fun validate(): Boolean {
        return Bank.isOpen()
    }

    override fun execute() {
        DefaultUI.setStatus("Depositing Items")
        //Deposit everything except Bronze Axe
        Bank.depositAllExcept("Bronze axe")

        //Withdraw bronze axe if not already in inventory
        if(!Inventory.contains("Bronze axe")) {
            if(!Bank.withdraw("Bronze axe", 1)) {
                DefaultUI.setStatus("You don't have a bronze axe, stopping bot.")
                Environment.getBot()?.stop("No axe")
                return
            }

            Bank.close()
        }
    }

}