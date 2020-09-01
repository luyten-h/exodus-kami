package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.MinecraftEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlayerDamageBlock extends MinecraftEvent {
    private BlockPos BlockPos;
    private EnumFacing Direction;

    public PlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
        BlockPos = posBlock;
        setDirection(directionFacing);
    }

    public BlockPos getPos() {
        return BlockPos;
    }

    /**
     * @return the direction
     */
    public EnumFacing getDirection() {
        return Direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(EnumFacing direction) {
        Direction = direction;
    }

}