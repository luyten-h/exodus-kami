package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;

/**
 * @author 086
 */
@Module.Info(
        name = "NoHurtCam",
        description = "Disables the 'hurt' camera effect",
        category = Module.Category.COMBAT
)
public class NoHurtCam extends Module {

    private static NoHurtCam INSTANCE;

    public NoHurtCam() {
        INSTANCE = this;
    }

    public static boolean shouldDisable() {
        return INSTANCE != null && INSTANCE.isEnabled();
    }

}
