package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.module.modules.gui.FakeClient;

import static me.zeroeightsix.kami.KamiMod.MODULE_MANAGER;
import static me.zeroeightsix.kami.util.MessageSendHelper.*;

/**
 * @author dominikaaaa
 * Created by dominikaaaa on 17/02/20
 */
public class FakeClientCommand extends Command {
    public FakeClientCommand() {
        super("fakeclient", new ChunkBuilder().append("ending").build(), "fake");
        setDescription("Allows you to change the Fake Client text!");
    }

    @Override
    public void call(String[] args) {
        FakeClient iO = MODULE_MANAGER.getModuleT(FakeClient.class);
        if (iO == null) {
            sendErrorMessage("&cThe InfoOverlay module is not available for some reason. Make sure the name you're calling is correct and that you have the module installed!!");
            return;
        }
        if (!iO.customizadfClient.getValue()) {
            sendWarningMessage("You don't have Custom Fake enabled in InfoOverlay!");
            sendWarningMessage("The command will still work, but will not visibly do anything.");
        }
        for (String s : args) {
            if (s == null)
                continue;
            iO.customClientVAL.setValue(s);
            sendChatMessage("Set the Custom Client Text to <" + s + ">");
        }
    }
}
