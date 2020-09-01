package me.zeroeightsix.kami.module.modules.gui;

import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourTextFormatting;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.InfoCalculator;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.Minecraft;

import java.awt.*;

import static me.zeroeightsix.kami.util.ColourTextFormatting.toTextMap;
import static me.zeroeightsix.kami.util.InfoCalculator.speed;

@Module.Info(
        name = "Info",
        category = Module.Category.GUI,
        description = "Shows some useful info"
)
public class Info extends Module {
    private Setting<Boolean> coloredValues = register(Settings.booleanBuilder("Colored Values").withValue(false).build());
    private Setting<Boolean> durability = register(Settings.booleanBuilder("Durability").withValue(false).build());
    private Setting<Boolean> speed = register(Settings.booleanBuilder("Speed").withValue(true).build());
    private Setting<Boolean> ping = register(Settings.booleanBuilder("Ping").withValue(true).build());
    private Setting<Boolean> fps = register(Settings.booleanBuilder("FPS").withValue(true).build());
    private Setting<SpeedUnit> speedUnit = register(Settings.enumBuilder(SpeedUnit.class).withName("Speed Unit").withValue(SpeedUnit.KMH).build());
    private Setting<Integer> decimalPlaces = register(Settings.integerBuilder("Decimal Places").withValue(2).withMinimum(0).withMaximum(10));
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("Rainbow").withValue(true).build());
    private Setting<Integer> duraX = register(Settings.integerBuilder("Dura-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> duraY = register(Settings.integerBuilder("Dura-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> speedX = register(Settings.integerBuilder("Speed-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> speedY = register(Settings.integerBuilder("Speed-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> pingX = register(Settings.integerBuilder("Ping-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> pingY = register(Settings.integerBuilder("Ping-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> fpsX = register(Settings.integerBuilder("FPS-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> fpsY = register(Settings.integerBuilder("FPS-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> colorRed = register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorBlue = register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorGreen = register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(40).build());
    public Setting<ColourTextFormatting.ColourCode> customColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Custom Colour").withValue(ColourTextFormatting.ColourCode.WHITE).build());
    public int redForBG;
    public int greenForBG;
    public int blueForBG;
    int rgb;
    int dX;
    int dY;
    int sX;
    int sY;
    int pX;
    int pY;
    int fX;
    int fY;
    public boolean rainbowBG;
    CFontRenderer font;

    public Info() {
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

    public static String getStringColour(TextFormatting c) {
        return c.toString();
    }

    private TextFormatting setToText(ColourTextFormatting.ColourCode colourCode) {
        return toTextMap.get(colourCode);
    }

    @Override
    public void onRender() {
        final int dX = duraX.getValue();
        final int dY = duraY.getValue();
        final int sX = speedX.getValue();
        final int sY = speedY.getValue();
        final int pX = pingX.getValue();
        final int pY = pingY.getValue();
        final int fX = fpsX.getValue();
        final int fY = fpsY.getValue();
        settingList.forEach(setting -> this.checkSettingGuiColour(setting));
        settingList.forEach(setting -> this.checkRainbowSetting(setting));
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        if (this.rainbow.getValue()) {
            if (this.coloredValues.getValue()) {
                if (this.durability.getValue()) {
                    this.font.drawStringWithShadow("Durability: " + getStringColour(setToText(customColour.getValue())) + InfoCalculator.dura(mc), dX, dY, rgb);
                } if (this.speed.getValue()) {
                    this.font.drawStringWithShadow("Speed: " + getStringColour(setToText(customColour.getValue())) + speed(useUnitKmH(), mc, decimalPlaces.getValue()) + " " + unitType(this.speedUnit.getValue()), sX, sY, rgb);
                } if (this.ping.getValue()) {
                    this.font.drawStringWithShadow("Ping: " + getStringColour(setToText(customColour.getValue())) + InfoCalculator.ping(mc) + "ms", pX, pY, rgb);
                } if (this.fps.getValue()) {
                    this.font.drawStringWithShadow("FPS: " + getStringColour(setToText(customColour.getValue())) + Minecraft.debugFPS, fX, fY, rgb);
                }
            } else {
                if (this.durability.getValue()) {
                    this.font.drawStringWithShadow("Durability: " + InfoCalculator.dura(mc), dX, dY, rgb);
                } if (this.speed.getValue()) {
                    this.font.drawStringWithShadow("Speed: " + speed(useUnitKmH(), mc, decimalPlaces.getValue()) + " " + unitType(this.speedUnit.getValue()), sX, sY, rgb);
                } if (this.ping.getValue()) {
                    this.font.drawStringWithShadow("Ping: " + InfoCalculator.ping(mc) + "ms", pX, pY, rgb);
                } if (this.fps.getValue()) {
                    this.font.drawStringWithShadow("FPS: " + Minecraft.debugFPS, fX, fY, rgb);
                }
            }
        } else {
            if (this.coloredValues.getValue()) {
                if (this.durability.getValue()) {
                    this.font.drawStringWithShadow("Durability: " + getStringColour(setToText(customColour.getValue())) + InfoCalculator.dura(mc), dX, dY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.speed.getValue()) {
                    this.font.drawStringWithShadow("Speed: " + getStringColour(setToText(customColour.getValue())) + speed(useUnitKmH(), mc, decimalPlaces.getValue()) + " " + unitType(this.speedUnit.getValue()), sX, sY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.ping.getValue()) {
                    this.font.drawStringWithShadow("Ping: " + getStringColour(setToText(customColour.getValue())) + InfoCalculator.ping(mc) + "ms", pX, pY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.fps.getValue()) {
                    this.font.drawStringWithShadow("FPS: " + getStringColour(setToText(customColour.getValue())) + Minecraft.debugFPS, fX, fY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            } else {
                if (this.durability.getValue()) {
                    this.font.drawStringWithShadow("Durability: " + InfoCalculator.dura(mc), dX, dY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.speed.getValue()) {
                    this.font.drawStringWithShadow("Speed: " + speed(useUnitKmH(), mc, decimalPlaces.getValue()) + " " + unitType(this.speedUnit.getValue()), sX, sY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.ping.getValue()) {
                    this.font.drawStringWithShadow("Ping: " + InfoCalculator.ping(mc) + "ms", pX, pY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.fps.getValue()) {
                    this.font.drawStringWithShadow("FPS: " + Minecraft.debugFPS, fX, fY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            }
        }
    }

    private enum SpeedUnit { MPS, KMH }

    public boolean useUnitKmH() {
        return speedUnit.getValue().equals(SpeedUnit.KMH);
    }

    private String unitType(SpeedUnit s) {
        switch (s) {
            case MPS: return "M/S";
            case KMH: return "KM/H";
            default: return "Invalid unit type (mps or kmh)";
        }
    }
}
