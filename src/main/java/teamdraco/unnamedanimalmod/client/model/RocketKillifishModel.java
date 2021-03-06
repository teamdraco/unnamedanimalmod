package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RocketKillifishModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer fins;
    public ModelRenderer tail;
    public ModelRenderer leftPectoralFin;
    public ModelRenderer rightPectoralFin;

    public RocketKillifishModel() {
        this.texWidth = 16;
        this.texHeight = 16;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 23.5F, 0.0F);
        this.body.addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 4, 3);
        this.tail.setPos(0.0F, 0.0F, 2.5F);
        this.tail.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.fins = new ModelRenderer(this, 0, 5);
        this.fins.setPos(0.0F, 0.0F, 1.5F);
        this.fins.addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftPectoralFin = new ModelRenderer(this, 0, 1);
        this.leftPectoralFin.setPos(0.5F, 0.0F, -1.0F);
        this.leftPectoralFin.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftPectoralFin, 0.5235987755982988F, 0.0F, -0.08726646259971647F);
        this.rightPectoralFin = new ModelRenderer(this, 0, 1);
        this.rightPectoralFin.setPos(-0.5F, 0.0F, -1.0F);
        this.rightPectoralFin.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightPectoralFin, 0.5235987755982988F, 0.0F, 0.08726646259971647F);
        this.body.addChild(this.tail);
        this.body.addChild(this.fins);
        this.body.addChild(this.leftPectoralFin);
        this.body.addChild(this.rightPectoralFin);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }
        this.tail.yRot = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
