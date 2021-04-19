package mod.coda.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.BlubberJellyModel;
import mod.coda.unnamedanimalmod.client.model.MangroveSnakeModel;
import mod.coda.unnamedanimalmod.entity.BlubberJellyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BlubberJellyRenderer extends MobRenderer<BlubberJellyEntity, BlubberJellyModel<BlubberJellyEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_1.png"));
        hashMap.put(1, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_2.png"));
        hashMap.put(2, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_3.png"));
        hashMap.put(3, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_4.png"));
        hashMap.put(4, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_5.png"));
        hashMap.put(5, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_6.png"));
        hashMap.put(6, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_7.png"));
        hashMap.put(7, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_8.png"));
        hashMap.put(8, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_9.png"));
        hashMap.put(9, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_10.png"));
        hashMap.put(10, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/blubber_jelly/blubber_jelly_11.png"));
    });

    public BlubberJellyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BlubberJellyModel<>(), 0.25F);
    }

    public ResourceLocation getEntityTexture(BlubberJellyEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }
}