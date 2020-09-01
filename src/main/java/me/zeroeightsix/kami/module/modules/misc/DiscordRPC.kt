package me.zeroeightsix.kami.module.modules.misc

import me.zeroeightsix.kami.DiscordPresence
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.setting.Setting
import me.zeroeightsix.kami.setting.Settings
import me.zeroeightsix.kami.util.InfoCalculator
import me.zeroeightsix.kami.util.MessageSendHelper
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextFormatting


/**
 * @author dominikaaaa
 * Updated by dominikaaaa on 13/01/20
 * Updated (slightly) by Dewy on 3rd April 2020
 */
@Module.Info(
        name = "DiscordRPC",
        category = Module.Category.CLIENT,
        description = "Discord Rich Presence"
)
class DiscordRPC : Module() {
    private val coordsConfirm = register(Settings.b("Coords Confirm", false))
    @JvmField
    var line1Setting: Setting<LineInfo> = register(Settings.e("Line 1 Left", LineInfo.TEXT)) // details left
    @JvmField
    var line3Setting: Setting<LineInfo> = register(Settings.e("Line 1 Right", LineInfo.USERNAME)) // details right
    @JvmField
    var line2Setting: Setting<LineInfo> = register(Settings.e("Line 2 Left", LineInfo.SERVER_IP)) // state left
    @JvmField
    var line4Setting: Setting<LineInfo> = register(Settings.e("Line 2 Right", LineInfo.TOTEMS)) // state right

    enum class LineInfo {
        TEXT, DIMENSION, USERNAME, TOTEMS, SERVER_IP, COORDS, NONE
    }

    fun getLine(line: LineInfo?): String {
        return when (line) {
            LineInfo.TEXT -> "Upsilon"
            LineInfo.DIMENSION -> InfoCalculator.playerDimension(mc)
            LineInfo.USERNAME -> if (mc.player != null) mc.player.name else mc.getSession().username
            LineInfo.TOTEMS -> "Baithck v1.0"
            LineInfo.SERVER_IP -> if (mc.getCurrentServerData() != null) mc.getCurrentServerData()!!.serverIP else if (mc.isIntegratedServerRunning) "Offline" else "Menu"
            LineInfo.COORDS -> "69 420 69"
            else -> ""
        }
    }

    public override fun onEnable() {
        DiscordPresence.start()
    }

    override fun onUpdate() {
        if (startTime == 0L) startTime = System.currentTimeMillis()
        if (startTime + 10000 <= System.currentTimeMillis()) { // 10 seconds in milliseconds
            if (line1Setting.value == LineInfo.COORDS || line2Setting.value == LineInfo.COORDS || line3Setting.value == LineInfo.COORDS || line4Setting.value == LineInfo.COORDS) {
                if (!coordsConfirm.value && mc.player != null) {
                    MessageSendHelper.sendWarningMessage("$chatName Warning: In order to use the coords option please enable the coords confirmation option. This will display your coords on the discord rpc. Do NOT use this if you do not want your coords displayed")
                }
            }
            startTime = System.currentTimeMillis()
        }
    }

    override fun onDisable() {
        DiscordPresence.end()
    }

    companion object {
        private var startTime: Long = 0
    }
}