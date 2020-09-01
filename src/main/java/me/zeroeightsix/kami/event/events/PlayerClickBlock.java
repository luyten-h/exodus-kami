package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.MinecraftEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlayerClickBlock extends MinecraftEvent {
    public BlockPos Location;
    public EnumFacing Facing;

    public PlayerClickBlock(BlockPos loc, EnumFacing face) {
        Location = loc;
        Facing = face;
    }
}