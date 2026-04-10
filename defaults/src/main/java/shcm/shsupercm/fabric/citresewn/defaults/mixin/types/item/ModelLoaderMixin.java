package shcm.shsupercm.fabric.citresewn.defaults.mixin.types.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shcm.shsupercm.fabric.citresewn.CITResewn;
import shcm.shsupercm.fabric.citresewn.cit.CIT;
import shcm.shsupercm.fabric.citresewn.defaults.cit.types.TypeItem;
import shcm.shsupercm.fabric.citresewn.defaults.common.ResewnItemModelIdentifier;

import java.util.*;

import static shcm.shsupercm.fabric.citresewn.CITResewn.info;
import static shcm.shsupercm.fabric.citresewn.defaults.cit.types.TypeItem.CONTAINER;

/*? <1.21.5 {*/
@Mixin(/*? <1.21.2 {*//*ModelLoader.class*//*?} else {*/ModelBaker.class/*?}*/)
public class ModelLoaderMixin {
    @Shadow @Final private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow @Final private Map<ModelIdentifier, UnbakedModel> modelsToBake;
    @Shadow @Final private Map<ModelIdentifier, BakedModel> bakedModels;


    @Inject(method = "<init>", at =
    @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    public void citresewn$addTypeItemModels(BlockColors blockColors, Profiler profiler, Map map, Map map2, CallbackInfo ci) {
        profiler.swap("citresewn:type_item_models");
        if (!CONTAINER.active())
            return;

        info("Loading item CIT models...");
        for (CIT<TypeItem> cit : CONTAINER.loaded)
            try {
                cit.type.loadUnbakedAssets(MinecraftClient.getInstance().getResourceManager());

                for (JsonUnbakedModel unbakedModel : cit.type.unbakedAssets.values()) {
                    Identifier id = ResewnItemModelIdentifier.pack(Identifier.tryParse(unbakedModel.id));
                    this.unbakedModels.put(id, unbakedModel);
                }
            } catch (Exception e) {
                CITResewn.logErrorLoading("Errored loading model in " + cit.propertiesIdentifier + " from " + cit.packName);
                e.printStackTrace();
            }

        TypeItem.GENERATED_SUB_CITS_SEEN.clear();
    }

    @Inject(method = "bake", at = @At("RETURN"))
    public void citresewn$linkTypeItemModels(/*? <1.21 {*//*java.util.function.BiFunction<Identifier, SpriteIdentifier, Sprite> spriteLoader*//*?} else {*//*? <1.21.2 {*//*ModelLoader.SpriteGetter spriteGetter*//*?} else {*/ModelBaker.SpriteGetter spriteGetter/*?}*/ /*?}*/, CallbackInfo ci) {
        if (!CONTAINER.active())
            return;

        for (CIT<TypeItem> cit : CONTAINER.loaded)
            for (Map.Entry<String, JsonUnbakedModel> entry : cit.type.unbakedAssets.entrySet()) {
                Identifier id = ResewnItemModelIdentifier.pack(Identifier.tryParse(entry.getValue().id));
                /* (Baked model linking disabled for 1.21.2+ until new hook found) */
            }
    }
}
/*?}*/

