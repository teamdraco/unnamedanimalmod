package teamdraco.unnamedanimalmod.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.ElephantnoseFishModel;
import teamdraco.unnamedanimalmod.client.model.LeafySeadragonModel;
import teamdraco.unnamedanimalmod.common.entity.ElephantnoseFishEntity;
import teamdraco.unnamedanimalmod.common.entity.LeafySeadragonEntity;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class LeafySeadragonRenderer extends MobRenderer<LeafySeadragonEntity, LeafySeadragonModel<LeafySeadragonEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/leafy_seadragon/leafy_seadragon_1.png"));
        hashMap.put(1, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/leafy_seadragon/leafy_seadragon_2.png"));
        hashMap.put(2, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/leafy_seadragon/leafy_seadragon_3.png"));
        hashMap.put(3, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/leafy_seadragon/leafy_seadragon_4.png"));
    });

    public LeafySeadragonRenderer(EntityRendererManager p_i48864_1_) {
        super(p_i48864_1_, new LeafySeadragonModel<>(), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(LeafySeadragonEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }

    @Override
    protected void setupRotations(LeafySeadragonEntity entity, MatrixStack matrixStack, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(entity, matrixStack, p_225621_3_, p_225621_4_, p_225621_5_);
        if (!entity.isInWater()) {
            matrixStack.translate(0.10000000149011612D, 0.10000000149011612D, -0.10000000149011612D);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }
        else {
            matrixStack.translate(0, 0.1, 0);
        }
    }
}
