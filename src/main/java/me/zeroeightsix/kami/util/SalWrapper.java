package me.zeroeightsix.kami.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SalWrapper
{
    final static Minecraft mc = Minecraft.getMinecraft();

    public static Minecraft GetMC()
    {
        return mc;
    }

    public static EntityPlayerSP GetPlayer()
    {
        return mc.player;
    }
}