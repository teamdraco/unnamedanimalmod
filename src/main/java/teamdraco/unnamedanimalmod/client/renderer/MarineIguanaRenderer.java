package teamdraco.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.MarineIguanaModel;
import teamdraco.unnamedanimalmod.common.entity.MarineIguanaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Map;

public class MarineIguanaRenderer extends MobRenderer<MarineIguanaEntity, MarineIguanaModel<MarineIguanaEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_1.png"));
        hashMap.put(1, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_2.png"));
        hashMap.put(2, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_3.png"));
        hashMap.put(3, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_4.png"));
        hashMap.put(4, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_5.png"));
    });

    public MarineIguanaRenderer(EntityRendererManager manager) {
        super(manager, new MarineIguanaModel<>(), 0.4f);
        this.addLayer(new MarineIguanaGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MarineIguanaEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }
}
