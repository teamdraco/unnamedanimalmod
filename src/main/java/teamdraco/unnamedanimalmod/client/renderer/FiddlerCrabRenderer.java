package teamdraco.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Util;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.BananaSlugModel;
import teamdraco.unnamedanimalmod.client.model.FiddlerCrabModel;
import teamdraco.unnamedanimalmod.common.entity.BananaSlugEntity;
import teamdraco.unnamedanimalmod.common.entity.FiddlerCrabEntity;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class FiddlerCrabRenderer extends MobRenderer<FiddlerCrabEntity, FiddlerCrabModel<FiddlerCrabEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_0.png"));
        hashMap.put(1, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_1.png"));
        hashMap.put(2, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_2.png"));
        hashMap.put(3, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_3.png"));
        hashMap.put(4, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_4.png"));
        hashMap.put(5, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_5.png"));
        hashMap.put(6, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_7.png"));
        hashMap.put(7, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_8.png"));
        hashMap.put(8, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_9.png"));
        hashMap.put(9, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_10.png"));
        hashMap.put(10, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_11.png"));
        hashMap.put(11, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_12.png"));
        hashMap.put(12, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_13.png"));
        hashMap.put(13, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_14.png"));
        hashMap.put(14, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_15.png"));
        hashMap.put(15, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_16.png"));
        hashMap.put(16, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_17.png"));
        hashMap.put(17, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_18.png"));
        hashMap.put(18, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_19.png"));
        hashMap.put(19, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_20.png"));
        hashMap.put(20, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/kungsime.png"));
    });

    public FiddlerCrabRenderer(EntityRendererManager manager) {
        super(manager, new FiddlerCrabModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(FiddlerCrabEntity entity) {
        String s = entity.getName().getString();

        if (s.equals("Kungsime")) {
            return TEXTURES.get(20);
        }
        else {
            return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
        }
    }

    @Override
    protected void setupRotations(FiddlerCrabEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(90));
    }
}
