package me.zeroeightsix.kami.module.modules.gui;

import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourTextFormatting;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

import static me.zeroeightsix.kami.util.ColourTextFormatting.toTextMap;
import static net.minecraft.init.Items.TOTEM_OF_UNDYING;
import static net.minecraft.init.Items.END_CRYSTAL;
import static net.minecraft.init.Items.GOLDEN_APPLE;

@Module.Info(
        name = "Items",
        category = Module.Category.GUI,
        description = "Shows the counter of certain items"
)
public class Items extends Module {
    private Setting<Boolean> customColourV = register(Settings.booleanBuilder("Colored Number").withValue(true).build());
    private Setting<Boolean> totemsST = register(Settings.booleanBuilder("Totems").withValue(true).build());
    private Setting<Boolean> crystalsST = register(Settings.booleanBuilder("Crystals").withValue(true).build());
    private Setting<Boolean> gapplesST = register(Settings.booleanBuilder("Gapples").withValue(true).build());
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("Rainbow").withValue(true).build());
    private Setting<Integer> totemX = register(Settings.integerBuilder("Totem-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> totemY = register(Settings.integerBuilder("Totem-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> crystalX = register(Settings.integerBuilder("Crystal-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> crystalY = register(Settings.integerBuilder("Crystal-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> gappleX = register(Settings.integerBuilder("Gapple-X").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> gappleY = register(Settings.integerBuilder("Gapple-Y").withMinimum(1).withMaximum(5000).withValue(2).build());
    private Setting<Integer> colorRed = register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorBlue = register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(40).build());
    private Setting<Integer> colorGreen = register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(40).build());
    public Setting<ColourTextFormatting.ColourCode> customColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Custom Colour").withValue(ColourTextFormatting.ColourCode.WHITE).build());
    public int redForBG;
    public int greenForBG;
    public int blueForBG;
    int rgb;
    int tX;
    int tY;
    int cX;
    int cY;
    int gX;
    int gY;
    public boolean rainbowBG;
    CFontRenderer font;

    public Items() {
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

    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

    public static String getStringColour(TextFormatting c) {
        return c.toString();
    }

    private TextFormatting setToText(ColourTextFormatting.ColourCode colourCode) {
        return toTextMap.get(colourCode);
    }

    @Override
    public void onRender() {
        final int tX = totemX.getValue();
        final int tY = totemY.getValue();
        final int cX = crystalX.getValue();
        final int cY = crystalY.getValue();
        final int gX = gappleX.getValue();
        final int gY = gappleY.getValue();
        settingList.forEach(setting -> this.checkSettingGuiColour(setting));
        settingList.forEach(setting -> this.checkRainbowSetting(setting));
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        if (this.rainbow.getValue()) {
            if (this.customColourV.getValue()) {
                if (this.totemsST.getValue()) {
                    this.font.drawStringWithShadow("Totems: " + getStringColour(setToText(customColour.getValue())) + getItems(TOTEM_OF_UNDYING), tX, tY, rgb);
                } if (this.crystalsST.getValue()) {
                    this.font.drawStringWithShadow("Crystals: " + getStringColour(setToText(customColour.getValue())) + getItems(END_CRYSTAL), cX, cY, rgb);
                } if (this.gapplesST.getValue()) {
                    this.font.drawStringWithShadow("Gapples: " + getStringColour(setToText(customColour.getValue())) + getItems(GOLDEN_APPLE), gX, gY, rgb);
                }
            } else {
                if (this.totemsST.getValue()) {
                    this.font.drawStringWithShadow("Totems: " + getItems(TOTEM_OF_UNDYING), tX, tY, rgb);
                } if (this.crystalsST.getValue()) {
                    this.font.drawStringWithShadow("Crystals: " + getItems(END_CRYSTAL), cX, cY, rgb);
                } if (this.gapplesST.getValue()) {
                    this.font.drawStringWithShadow("Gapples: " + getItems(GOLDEN_APPLE), gX, gY, rgb);
                }
            }
        } else {
            if (this.customColourV.getValue()) {
                if (this.totemsST.getValue()) {
                    this.font.drawStringWithShadow("Totems: " + getStringColour(setToText(customColour.getValue())) + getItems(TOTEM_OF_UNDYING), tX, tY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.crystalsST.getValue()) {
                    this.font.drawStringWithShadow("Crystals: " + getStringColour(setToText(customColour.getValue())) + getItems(END_CRYSTAL), cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.gapplesST.getValue()) {
                    this.font.drawStringWithShadow("Gapples: " + getStringColour(setToText(customColour.getValue())) + getItems(GOLDEN_APPLE), gX, gY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            } else {
                if (this.totemsST.getValue()) {
                    this.font.drawStringWithShadow("Totems: " + getItems(TOTEM_OF_UNDYING), tX, tY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.crystalsST.getValue()) {
                    this.font.drawStringWithShadow("Crystals: " + getItems(END_CRYSTAL), cX, cY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                } if (this.gapplesST.getValue()) {
                    this.font.drawStringWithShadow("Gapples: " + getItems(GOLDEN_APPLE), gX, gY, ColourUtils.toRGBA(this.redForBG, this.greenForBG, this.blueForBG, 255));
                }
            }
        }
    }
}
