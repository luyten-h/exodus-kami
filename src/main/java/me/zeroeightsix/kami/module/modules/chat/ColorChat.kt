package me.zeroeightsix.kami.module.modules.chat

import me.zero.alpine.listener.EventHandler
import me.zero.alpine.listener.EventHook
import me.zero.alpine.listener.Listener
import me.zeroeightsix.kami.event.events.PacketEvent
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.setting.Settings
import me.zeroeightsix.kami.util.MathsUtils
import net.minecraft.network.play.client.CPacketChatMessage
import java.util.*

/**
 * @author dominikaaaa
 * Updated by dominikaaaa on 12/03/20
 */
@Module.Info(
        name = "ColorChat",
        category = Module.Category.CHAT,
        description = "Makes your messages automatically colored using the ChatCo plugin",
        showOnArray = Module.ShowOnArray.OFF
)
class ColorChat : Module() {
    private val green = register(Settings.b(">", false))
    private val red = register(Settings.b("£", true))
    private val orange = register(Settings.b("$", true))
    private val blue = register(Settings.b("'", true))
    private val commands = register(Settings.b("Commands", false))

    private fun getText(s: String): String {
        var string = s
        if (green.value) string = greenConverter(string)
        if (red.value) string = redConverter(string)
        if (orange.value) string = orangeConverter(string)
        if (blue.value) string = blueConverter(string)
        return string
    }

    private fun greenConverter(input: String): String {
        return "> $input"
    }

    private fun redConverter(input: String): String {
        return "£ $input"
    }

    private fun orangeConverter(input: String): String {
        return "$ $input"
    }

    private fun blueConverter(input: String): String {
        return "' $input"
    }

    @EventHandler
    private val listener = Listener(EventHook { event: PacketEvent.Send ->
        if (event.packet is CPacketChatMessage) {
            var s = (event.packet as CPacketChatMessage).getMessage()

            if (!commands.value && isCommand(s)) return@EventHook
            s = getText(s)

            if (s.length >= 256) s = s.substring(0, 256)
            (event.packet as CPacketChatMessage).message = s
        }
    })

    override fun getHudInfo(): String {
        val returned = StringBuilder()
        if (green.value) {
            returned.append(" >")
        }
        if (red.value) {
            returned.append(" £")
        }
        if (orange.value) {
            returned.append(" $")
        }
        if (blue.value) {
            returned.append(" '")
        }
        return returned.toString()
    }

    private fun isCommand(s: String): Boolean {
        for (value in CustomChat.cmdCheck) {
            if (s.startsWith(value)) return true
        }
        return false
    }

    private fun leetConverter(input: String): String {
        val message = StringBuilder()
        for (element in input) {
            var inputChar = element.toString() + ""
            inputChar = inputChar.toLowerCase()
            inputChar = leetSwitch(inputChar)
            message.append(inputChar)
        }
        return message.toString()
    }

    private fun mockingConverter(input: String): String {
        val message = StringBuilder()
        for (i in input.indices) {
            var inputChar = input[i].toString() + ""
            var rand = 0
            inputChar = if (!MathsUtils.isNumberEven(i + rand)) inputChar.toUpperCase() else inputChar.toLowerCase()
            message.append(inputChar)
        }
        return message.toString()
    }

    private fun leetSwitch(i: String): String {
        return when (i) {
            "a" -> "4"
            "e" -> "3"
            "g" -> "6"
            "l", "i" -> "1"
            "o" -> "0"
            "s" -> "$"
            "t" -> "7"
            else -> i
        }
    }

    companion object {
        private val random = Random()
    }
}
