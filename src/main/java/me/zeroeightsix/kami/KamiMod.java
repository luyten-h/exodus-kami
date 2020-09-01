package me.zeroeightsix.kami;

import me.zero.alpine.EventManager;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import com.google.gson.JsonPrimitive;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import java.util.Optional;
import java.util.Iterator;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import com.google.gson.JsonElement;
import java.util.Map;
import me.zeroeightsix.kami.setting.config.Configuration;
import java.nio.file.LinkOption;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.OldModuleManager;
import me.zeroeightsix.kami.setting.SettingsRegister;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.Wrapper;
import me.zeroeightsix.kami.util.LagCompensator;
import me.zeroeightsix.kami.event.ForgeEventProcessor;
import net.minecraftforge.common.MinecraftForge;
import java.util.function.Consumer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.zeroeightsix.kami.util.ColourUtils;
import java.awt.Color;
import me.zeroeightsix.kami.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.awt.Font;
import me.zeroeightsix.kami.setting.Settings;
import com.google.common.base.Converter;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import com.google.gson.JsonObject;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.command.CommandManager;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zero.alpine.EventBus;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.CommandManager;
import me.zeroeightsix.kami.event.ForgeEventProcessor;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.MacroManager;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.chat.ChatEncryption;
import me.zeroeightsix.kami.module.modules.client.CommandConfig;
import me.zeroeightsix.kami.module.modules.hidden.RunConfig;
import me.zeroeightsix.kami.process.TemporaryPauseProcess;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.SettingsRegister;
import me.zeroeightsix.kami.setting.config.Configuration;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.LagCompensator;
import me.zeroeightsix.kami.util.RichPresence;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 086 on 7/11/2017.
 * Updated by dominikaaaa on 25/03/19
 * Updated by Dewy on 09/04/2020
 */
@Mod(
        modid = KamiMod.MODID,
        name = KamiMod.MODNAME,
        version = KamiMod.MODVER
)
public class KamiMod {

    public static final String MODNAME = "Exodus";
    public static final String MODID = "exodus";
    public static final String MODVER = "Insider"; // this is changed to v1.x.x-commit for debugging during travis releases
    public static final String MODVERSMALL = "Insider"; // shown to the user
    public static final String MODVERBROAD = "Insider"; // used for update checking
    public static final String MODVERTITLE = "Insider"; // used for the tab name

    public static final String MCVER = "1.12.2";

    public static final String APP_ID = "724333306786873364";

    private static final String UPDATE_JSON = " ";
    public static final String DONATORS_JSON = " ";
    public static final String CAPES_JSON = " ";
    public static final String GITHUB_LINK = " ";
    public static final String WEBSITE_LINK = " ";

    public static final char colour = '\u00A7';
    public static final char separator = '\u23d0';

    private static final String KAMI_CONFIG_NAME_DEFAULT = "ExodusConfig.json";

    public static final Logger log = LogManager.getLogger("Exodus");

    public static final EventBus EVENT_BUS = new EventManager();
    public static final ModuleManager MODULE_MANAGER = new ModuleManager();

    public static String latest; // latest version (null if no internet or exception occurred)
    public static boolean isLatest;
    public static boolean hasAskedToUpdate = false;

    public static TemporaryPauseProcess pauseProcess;

    @Mod.Instance
    private static KamiMod INSTANCE;

    public KamiGUI guiManager;
    public CommandManager commandManager;
    private Setting<JsonObject> guiStateSetting = Settings.custom("gui", new JsonObject(), new Converter<JsonObject, JsonObject>() {
        @Override
        protected JsonObject doForward(JsonObject jsonObject) {
            return jsonObject;
        }

        @Override
        protected JsonObject doBackward(JsonObject jsonObject) {
            return jsonObject;
        }
    }).buildAndRegister("");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        updateCheck();

        pauseProcess = new TemporaryPauseProcess();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (MODULE_MANAGER.getModuleT(CommandConfig.class).customTitle.getValue()) {
            Display.setTitle("Exodus " + MODVERTITLE);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        log.info("\n\nInitializing Exodus");

        MODULE_MANAGER.register();

        MODULE_MANAGER.getModules().stream().filter(module -> module.alwaysListening).forEach(EVENT_BUS::subscribe);
        MinecraftForge.EVENT_BUS.register(new ForgeEventProcessor());
        LagCompensator.INSTANCE = new LagCompensator();

        Wrapper.init();

        guiManager = new KamiGUI();
        guiManager.initializeGUI();

        commandManager = new CommandManager();

        Friends.initFriends();

        MacroManager.INSTANCE.registerMacros();

        /* Custom static Settings, which can't register normally if they're static */
        SettingsRegister.register("commandPrefix", Command.commandPrefix);
        SettingsRegister.register("delimiterV", ChatEncryption.delimiterValue);
        loadConfiguration();
        log.info("Settings loaded");

        new RichPresence();
        log.info("Rich Presence Users init!\n");

        // After settings loaded, we want to let the enabled modules know they've been enabled (since the setting is done through reflection)
        MODULE_MANAGER.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);

