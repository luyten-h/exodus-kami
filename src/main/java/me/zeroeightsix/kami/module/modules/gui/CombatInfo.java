package me.zeroeightsix.kami.module.modules.gui;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourTextFormatting;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.Minecraft;

import java.awt.*;

import static me.zeroeightsix.kami.KamiMod.MODULE_MANAGER;
import static me.zeroeightsix.kami.util.ColourTextFormatting.toTextMap;

@Module.Info(
        name = "CombatInfo",
        category = Module.Category.GUI,
        description = "Shows certain modules and if they are enabled or not"
)
public class CombatInfo extends Module {
    /* fuck my life this module was horrible to make */
    private Setting<Page> page = register(Settings.enumBuilder(Page.class).withName("Page").withValue(Page.ONE).build());
    /* Page One */
    private Setting<Boolean> coloredValues = register(Settings.booleanBuilder("Colored Values").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> autoCrystalO = register(Settings.booleanBuilder("AutoCrystalO").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> explodeCrystal = register(Settings.booleanBuilder("CrystalExplode").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> autoCrystalY = register(Settings.booleanBuilder("AutoCrystalY").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> legCrystals = register(Settings.booleanBuilder("LegCrystals").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> autoTotem = register(Settings.booleanBuilder("AutoTotem").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> surround = register(Settings.booleanBuilder("Surround").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> autoTrap = register(Settings.booleanBuilder("AutoTrap").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> killAura = register(Settings.booleanBuilder("KillAura").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> blink = register(Settings.booleanBuilder("Blink").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("Rainbow").withValue(true).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Integer> colorRed = register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(40).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Integer> colorBlue = register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(40).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    private Setting<Integer> colorGreen = register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(40).withVisibility(v -> page.getValue().equals(Page.ONE)).build());
    /* Page Two */
    private Setting<Integer> caoXm = register(Settings.integerBuilder("CrystalAuraO-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> caoYm = register(Settings.integerBuilder("CrystalAuraO-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> ecXm = register(Settings.integerBuilder("ExplodeCrystal-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> ecYm = register(Settings.integerBuilder("ExplodeCrystal-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> cayXm = register(Settings.integerBuilder("CrystalAuraY-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> cayYm = register(Settings.integerBuilder("CrystalAuraY-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> lgXm = register(Settings.integerBuilder("LegCrystals-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> lgYm = register(Settings.integerBuilder("LegCrystals-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atXm = register(Settings.integerBuilder("AutoTotem-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atYm = register(Settings.integerBuilder("AutoTotem-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> suXm = register(Settings.integerBuilder("Surround-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> suYm = register(Settings.integerBuilder("Surround-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atpXm = register(Settings.integerBuilder("AutoTrap-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atpYm = register(Settings.integerBuilder("AutoTrap-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atpxXm = register(Settings.integerBuilder("AutoTrapX-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> atpxYm = register(Settings.integerBuilder("AutoTrapX-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> kaXm = register(Settings.integerBuilder("KillAura-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> kaYm = register(Settings.integerBuilder("KillAura-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> kaxXm = register(Settings.integerBuilder("KillAuraX-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> kaxYm = register(Settings.integerBuilder("KillAuraX-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> blXm = register(Settings.integerBuilder("Blink-X").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    private Setting<Integer> blYm = register(Settings.integerBuilder("Blink-Y").withMinimum(1).withMaximum(5000).withValue(2).withVisibility(v -> page.getValue().equals(Page.TWO)).build());
    public Setting<ColourTextFormatting.ColourCode> trueColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("True Colour").withValue(ColourTextFormatting.ColourCode.GREEN).build());
    public Setting<ColourTextFormatting.ColourCode> falseColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("False Colour").withValue(ColourTextFormatting.ColourCode.RED).build());
    public int redForBG;
    public int greenForBG;
    public int blueForBG;
    int rgb;
    int caoX;
    int caoY;
    int ecX;
    int ecY;
    int cayX;
    int cayY;
    int lgX;
    int lgY;
    int atX;
    int atY;
    int suX;
    int suY;
    int atpX;
    int atpY;
    int atpxX;
    int atpxY;
    int kaX;
    int kaY;
    int kaxX;
    int kaxY;
    int blX;
    int blY;
    public boolean rainbowBG;
    CFontRenderer font;

    public CombatInfo() {
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

    private enum Page { ONE, TWO }

    @Override
    public void onRender() {
        final int caoX = caoXm.getValue();
        final int caoY = caoYm.getValue();
        final int ecX = ecXm.getValue();
        final int ecY = ecYm.getValue();
        final int cayX = cayXm.getValue();
        final int cayY = cayYm.getValue();
        final int lgX = lgXm.getValue();
        final int lgY = lgYm.getValue();
        final int atX = atXm.getValue();
        final int atY = atYm.getValue();
        final int suX = suXm.getValue();
        final int suY = suYm.getValue();
        final int atpX = atpXm.getValue();
        final int atpY = atpYm.getValue();
        final int atpxX = atpxXm.getValue();
        final int atpxY = atpxYm.getValue();
        final int kaX = kaXm.getValue();
        final int kaY = kaYm.getValue();
        final int kaxX = kaxXm.getValue();
        final int kaxY = kaxYm.getValue();
        final int blX = blXm.getValue();
        final int blY = blYm.getValue();
        settingList.forEach(setting -> this.checkSettingGuiColour(setting));
        settingList.forEach(setting -> this.checkRainbowSetting(setting));
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        if (this.rainbow.getValue()) {
            if (this.coloredValues.getValue()) {
                if (this.autoCrystalO.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalO")) {
                        this.font.drawStringWithShadow("CAO: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", caoX, caoY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CAO: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", caoX, caoY, rgb);
                    }
                }
                if (this.explodeCrystal.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("CrystalExplode")) {
                        this.font.drawStringWithShadow("CE: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", ecX, ecY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CE: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", ecX, ecY, rgb);
                    }
                }
                if (this.autoCrystalY.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalY")) {
                        this.font.drawStringWithShadow("CAY: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", cayX, cayY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CAY: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", cayX, cayY, rgb);
                    }
                }
                if (this.legCrystals.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("LegCrystals")) {
                        this.font.drawStringWithShadow("LG: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", lgX, lgY, rgb);
                    } else {
                        this.font.drawStringWithShadow("LG: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", lgX, lgY, rgb);
                    }
                }
                if (this.autoTotem.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTotem")) {
                        this.font.drawStringWithShadow("AT: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", atX, atY, rgb);
                    } else {
                        this.font.drawStringWithShadow("AT: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", atX, atY, rgb);
                    }
                }
                if (this.surround.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Surround")) {
                        this.font.drawStringWithShadow("SU: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", suX, suY, rgb);
                    } else {
                        this.font.drawStringWithShadow("SU: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", suX, suY, rgb);
                    }
                }
                if (this.autoTrap.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTrap")) {
                        this.font.drawStringWithShadow("ATP: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", atpX, atpY, rgb);
                    } else {
                        this.font.drawStringWithShadow("ATP: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", atpX, atpY, rgb);
                    }
                }
                if (this.killAura.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("KillAura")) {
                        this.font.drawStringWithShadow("KA: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", kaX, kaY, rgb);
                    } else {
                        this.font.drawStringWithShadow("KA: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", kaX, kaY, rgb);
                    }
                }
                if (this.blink.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Blink")) {
                        this.font.drawStringWithShadow("BL: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", blX, blY, rgb);
                    } else {
                        this.font.drawStringWithShadow("BL: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", blX, blY, rgb);
                    }
                }
            } else {
                if (this.autoCrystalO.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalO")) {
                        this.font.drawStringWithShadow("CAO: " + "FALSE", caoX, caoY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CAO: " + "TRUE", caoX, caoY, rgb);
                    }
                }
                if (this.explodeCrystal.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("CrystalExplode")) {
                        this.font.drawStringWithShadow("CAZ: " + "FALSE", ecX, ecY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CAZ: " + "TRUE", ecX, ecY, rgb);
                    }
                }
                if (this.autoCrystalY.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalY")) {
                        this.font.drawStringWithShadow("CAY: " + "FALSE", cayX, cayY, rgb);
                    } else {
                        this.font.drawStringWithShadow("CAY: " + "TRUE", cayX, cayY, rgb);
                    }
                }
                if (this.legCrystals.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("LegCrystals")) {
                        this.font.drawStringWithShadow("LG: " + "FALSE", lgX, lgY, rgb);
                    } else {
                        this.font.drawStringWithShadow("LG: " + "TRUE", lgX, lgY, rgb);
                    }
                }
                if (this.autoTotem.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTotem")) {
                        this.font.drawStringWithShadow("AT: " + "FALSE", atX, atY, rgb);
                    } else {
                        this.font.drawStringWithShadow("AT: " + "TRUE", atX, atY, rgb);
                    }
                }
                if (this.surround.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Surround")) {
                        this.font.drawStringWithShadow("SU: " + "FALSE", suX, suY, rgb);
                    } else {
                        this.font.drawStringWithShadow("SU: " + "TRUE", suX, suY, rgb);
                    }
                }
                if (this.autoTrap.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTrap")) {
                        this.font.drawStringWithShadow("ATP: " + "FALSE", atpX, atpY, rgb);
                    } else {
                        this.font.drawStringWithShadow("ATP: " + "TRUE", atpX, atpY, rgb);
                    }
                }
                if (this.killAura.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("KillAura")) {
                        this.font.drawStringWithShadow("KA: " + "FALSE", kaX, kaY, rgb);
                    } else {
                        this.font.drawStringWithShadow("KA: " + "TRUE", kaX, kaY, rgb);
                    }
                }
                if (this.blink.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Blink")) {
                        this.font.drawStringWithShadow("BL: " + "FALSE", blX, blY, rgb);
                    } else {
                        this.font.drawStringWithShadow("BL: " + "TRUE", blX, blY, rgb);
                    }
                }
            }
        } else {
            if (this.coloredValues.getValue()) {
                if (this.autoCrystalO.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalO")) {
                        this.font.drawStringWithShadow("CAO: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", caoX, caoY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAO: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", caoX, caoY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.explodeCrystal.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("CrystalExplode")) {
                        this.font.drawStringWithShadow("CAZ: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", ecX, ecY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAZ: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", ecX, ecY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoCrystalY.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalY")) {
                        this.font.drawStringWithShadow("CAY: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", cayX, cayY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAY: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", cayX, cayY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.legCrystals.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("LegCrystals")) {
                        this.font.drawStringWithShadow("LG: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", lgX, lgY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("LG: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", lgX, lgY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoTotem.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTotem")) {
                        this.font.drawStringWithShadow("AT: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", atX, atY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("AT: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", atX, atY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.surround.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Surround")) {
                        this.font.drawStringWithShadow("SU: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", suX, suY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("SU: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", suX, suY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoTrap.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTrap")) {
                        this.font.drawStringWithShadow("ATP: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", atpX, atpY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("ATP: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", atpX, atpY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.killAura.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("KillAura")) {
                        this.font.drawStringWithShadow("KA: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", kaX, kaY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("KA: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", kaX, kaY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.blink.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Blink")) {
                        this.font.drawStringWithShadow("BL: " + getStringColour(setToText(falseColour.getValue())) + "FALSE", blX, blY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("BL: " + getStringColour(setToText(trueColour.getValue())) + "TRUE", blX, blY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
            } else {
                if (this.autoCrystalO.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalO")) {
                        this.font.drawStringWithShadow("CAO: " + "FALSE", caoX, caoY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAO: " + "TRUE", caoX, caoY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.explodeCrystal.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("CrystalExplode")) {
                        this.font.drawStringWithShadow("CAZ: " + "FALSE", ecX, ecY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAZ: " + "TRUE", ecX, ecY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoCrystalY.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoCrystalY")) {
                        this.font.drawStringWithShadow("CAY: " + "FALSE", cayX, cayY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("CAY: " + "TRUE", cayX, cayY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.legCrystals.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("LegCrystals")) {
                        this.font.drawStringWithShadow("LG: " + "FALSE", lgX, lgY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("LG: " + "TRUE", lgX, lgY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoTotem.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTotem")) {
                        this.font.drawStringWithShadow("AT: " + "FALSE", atX, atY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("AT: " + "TRUE", atX, atY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.surround.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Surround")) {
                        this.font.drawStringWithShadow("SU: " + "FALSE", suX, suY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("SU: " + "TRUE", suX, suY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.autoTrap.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("AutoTrap")) {
                        this.font.drawStringWithShadow("ATP: " + "FALSE", atpX, atpY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("ATP: " + "TRUE", atpX, atpY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.killAura.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("KillAura")) {
                        this.font.drawStringWithShadow("KA: " + "FALSE", kaX, kaY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("KA: " + "TRUE", kaX, kaY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
                if (this.blink.getValue()) {
                    if (!MODULE_MANAGER.isModuleEnabled("Blink")) {
                        this.font.drawStringWithShadow("BL: " + "FALSE", blX, blY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    } else {
                        this.font.drawStringWithShadow("BL: " + "TRUE", blX, blY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                    }
                }
            }
        }
    }
}
