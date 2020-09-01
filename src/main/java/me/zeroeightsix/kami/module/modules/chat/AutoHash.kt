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
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author okk
 * custom module by okk
 * ily -astolfo
 */
@Module.Info(
        name = "AutoHash",
        category = Module.Category.CHAT,
        description = "Bypasses AntiSpammer",
        showOnArray = Module.ShowOnArray.OFF
)
class BypassAntiSpammer : Module() {
    private val commands = register(Settings.b("Commands", false))

    private fun getText(s: String): String {
        var string = s
        string = returnBypassedMessage(string)
        return string
    }
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
    private fun returnBypassedMessage(input: String): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var randomChars = ""
        for (i in 0..31) {
            randomChars += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        var md5HashofChar = randomChars.md5()
        return input +  " " + "[" + md5HashofChar + "]"
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
        returned.append(" MD5")
        return returned.toString()
    }

    private fun isCommand(s: String): Boolean {
        for (value in CustomChat.cmdCheck) {
            if (s.startsWith(value)) return true
        }
        return false
    }
}
