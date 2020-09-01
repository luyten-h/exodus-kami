package me.zeroeightsix.kami.module.modules.player

import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.event.events.AllowInteractEvent
import net.minecraftforge.fml.common.Mod

@Module.Info(
        name = "MultiTask",
        category = Module.Category.PLAYER,
        description = "Allows you to place crystals and do something else at the same time"
)
class MultiTask : Module() {
    private fun allowInteract(event: AllowInteractEvent) {
        event.isUsingItem = false
    }
}