package shcm.shsupercm.fabric.citresewn.defaults.mixin.common;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shcm.shsupercm.fabric.citresewn.cit.CITType;
import shcm.shsupercm.fabric.citresewn.defaults.common.ResewnItemModelIdentifier;
import shcm.shsupercm.fabric.citresewn.defaults.mixin.types.item.JsonUnbakedModelAccessor;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Will be rewritten at some point.
 */
/*? <1.21.5 {*/
@Mixin(/*? <1.21.2 {*//*ModelLoader.class*//*?} else {*/ModelBaker.class/*?}*/)
public class ModelLoaderMixin {
    @Inject(method = "loadModelFromJson", cancellable = true, at = @At("HEAD"))
    public void citresewn$forceLiteralResewnModelIdentifier(Identifier originalId, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        if (originalId instanceof ResewnItemModelIdentifier resewnIdentifier) {
            InputStream is = null;
            try {
                JsonUnbakedModel json = JsonUnbakedModel.deserialize(IOUtils.toString(is = MinecraftClient.getInstance().getResourceManager().open(resewnIdentifier.getIdentifier()), StandardCharsets.UTF_8));
                json.id = resewnIdentifier.getIdentifier().toString();
                ((JsonUnbakedModelAccessor) json).getOverrides().clear();
                ((JsonUnbakedModelAccessor) json).getOverrides().addAll(resewnIdentifier.getOverrides().stream().map(override -> new ModelOverride(override.modelId(), override.conditions())).collect(Collectors.toList()));

                cir.setReturnValue(json);
            } catch (Exception ignored) {
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }

    @Inject(method = "resolveModel", cancellable = true, at = @At("HEAD"))
    public void citresewn$resolveLiteralResewnModelIdentifier(Identifier id, CallbackInfoReturnable<Either> cir) {
        if (id instanceof ResewnItemModelIdentifier resewnIdentifier)
            cir.setReturnValue(Either.left(resewnIdentifier.getIdentifier()));
    }
}
/*?}*/

