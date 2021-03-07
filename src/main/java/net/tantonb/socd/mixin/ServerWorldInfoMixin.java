package net.tantonb.socd.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.storage.ServerWorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Suppress experimental world warning dialog, credit to CorgiTaco:
 *
 *  https://github.com/CorgiTaco/ShutupExperimentalSettings/blob/master/src/main/java/corgitaco/shutupexperimentalsettings/mixin/client/MixinServerWorldInfo.java
 */

@Mixin(ServerWorldInfo.class)
public class ServerWorldInfoMixin {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject(method = "getLifecycle", at = @At("HEAD"), cancellable = true)
    private void forceStableLifeCycle(CallbackInfoReturnable<Lifecycle> cir) {
        LOGGER.info("Preventing experimental world warning dialog.");
        cir.setReturnValue(Lifecycle.stable());
    }
}