        // load modules that are on by default // autoenable
        MODULE_MANAGER.getModule(RunConfig.class).enable();

        log.info("Exodus Mod initialized!\n");
    }

    public static String getConfigName() {
        Path config = Paths.get("ExodusLastConfig.txt");
        String kamiConfigName = KAMI_CONFIG_NAME_DEFAULT;
        try (BufferedReader reader = Files.newBufferedReader(config)) {
            kamiConfigName = reader.readLine();
            if (!isFilenameValid(kamiConfigName)) kamiConfigName = KAMI_CONFIG_NAME_DEFAULT;
        } catch (NoSuchFileException e) {
            try (BufferedWriter writer = Files.newBufferedWriter(config)) {
                writer.write(KAMI_CONFIG_NAME_DEFAULT);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kamiConfigName;
    }

    public static void loadConfiguration() {
        try {
            loadConfigurationUnsafe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigurationUnsafe() throws IOException {
        String kamiConfigName = getConfigName();
        Path kamiConfig = Paths.get(kamiConfigName);
        if (!Files.exists(kamiConfig)) return;
        Configuration.loadConfiguration(kamiConfig);

        JsonObject gui = KamiMod.INSTANCE.guiStateSetting.getValue();
        for (Map.Entry<String, JsonElement> entry : gui.entrySet()) {
            Optional<Component> optional = KamiMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).filter(component -> ((Frame) component).getTitle().equals(entry.getKey())).findFirst();
            if (optional.isPresent()) {
                JsonObject object = entry.getValue().getAsJsonObject();
                Frame frame = (Frame) optional.get();
                frame.setX(object.get("x").getAsInt());
                frame.setY(object.get("y").getAsInt());
                Docking docking = Docking.values()[object.get("docking").getAsInt()];
                if (docking.isLeft()) ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.LEFT);
                else if (docking.isRight()) ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.RIGHT);
                else if (docking.isCenterVertical())
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.CENTER);
                frame.setDocking(docking);
                frame.setMinimized(object.get("minimized").getAsBoolean());
                frame.setPinned(object.get("pinned").getAsBoolean());
            } else {
                System.err.println("Found GUI config entry for " + entry.getKey() + ", but found no frame with that name");
            }
        }
        KamiMod.getInstance().getGuiManager().getChildren().stream().filter(component -> (component instanceof Frame) && (((Frame) component).isPinnable()) && component.isVisible()).forEach(component -> component.setOpacity(0f));
    }

    public static void saveConfiguration() {
        try {
            saveConfigurationUnsafe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfigurationUnsafe() throws IOException {
        JsonObject object = new JsonObject();
        KamiMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).map(component -> (Frame) component).forEach(frame -> {
            JsonObject frameObject = new JsonObject();
            frameObject.add("x", new JsonPrimitive(frame.getX()));
            frameObject.add("y", new JsonPrimitive(frame.getY()));
            frameObject.add("docking", new JsonPrimitive(Arrays.asList(Docking.values()).indexOf(frame.getDocking())));
            frameObject.add("minimized", new JsonPrimitive(frame.isMinimized()));
            frameObject.add("pinned", new JsonPrimitive(frame.isPinned()));
            object.add(frame.getTitle(), frameObject);
        });
        KamiMod.INSTANCE.guiStateSetting.setValue(object);

        Path outputFile = Paths.get(getConfigName());
        if (!Files.exists(outputFile))
            Files.createFile(outputFile);
        Configuration.saveConfiguration(outputFile);
        MODULE_MANAGER.getModules().forEach(Module::destroy);
    }

    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static KamiMod getInstance() {
        return INSTANCE;
    }

    public KamiGUI getGuiManager() {
        return guiManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void updateCheck() {
        try {
            KamiMod.log.info("Not attempting anything, there's no Update Check in Exodus.");

            JsonParser parser = new JsonParser();
            String latestVersion = parser.parse(IOUtils.toString(new URL(UPDATE_JSON))).getAsJsonObject().getAsJsonObject("version").get(MCVER + "-latest").getAsString();

            isLatest = latestVersion.equals(MODVERBROAD);
            latest = latestVersion;

            if (!isLatest) {
                KamiMod.log.warn("If you see this, contact Astolfo. There's no update check in Exodus.");

                return;
            }

            KamiMod.log.info("Your Exodus version is completely fine. No update check in exodus, it doesn't matter whether you're running an outdated version or not.");
        } catch (IOException e) {
            latest = null;

            KamiMod.log.error("If you see this, it's fine. There's no Update Check in Exodus, so you're completely fine!");
            e.printStackTrace();
        }
    }
}