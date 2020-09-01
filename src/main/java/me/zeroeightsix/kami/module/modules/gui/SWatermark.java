package me.zeroeightsix.kami.module.modules.gui;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;

import java.awt.*;

@Module.Info(
        name = "SWatermark",
        category = Module.Category.GUI,
        description = "A second watermark without reason"
)
public class SWatermark extends Module {
    private Setting<Boolean> fullWebsite = register(Settings.booleanBuilder("Full Website").withValue(true).build());
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("Rainbow").withValue(true).build());
    private Setting<Integer> coordX = register(Settings.integerBuilder("X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> coordY = register(Settings.integerBuilder("Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> colorRed = register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorBlue = register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorGreen = register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(40).build());
    public int redForBG;
    public int greenForBG;
    public int blueForBG;
    int rgb;
    int cX;
    int cY;
    public boolean rainbowBG;
    CFontRenderer font;

    public SWatermark() {
        this.font = new CFontRenderer(new Font("Arial", 0, 18), true, true);
    }

    private void checkSettingGuiColour(final Setting setting) {
        final String name3;
        final String s;
        final String name2 = s = (name3 = setting.getName());
        switch (s) {
            case "Red": {
                this.redForBG = colorRed.getValue();
                break;
            }
            case "Green": {
                this.greenForBG = colorBlue.getValue();
                break;
            }
            case "Blue": {
                this.blueForBG = colorGreen.getValue();
                break;
            }
        }
    }

    private void checkRainbowSetting(final Setting setting) {
        final String name3;
        final String s;
        final String name2 = s = (name3 = setting.getName());
        switch (s) {
            case "Rainbow": {
                this.rainbowBG = rainbow.getValue();
                break;
            }
        }
    }

    public int getArgb() {
        final int argb = ColourUtils.toRGBA(this.colorRed.getValue(), this.colorGreen.getValue(), this.colorBlue.getValue(), 255);
        return argb;
    }

    @Override
    public void onRender() {
        final int cX = coordX.getValue();
        final int cY = coordY.getValue();
        settingList.forEach(setting -> this.checkSettingGuiColour(setting));
        settingList.forEach(setting -> this.checkRainbowSetting(setting));
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        if (this.rainbow.getValue()) {
            if (this.fullWebsite.getValue()) {
                this.font.drawStringWithShadow("Exodus.exe/download.html", cX, cY, rgb);
            } else {
                this.font.drawStringWithShadow("Exodus.exe", cX, cY, rgb);
            }
        } else {
            if (this.fullWebsite.getValue()) {
                this.font.drawStringWithShadow("Exodus.exe/download.html", cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
            } else {
                this.font.drawStringWithShadow("Exodus.exe", cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
            }
        }
    }
}
