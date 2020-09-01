package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(
        name = "Step",
        category = Module.Category.MOVEMENT,
        description = "Steps up blocks"
)
public class Step extends Module{

    private final Setting<Integer> height = register(Settings.integerBuilder("Height").withValue(1).withMinimum(1).withMaximum(8));

    @Override
    public void onUpdate(){
        if (mc.player.collidedHorizontally && mc.player.onGround) {
            mc.player.stepHeight = (float)0.5f;
            mc.player.jump();
        } else
        {
            mc.player.stepHeight = height.getValue();
        }
    }

    @Override
    public void onDisable(){
        mc.player.stepHeight = (float) 0.5;
    }
}