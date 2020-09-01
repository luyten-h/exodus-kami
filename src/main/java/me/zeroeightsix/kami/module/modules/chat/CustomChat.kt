package me.zeroeightsix.kami.module.modules.chat

import me.zero.alpine.listener.EventHandler
import me.zero.alpine.listener.EventHook
import me.zero.alpine.listener.Listener
import me.zeroeightsix.kami.KamiMod
import me.zeroeightsix.kami.command.Command
import me.zeroeightsix.kami.event.events.PacketEvent
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.setting.Setting
import me.zeroeightsix.kami.setting.Settings
import me.zeroeightsix.kami.util.MessageSendHelper
import net.minecraft.network.play.client.CPacketChatMessage
import java.util.*

/**
 * Created by 086 on 8/04/2018.
 * Updated by dominikaaaa on 12/03/20
 */
@Module.Info(
        name = "ChatAppend",
        category = Module.Category.CHAT,
        description = "Add a custom ending to your message!",
        showOnArray = Module.ShowOnArray.OFF
)
class CustomChat : Module() {
    @JvmField
    var textMode: Setting<TextMode> = register(Settings.e("Message", TextMode.NAME))
    private val decoMode = register(Settings.e<DecoMode>("Separator", DecoMode.NONE))
    private val commands = register(Settings.b("Commands", false))
    @JvmField
    var customText: Setting<String> = register(Settings.s("Custom Text", "unchanged"))

    private enum class DecoMode {
        SEPARATOR, CLASSIC, NONE
    }

    enum class TextMode {
        NAME, GROUP, WEBSITE, POPBOB, CRYSTAL, KINODUPE, EZCLIENT, AURORA, XDOLFCLIENT, NHACK, CUSTOM
    }

    private fun getText(t: TextMode): String {
        return when (t) {
            TextMode.NAME -> "乇乂ㄖᗪㄩ丂"
            TextMode.GROUP -> "ᴜᴘꜱɪʟᴏɴ"
            TextMode.WEBSITE -> "乇乂ㄖᗪㄩ丂.乇乂乇"
            TextMode.POPBOB -> "ᴘᴏᴘʙᴏʙ.ᴄᴄ"
            TextMode.CRYSTAL -> "ᴄʀʏꜱᴛᴀʟᴀᴜʀᴀ.ᴇᴢ"
            TextMode.KINODUPE -> "ᴋɪɴᴏᴅᴜᴘᴇ"
            TextMode.EZCLIENT -> "ᴇᴢᴄʟɪᴇɴᴛ.ɢᴏᴅ"
            TextMode.AURORA -> "ᴀᴜʀᴏʀᴀ"
            TextMode.XDOLFCLIENT -> "xᴅᴏʟꜰ ᴄʟɪᴇɴᴛ"
            TextMode.NHACK -> "ɴʜᴀᴄᴋ"
            TextMode.CUSTOM -> customText.value
        }
    }

    private fun getFull(d: DecoMode): String {
        return when (d) {
            DecoMode.NONE -> " " + getText(textMode.value)
            DecoMode.CLASSIC -> " \u00ab " + getText(textMode.value) + " \u00bb"
            DecoMode.SEPARATOR -> " " + KamiMod.separator + " " + getText(textMode.value)
        }
    }

    @EventHandler
    private val listener = Listener(EventHook { event: PacketEvent.Send ->
        if (event.packet is CPacketChatMessage) {
            var s = (event.packet as CPacketChatMessage).getMessage()
            if (!commands.value && isCommand(s)) return@EventHook
            s += getFull(decoMode.value)

            if (s.length >= 256) s = s.substring(0, 256)
            (event.packet as CPacketChatMessage).message = s
        }
    })

    private fun isCommand(s: String): Boolean {
        for (value in cmdCheck) {
            if (s.startsWith(value)) return true
        }
        return false
    }

    override fun onUpdate() {
        if (startTime == 0L) startTime = System.currentTimeMillis()
        if (startTime + 5000 <= System.currentTimeMillis()) { // 5 seconds in milliseconds
            if (textMode.value == TextMode.CUSTOM && customText.value.equals("unchanged", ignoreCase = true) && mc.player != null) {
                MessageSendHelper.sendWarningMessage("&rIn order to use the custom " + name + ", please run the &9" + Command.getCommandPrefix() + "customchat&r command to change it!")
            }
            startTime = System.currentTimeMillis()
        }
    }

    companion object {
        @JvmField
        var cmdCheck = arrayOf("/", "!", "-", "#", Command.getCommandPrefix(), ChatEncryption.delimiterValue.value)
        private var startTime: Long = 0
    }
}