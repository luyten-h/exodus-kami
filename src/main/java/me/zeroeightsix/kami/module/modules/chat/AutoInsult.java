package me.zeroeightsix.kami.module.modules.chat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.zeroeightsix.kami.module.Module;

@Module.Info(
        name = "Insulter",
        description = "Sends a random insult in chat from this list, and annoys the person you are maybe fighting with, or just for random annoying spam",
        category = Module.Category.CHAT
)
public class AutoInsult extends Module {
    protected void onEnable() {
        List myList = Arrays.asList("Sorry, who are you again?", "You just got ez'd by ObamaHack!", " Nice photoshop, I didn't die.", "Someone sounds mad", " Someone sounds angry", "Be quiet kid, it's past your bedtime!", "You just got fucked by Impact's AutoCrystal!", "Sorry i dont speak 漢字漢字漢字漢字漢字漢字漢字", "Cuck", "You're just mad ObamaHack's on top!", "you suck nerd", "commit anvil hat");
        Random r = new Random();
        int randomitem = r.nextInt(myList.size());
        String randomElement = (String)myList.get(randomitem);
        mc.player.sendChatMessage(randomElement);
        super.toggle();
    }
}