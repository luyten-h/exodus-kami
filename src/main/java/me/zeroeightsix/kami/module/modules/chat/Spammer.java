package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.zeroeightsix.kami.util.MessageSendHelper.*;

/**
 * @author dominikaaaa
 * Created by dominikaaaa on 10/04/20
 */
@Module.Info(
        name = "Spammer",
        description = "Spams text from a file on a set delay into the chat",
        category = Module.Category.CHAT
)
public class Spammer extends Module {
    private Setting<Mode> modeSetting = register(Settings.e("Order", Mode.RANDOM_ORDER));
    private Setting<Integer> timeoutTime = register(Settings.integerBuilder().withName("Timeout (s)").withMinimum(1).withMaximum(240).withValue(10).build());

    private List<String> tempLines = new ArrayList<>();
    private String[] spammer;
    private static int currentLine = 0;
    private enum Mode { IN_ORDER, RANDOM_ORDER }

    public void onEnable() {
        BufferedReader bufferedReader;
        try {
            sendChatMessage("&rTrying to find '&9spammer.txt&f'");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("spammer.txt"), "UTF-8"));
            String line;
            tempLines.clear();
            while ((line = bufferedReader.readLine()) != null) {
                tempLines.add(line);
            }
            bufferedReader.close();
            spammer = tempLines.toArray(new String[]{});
        } catch (FileNotFoundException exception) {
            sendErrorMessage("&rCouldn't find a file called '&9spammer.txt&f' inside your '&9.minecraft&f' folder, disabling");
            disable();
            return;
        } catch (IOException exception) {
            sendErrorMessage(exception.toString());
            return;
        }
        sendChatMessage("&rFound '&9spammer.txt&f'!");

    }

    public void onUpdate() {
        sendMsg();
    }

    private static long startTime = 0;
    private void sendMsg() {
        String message = "";
        if (startTime == 0) startTime = System.currentTimeMillis();

        if (startTime + (timeoutTime.getValue() * 1000) <= System.currentTimeMillis()) { // 1 timeout = 1 second = 1000 ms
            startTime = System.currentTimeMillis();
            switch (modeSetting.getValue()) {
                case IN_ORDER:
                    message = getOrdered(spammer);
                    break;
                case RANDOM_ORDER:
                    message = getRandom(spammer);
                    break;
            }
            sendServerMessage(message);
        }
    }

    private static String getOrdered(String[] array) {
        currentLine++;
        if (currentLine > array.length - 1) currentLine = 0;
        return array[currentLine];
    }

    private static String getRandom(String[] array) {
        int rand = new Random().nextInt(array.length);
        while (array[rand].isEmpty() || array[rand].equals(" ")) {
            rand = new Random().nextInt(array.length); // big meme to try to avoid sending empty messages
        }
        return array[rand];
    }
}
