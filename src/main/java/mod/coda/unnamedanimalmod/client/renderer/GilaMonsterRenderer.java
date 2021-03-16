package mod.coda.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.GilaMonsterModel;
import mod.coda.unnamedanimalmod.entity.GilaMonsterEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Map;

public class GilaMonsterRenderer extends MobRenderer<GilaMonsterEntity, GilaMonsterModel<GilaMonsterEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/gila_monster.png");

    public GilaMonsterRenderer(EntityRendererManager manager) {
        super(manager, new GilaMonsterModel<>(), 0.4f);
    }

    @Override
    public ResourceLocation getEntityTexture(GilaMonsterEntity entity) {
        return TEXTURE;
    }
}