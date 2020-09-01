package me.zeroeightsix.kami.module.modules.gui;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourTextFormatting;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

import static me.zeroeightsix.kami.util.ColourTextFormatting.toTextMap;

@Module.Info(
        name = "Coordinates",
        category = Module.Category.GUI,
        description = "Shows your coordinates!"
)
public class Coordinates extends Module {
    private Setting<Boolean> nether = register(Settings.booleanBuilder("Nether").withValue(true).build());
    private Setting<Boolean> coloredValues = register(Settings.booleanBuilder("Colored Values").withValue(true).build());
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("Rainbow").withValue(true).build());
    private Setting<Integer> coordX = register(Settings.integerBuilder("X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> coordY = register(Settings.integerBuilder("Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> colorRed = register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorBlue = register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorGreen = register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(40).build());
    public Setting<ColourTextFormatting.ColourCode> valueColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Value Colour").withValue(ColourTextFormatting.ColourCode.WHITE).build());
    public Setting<ColourTextFormatting.ColourCode> bracketColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Bracket Colour").withValue(ColourTextFormatting.ColourCode.GRAY).build());
    public int redForBG;
    public int greenForBG;
    public int blueForBG;
    int rgb;
    int cX;
    int cY;
    public boolean rainbowBG;
    CFontRenderer font;

    public Coordinates() {
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
        boolean inHell = (mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell"));
        final int cX = coordX.getValue();
        final int cY = coordY.getValue();
        int posX = (int) mc.player.posX;
        int posY = (int) mc.player.posY;
        int posZ = (int) mc.player.posZ;
        float f = !inHell ? 0.125f : 8;
        int hposX = (int) (mc.player.posX * f);
        int hposZ = (int) (mc.player.posZ * f);
        String colOverworld = (getStringColour(setToText(valueColour.getValue())) + posX + getStringColour(setToText(bracketColour.getValue())) + ", " + getStringColour(setToText(valueColour.getValue())) + posY + getStringColour(setToText(bracketColour.getValue())) + ", " + getStringColour(setToText(valueColour.getValue())) + posZ);
        String colNether = (getStringColour(setToText(bracketColour.getValue())) + "[ " + getStringColour(setToText(valueColour.getValue())) + hposX + getStringColour(setToText(bracketColour.getValue())) + ", " + getStringColour(setToText(valueColour.getValue())) + hposZ + getStringColour(setToText(bracketColour.getValue())) + " ]");
        String uncolOverworld = (posX + ", " + posY + ", " + posZ);
        String uncolNether = ("[ " + hposX + ", " + hposZ + " ]");
        settingList.forEach(setting -> this.checkSettingGuiColour(setting));
        settingList.forEach(setting -> this.checkRainbowSetting(setting));
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        if (this.rainbow.getValue()) {
            if (this.nether.getValue()) {
                if (this.coloredValues.getValue()) {
                    this.font.drawStringWithShadow(getStringColour(setToText(bracketColour.getValue())) + "XYZ " + colOverworld + " " + colNether, cX, cY, rgb);
                } else {
                    this.font.drawStringWithShadow("XYZ " + uncolOverworld + " " + uncolNether, cX, cY, rgb);
                }
            } else {
                if (this.coloredValues.getValue()) {
                    this.font.drawStringWithShadow(getStringColour(setToText(bracketColour.getValue())) + "XYZ " + colOverworld, cX, cY, rgb);
                } else {
                    this.font.drawStringWithShadow("XYZ " + uncolOverworld, cX, cY, rgb);
                }
            }
        } else {
            if (this.nether.getValue()) {
                if (this.coloredValues.getValue()) {
                    this.font.drawStringWithShadow(getStringColour(setToText(bracketColour.getValue())) + "XYZ " + colOverworld + " " + colNether, cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } else {
                    this.font.drawStringWithShadow("XYZ " + uncolOverworld + " " + uncolNether, cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            } else {
                if (this.coloredValues.getValue()) {
                    this.font.drawStringWithShadow(getStringColour(setToText(bracketColour.getValue())) + "XYZ " + colOverworld, cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } else {
                    this.font.drawStringWithShadow("XYZ " + uncolOverworld, cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            }
        }
    }
}
