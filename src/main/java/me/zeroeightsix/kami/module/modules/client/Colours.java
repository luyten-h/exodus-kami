package me.zeroeightsix.kami.module.modules.client;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourTextFormatting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import static me.zeroeightsix.kami.util.ColourTextFormatting.toTextMap;

@Module.Info(
        name = "Colours",
        category = Module.Category.CLIENT,
        description = "Cool stuff, used for code stuff",
        showOnArray = Module.ShowOnArray.OFF
)
public class Colours extends Module {
    public Setting<ColourTextFormatting.ColourCode> firstColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("First Colour").withValue(ColourTextFormatting.ColourCode.DARK_BLUE).build());
    public Setting<ColourTextFormatting.ColourCode> secondColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Second Colour").withValue(ColourTextFormatting.ColourCode.BLUE).build());
    public Setting<ColourTextFormatting.ColourCode> thirdColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Third Colour").withValue(ColourTextFormatting.ColourCode.GREEN).build());
    public Setting<ColourTextFormatting.ColourCode> fourthColour = register(Settings.enumBuilder(ColourTextFormatting.ColourCode.class).withName("Fourth Colour").withValue(ColourTextFormatting.ColourCode.RED).build());

    public static String getStringColour(TextFormatting c) {
        return c.toString();
    }

    private TextFormatting setToText(ColourTextFormatting.ColourCode colourCode) {
        return toTextMap.get(colourCode);
    }

    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }
}
