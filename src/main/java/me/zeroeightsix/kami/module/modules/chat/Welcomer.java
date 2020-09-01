package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Files;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.zeroeightsix.kami.util.MessageSendHelper;

import java.util.*;


@Module.Info(
        name = "Welcomer",
        category = Module.Category.CHAT,
        description = "Spams chat by welcoming people",
        showOnArray = Module.ShowOnArray.OFF
)
public class Welcomer extends Module {
    private Setting joinMsg = this.register(Settings.b("Join", true));
    private Setting leaveMsg = this.register(Settings.b("Leave", true));
    private Setting clientSideOnly = this.register(Settings.b("ClientSideOnly", true));
    private Setting greentext = this.register(Settings.b("Green Text", false));
    private Setting redtext = this.register(Settings.b("Red Text", true));
    private Setting messageDelaySecs = this.register(Settings.d("DelaySeconds", 5.0D));
    private List prevPlayers;
    private long lastMessageTicks;
    private String JOIN_MESSAGES_FILE = "ExodusJoinMessages.txt";
    private String LEAVE_MESSAGES_FILE = "ExodusLeaveMessages.txt";

    public void onEnable() {
        this.prevPlayers = this.getCurrentPlayers();
        this.lastMessageTicks = System.currentTimeMillis();
    }

    private String getJoinMessage(String name) {
        String[] messages = Files.readFileAllLines(this.JOIN_MESSAGES_FILE);
        if (messages == null) {
            Files.writeFile(this.JOIN_MESSAGES_FILE, new String[]{"Hello, $", "Greetings, $"});
            return "Hello " + name;
        } else if (messages.length == 0) {
            return "Hello " + name;
        } else {
            String msg = messages[(new Random()).nextInt(messages.length)];
            return msg.replace("$", name);
        }
    }

    private String getLeaveMessage(String name) {
        String[] messages = Files.readFileAllLines(this.LEAVE_MESSAGES_FILE);
        if (messages == null) {
            Files.writeFile(this.LEAVE_MESSAGES_FILE, new String[]{"Goodbye, $", "Farewell, $"});
            return "Goodbye " + name;
        } else if (messages.length == 0) {
            return "Goodbye " + name;
        } else {
            String msg = messages[(new Random()).nextInt(messages.length)];
            return msg.replace("$", name);
        }
    }

    public void onUpdate() {
        List currentPlayers = this.getCurrentPlayers();
        if (currentPlayers == null) {
            this.prevPlayers = null;
        } else {
            if (this.prevPlayers == null) {
                this.prevPlayers = this.getCurrentPlayers();
            }

            boolean msg = false;
            Iterator var3 = this.prevPlayers.iterator();

            String currentPlayer;
            while(var3.hasNext()) {
                currentPlayer = (String)var3.next();
                if (!currentPlayers.contains(currentPlayer) && (Boolean)this.leaveMsg.getValue()) {
                    this.writeChat(this.getLeaveMessage(currentPlayer));
                }
            }

            var3 = currentPlayers.iterator();

            while(var3.hasNext()) {
                currentPlayer = (String)var3.next();
                if (!this.prevPlayers.contains(currentPlayer) && (Boolean)this.joinMsg.getValue()) {
                    this.writeChat(this.getJoinMessage(currentPlayer));
                }
            }

            this.prevPlayers = currentPlayers;
        }
    }

    private List getCurrentPlayers() {
        if (mc.getConnection() == null) {
            return null;
        } else {
            Collection playerInfos = mc.getConnection().getPlayerInfoMap();
            if (playerInfos == null) {
                return null;
            } else {
                ArrayList ret = new ArrayList();
                Iterator it = playerInfos.iterator();

                while(it.hasNext()) {
                    ret.add(((NetworkPlayerInfo)it.next()).getGameProfile().getName());
                }

                return ret;
            }
        }
    }

    private void writeChat(String msg) {
        if (msg.length() != 0) {
            msg = msg.replace("\n", "");
            msg = msg.replace("\r", "");
            long now = System.currentTimeMillis();
            long delayMillis = (long)((Double)this.messageDelaySecs.getValue() * 1000.0D);
            if (now - this.lastMessageTicks >= delayMillis) {
                if ((Boolean)this.clientSideOnly.getValue()) {
                    MessageSendHelper.sendChatMessage(msg);
                } else if ((Boolean)this.greentext.getValue()) {
                    mc.playerController.connection.sendPacket(new CPacketChatMessage("> " + msg));
                } else if ((Boolean)this.redtext.getValue()) {
                    mc.playerController.connection.sendPacket(new CPacketChatMessage("£ " + msg));
                } else
                    mc.playerController.connection.sendPacket(new CPacketChatMessage(msg));

                this.lastMessageTicks = now;
            }

        }
    }
}