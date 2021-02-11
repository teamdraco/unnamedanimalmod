package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.FlashlightFishEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlashlightFishModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer dorsalfin;
    public ModelRenderer analfin;
    public ModelRenderer pelvicfinleft;
    public ModelRenderer pelvicfinright;

    public FlashlightFishModel() {
        this.textureWidth = 20;
        this.textureHeight = 20;
        this.dorsalfin = new ModelRenderer(this, 6, 9);
        this.dorsalfin.setRotationPoint(0.0F, -1.5F, 1.5F);
        this.dorsalfin.addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.pelvicfinright = new ModelRenderer(this, 0, -1);
        this.pelvicfinright.setRotationPoint(-0.5F, 1.5F, -1.0F);
        this.pelvicfinright.addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pelvicfinright, 0.0F, 0.0F, 0.3490658503988659F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.analfin = new ModelRenderer(this, 0, 0);
        this.analfin.setRotationPoint(0.0F, 1.5F, 2.0F);
        this.analfin.addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.pelvicfinleft = new ModelRenderer(this, 0, -1);
        this.pelvicfinleft.mirror = true;
        this.pelvicfinleft.setRotationPoint(0.5F, 1.5F, -1.0F);
        this.pelvicfinleft.addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pelvicfinleft, 0.0F, 0.0F, -0.3490658503988659F);
        this.tail = new ModelRenderer(this, 0, 6);
        this.tail.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.tail.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.dorsalfin);
        this.body.addChild(this.pelvicfinright);
        this.body.addChild(this.analfin);
        this.body.addChild(this.pelvicfinleft);
        this.body.addChild(this.tail);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }

        this.tail.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
