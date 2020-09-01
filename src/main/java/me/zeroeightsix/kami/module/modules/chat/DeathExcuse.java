package me.zeroeightsix.kami.module.modules.chat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.zeroeightsix.kami.module.Module;

@Module.Info(
        name = "DeathExcuse",
        description = "Sends a excuse in chat after dying",
        category = Module.Category.CHAT
)
public class DeathExcuse extends Module {
    private int delay = 0;

    public void onUpdate() {
        ++this.delay;
        List myList = Arrays.asList("Dude.. I was playing on WWE client, uncool!" + "Bro wtf, my cat stepped on my keyboard pressed the F key on my sword, and then she sat on the keyboard and I couldn't fight back. Uncool broo!!" + "I accidentally forgot to put Exodus in my mods folder!!!" + "Bro dude the 19 tps stopped me from winning" + "Man bro why u so fat my internet died");
        Random r = new Random();
        int randomitem = r.nextInt(myList.size());
        String randomElement = (String)myList.get(randomitem);
        if (mc.player.isDead) {
            this.delay = 20000000;
        }

        if (this.delay > 20000100) {
            mc.player.sendChatMessage(randomElement);
            this.delay = 0;
        }

    }
}