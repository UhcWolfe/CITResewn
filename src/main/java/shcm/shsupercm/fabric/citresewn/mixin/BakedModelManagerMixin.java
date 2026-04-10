package shcm.shsupercm.fabric.citresewn.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shcm.shsupercm.fabric.citresewn.cit.ActiveCITs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.minecraft.util.profiler.DummyProfiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Initializes the (re)loading of active cits in the resource manager.
 * @see ActiveCITs
 */
@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {
    /**
     * @see ActiveCITs#load(ResourceManager, net.minecraft.util.profiler.Profiler)
     */
    @Inject(method = "reload", at = @At("HEAD"))
    private void citresewn$loadCITs(ResourceReloader.Synchronizer synchronizer, ResourceManager manager, Executor prepareExecutor, Executor applyExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        ActiveCITs.load(manager, DummyProfiler.INSTANCE);
    }
}

