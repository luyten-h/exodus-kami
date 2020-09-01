package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Files;
import me.zeroeightsix.kami.util.MessageSendHelper;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.*;


@Module.Info(
        name = "ClientJoin",
        category = Module.Category.CHAT,
        description = "Makes welcome messages cooler",
        showOnArray = Module.ShowOnArray.OFF
)
public class ClientJoin extends Module {
    private Setting joinMsg = this.register(Settings.b("Join", true));
    private Setting leaveMsg = this.register(Settings.b("Leave", true));
    private Setting messageDelaySecs = this.register(Settings.d("DelaySeconds", 5.0D));
    private List prevPlayers;
    private long lastMessageTicks;
    private String JOIN_MESSAGES_FILE = "ExodusClientJoin.txt";
    private String LEAVE_MESSAGES_FILE = "ExodusClientLeave.txt";

    public void onEnable() {
        this.prevPlayers = this.getCurrentPlayers();
        this.lastMessageTicks = System.currentTimeMillis();
    }

    private String getJoinMessage(String name) {
        String[] messages = Files.readFileAllLines(this.JOIN_MESSAGES_FILE);
        if (messages == null) {
            Files.writeFile(this.JOIN_MESSAGES_FILE, new String[]{"§7[§a+§7] $"});
            return KamiMod.colour + "7[" + KamiMod.colour + "a-" + KamiMod.colour + "7]" + name;
        } else if (messages.length == 0) {
            return KamiMod.colour + "7[" + KamiMod.colour + "a-" + KamiMod.colour + "7]" + name;
        } else {
            String msg = messages[(new Random()).nextInt(messages.length)];
            return msg.replace("$", name);
        }
    }

    private String getLeaveMessage(String name) {
        String[] messages = Files.readFileAllLines(this.LEAVE_MESSAGES_FILE);
        if (messages == null) {
            Files.writeFile(this.LEAVE_MESSAGES_FILE, new String[]{"§7[§c-§7] $"});
            return KamiMod.colour + "7[" + KamiMod.colour + "c-" + KamiMod.colour + "7] " + name;
        } else if (messages.length == 0) {
            return KamiMod.colour + "7[" + KamiMod.colour + "c-" + KamiMod.colour + "7]" + name;
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
                    MessageSendHelper.sendRawChatMessage(msg);

        }
    }
}}