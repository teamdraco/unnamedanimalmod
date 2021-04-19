package mod.coda.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.MangroveSnakeModel;
import mod.coda.unnamedanimalmod.entity.MangroveSnakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeRenderer extends MobRenderer<MangroveSnakeEntity, MangroveSnakeModel<MangroveSnakeEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/mangrove_snake/mangrove_snake.png"));
        hashMap.put(1, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/mangrove_snake/mangrove_snake_sulawesi.png"));
    });

    public MangroveSnakeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MangroveSnakeModel<>(), 0.5F);
    }

    public ResourceLocation getEntityTexture(MangroveSnakeEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }
}