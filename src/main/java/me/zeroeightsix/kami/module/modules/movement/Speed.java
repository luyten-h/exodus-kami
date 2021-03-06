package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.util.math.MathHelper;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(
        name = "Speed",
        description = "Allows you to move faster, use other speed modules on 8b8t",
        category = Module.Category.MOVEMENT
)
public class Speed extends Module {
    int waitCounter;
    int forward = 1;

    private Setting<Boolean> jump = register(Settings.booleanBuilder("Jump").withValue(true).build());

    public void onUpdate(){

        boolean boost = Math.abs(mc.player.rotationYawHead - mc.player.rotationYaw) < 90;

        if(mc.player.moveForward != 0) {
            if(!mc.player.isSprinting()) mc.player.setSprinting(true);
            float yaw = mc.player.rotationYaw;
            if(mc.player.moveForward > 0) {
                if(mc.player.movementInput.moveStrafe != 0) {
                    yaw += (mc.player.movementInput.moveStrafe > 0) ? -45 : 45;
                }
                forward = 1;
                mc.player.moveForward = 1.0f;
                mc.player.moveStrafing = 0;
            }else if(mc.player.moveForward < 0) {
                if(mc.player.movementInput.moveStrafe != 0) {
                    yaw += (mc.player.movementInput.moveStrafe > 0) ? 45 : -45;
                }
                forward = -1;
                mc.player.moveForward = -1.0f;
                mc.player.moveStrafing = 0;
            }
            if (mc.player.onGround) {
                mc.player.setJumping(false);
                if (waitCounter < 1) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
                float f = (float)Math.toRadians(yaw);
                if(jump.getValue()) {
                    mc.player.motionY = 0.405;
                    mc.player.motionX -= (double) (MathHelper.sin(f) * 0.2f) * forward;
                    mc.player.motionZ += (double) (MathHelper.cos(f) * 0.2f) * forward;
                } else {
                    if(mc.gameSettings.keyBindJump.isPressed()){
                        mc.player.motionY = 0.405;
                        mc.player.motionX -= (double) (MathHelper.sin(f) * 0.2f) * forward;
                        mc.player.motionZ += (double) (MathHelper.cos(f) * 0.2f) * forward;
                    }
                }
            } else {
                if (waitCounter < 1) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
                double currentSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
                double speed = boost ? 1.0064 : 1.001;
                if(mc.player.motionY < 0) speed = 1;

                double direction = Math.toRadians(yaw);
                mc.player.motionX = (-Math.sin(direction) * speed * currentSpeed) * forward;
                mc.player.motionZ = (Math.cos(direction) * speed * currentSpeed) * forward;
            }
        }
    }
}
