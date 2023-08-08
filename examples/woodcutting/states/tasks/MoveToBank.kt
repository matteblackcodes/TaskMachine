package com.matteblack.fsm.examples.woodcutting.states.tasks

import com.matteblack.fsm.Task
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.web.WebPath
import com.runemate.ui.DefaultUI

class MoveToBank: Task {
    override fun validate(): Boolean {

        return Banks.getLoadedBankBooths().nearest()?.isVisible != true
    }

    private var path: WebPath? = null

    override fun execute() {
        if(path == null) path = WebPath.buildTo(Landmark.BANK)

        DefaultUI.setStatus("Walking to bank")
        path?.step()
    }
}