package shcm.shsupercm.fabric.citresewn.defaults.mixin.types.enchantment;

/*? <1.21.2 {*//*
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor {
    @Accessor("GLINT_PROGRAM") static RenderPhase.ShaderProgram GLINT_SHADER() { throw new RuntimeException(); }
    @Accessor("ENTITY_GLINT_PROGRAM") static RenderPhase.ShaderProgram ENTITY_GLINT_SHADER() { throw new RuntimeException(); }
    @Accessor("DIRECT_ENTITY_GLINT_PROGRAM") static RenderPhase.ShaderProgram DIRECT_ENTITY_GLINT_SHADER() { throw new RuntimeException(); }
    @Accessor("DISABLE_CULLING") static RenderPhase.Cull DISABLE_CULLING() { throw new RuntimeException(); }
    @Accessor("EQUAL_DEPTH_TEST") static RenderPhase.DepthTest EQUAL_DEPTH_TEST() { throw new RuntimeException(); }
    @Accessor("COLOR_MASK") static RenderPhase.WriteMaskState COLOR_MASK() { throw new RuntimeException(); }
}
*//*?}*/
