package me.zeroeightsix.kami.event;

import me.zero.alpine.type.Cancellable;
import me.zeroeightsix.kami.util.SalWrapper;

public class MinecraftEvent extends Cancellable
{
    private Era era = Era.PRE;
    private final float partialTicks;

    public MinecraftEvent()
    {
        partialTicks = SalWrapper.GetMC().getRenderPartialTicks();
    }

    public MinecraftEvent(Era p_Era)
    {
        partialTicks = SalWrapper.GetMC().getRenderPartialTicks();
        era = p_Era;
    }

    public Era getEra()
    {
        return era;
    }

    public float getPartialTicks()
    {
        return partialTicks;
    }

    public enum Era
    {
        PRE,
        PERI,
        POST
    }

}