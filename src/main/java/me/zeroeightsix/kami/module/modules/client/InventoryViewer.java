package me.zeroeightsix.kami.module.modules.client;

import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourConverter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static me.zeroeightsix.kami.util.GuiFrameUtil.getFrameByName;
import static me.zeroeightsix.kami.util.MessageSendHelper.sendDisableMessage;

/**
 * Updated by dominikaaaa on 21/02/20
 * Slight updates by 20kdc, 19/02/20
 * Everything except somethingRender() methods was written by dominikaaaa
 */
@Module.Info(
        name = "InventoryViewer",
        category = Module.Category.CLIENT,
        description = "Configures Inventory Viewer's options",
        showOnArray = Module.ShowOnArray.OFF
)
public class InventoryViewer extends Module {
    private Setting<Boolean> mcTexture = register(Settings.b("Use ResourcePack", false));
    private Setting<Boolean> coloredBackground = register(Settings.booleanBuilder("Colored Background").withValue(true).withVisibility(v -> !mcTexture.getValue()).build());
    private Setting<Integer> a = register(Settings.integerBuilder("Transparency").withMinimum(0).withValue(32).withMaximum(255).withVisibility(v -> coloredBackground.getValue() && !mcTexture.getValue()).build());
    private Setting<Integer> r = register(Settings.integerBuilder("Red").withMinimum(0).withValue(0).withMaximum(255).withVisibility(v -> coloredBackground.getValue() && !mcTexture.getValue()).build());
    private Setting<Integer> g = register(Settings.integerBuilder("Green").withMinimum(0).withValue(150).withMaximum(255).withVisibility(v -> coloredBackground.getValue() && !mcTexture.getValue()).build());
    private Setting<Integer> b = register(Settings.integerBuilder("Blue").withMinimum(0).withValue(0).withMaximum(255).withVisibility(v -> coloredBackground.getValue() && !mcTexture.getValue()).build());

    private enum ViewSize {
        LARGE, MEDIUM, SMALL
    }

    private void boxRender(final int x, final int y) {
        // SET UNRELIABLE DEFAULTS (Don't restore these) {
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        // }

        // ENABLE LOCAL CHANGES {
        GlStateManager.disableDepth();
        // }
        if (coloredBackground.getValue()) { // 1 == 2 px in game
            Gui.drawRect(x, y, x + 162, y + 54, ColourConverter.rgbToInt(r.getValue(), g.getValue(), b.getValue(), a.getValue()));
        }
    }

    @Override
    public void onRender() {
        Frame frame = getFrameByName("inventory viewer");
        if (frame == null)
            return;
        if (frame.isPinned() && !frame.isMinimized()) {
            final NonNullList<ItemStack> items = mc.player.inventory.mainInventory;
            boxRender(frame.getX(), frame.getY());
            itemRender(items, frame.getX(), frame.getY());
        }
    }

    private void itemRender(final NonNullList<ItemStack> items, final int x, final int y) {
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        for (int size = items.size(), item = 9; item < size; ++item) {
            final int slotX = x + 1 + item % 9 * 18;
            final int slotY = y + 1 + (item / 9 - 1) * 18;
            preItemRender();
            mc.getRenderItem().renderItemAndEffectIntoGUI(items.get(item), slotX, slotY);
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, items.get(item), slotX, slotY);
            postItemRender();
        }
    }

    // These methods should apply and clean up in pairs.
    // That means that if a pre* has to disableAlpha, the post* function should enableAlpha.
    //  - 20kdc

    private static void preItemRender() {
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        // Yes, this is meant to be paired with disableStandardItemLighting - 20kdc
        RenderHelper.enableGUIStandardItemLighting();
    }

    private static void postItemRender() {
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
    }

    public void onDisable() { sendDisableMessage(this.getClass()); }
}
