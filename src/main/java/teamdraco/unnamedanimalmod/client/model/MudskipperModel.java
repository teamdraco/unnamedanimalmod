package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MudskipperModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer tailfin;
    public ModelRenderer dorsalfinfirst;
    public ModelRenderer dorsalfinsecond;
    public ModelRenderer analfin;
    public ModelRenderer pelvicfins;
    public ModelRenderer eyes;
    public ModelRenderer pectoralfinright;
    public ModelRenderer pectoralfinleft;

    public MudskipperModel() {
        this.texWidth = 32;
        this.texHeight = 16;
        this.pelvicfins = new ModelRenderer(this, 0, 0);
        this.pelvicfins.setPos(0.0F, 1.0F, -3.5F);
        this.pelvicfins.addBox(-1.5F, 0.5F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 18, 9);
        this.head.setPos(0.0F, 0.0F, -4.0F);
        this.head.addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 22.5F, 1.0F);
        this.body.addBox(-1.0F, -1.5F, -4.0F, 2.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.dorsalfinsecond = new ModelRenderer(this, 0, 0);
        this.dorsalfinsecond.setPos(0.0F, -1.0F, 1.5F);
        this.dorsalfinsecond.addBox(0.0F, -1.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralfinleft = new ModelRenderer(this, 9, 0);
        this.pectoralfinleft.mirror = true;
        this.pectoralfinleft.setPos(1.5F, 1.0F, 0.0F);
        this.pectoralfinleft.addBox(-0.5F, 0.0F, -1.0F, 3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralfinright = new ModelRenderer(this, 9, 0);
        this.pectoralfinright.setPos(-1.5F, 1.0F, 0.0F);
        this.pectoralfinright.addBox(-2.5F, 0.0F, -1.0F, 3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tailfin = new ModelRenderer(this, 0, 7);
        this.tailfin.setPos(0.0F, -0.5F, 4.0F);
        this.tailfin.addBox(0.0F, -2.5F, -0.5F, 0.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.analfin = new ModelRenderer(this, 0, 0);
        this.analfin.setPos(0.0F, 1.5F, 1.0F);
        this.analfin.addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.dorsalfinfirst = new ModelRenderer(this, 0, 2);
        this.dorsalfinfirst.setPos(0.0F, -1.0F, -4.0F);
        this.dorsalfinfirst.addBox(0.0F, -2.5F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.eyes = new ModelRenderer(this, 10, 11);
        this.eyes.setPos(0.0F, -1.5F, -2.5F);
        this.eyes.addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.pelvicfins);
        this.body.addChild(this.head);
        this.body.addChild(this.dorsalfinsecond);
        this.head.addChild(this.pectoralfinleft);
        this.head.addChild(this.pectoralfinright);
        this.body.addChild(this.tailfin);
        this.body.addChild(this.analfin);
        this.body.addChild(this.dorsalfinfirst);
        this.head.addChild(this.eyes);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        if (entityIn.isInWater()) {
            this.body.yRot = MathHelper.cos(ageInTicks * speed * 0.6F) * degree * 0.3F * 0.3F;
            this.body.zRot = MathHelper.cos(limbSwing * speed * 0.8F) * degree * 0.25F * limbSwingAmount;
            this.head.yRot = MathHelper.cos(limbSwing * speed * 0.8F) * degree * 0.3F * limbSwingAmount;
            this.head.zRot = MathHelper.cos(limbSwing * speed * 0.8F) * degree * 0.25F * limbSwingAmount;
            this.tailfin.yRot = MathHelper.cos(ageInTicks * speed * 0.6F) * degree * 0.4F;
            this.pelvicfins.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 1.0F;
            this.pectoralfinright.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
            this.pectoralfinright.xRot = 0.0F;
            this.pectoralfinleft.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount;
            this.pectoralfinleft.xRot = 0.0F;
            this.dorsalfinfirst.xRot = limbSwingAmount - 0.5F;
        }
        else {
            this.body.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.body.zRot = 0.0F;
            this.pectoralfinright.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.25F;
            this.pectoralfinleft.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.25F;
            this.head.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
            this.head.zRot = 0.0F;
            this.tailfin.yRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
            this.pectoralfinright.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.25F;
            this.pectoralfinleft.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.25F;
            this.pelvicfins.xRot = limbSwingAmount + 1.2F;
            this.dorsalfinfirst.xRot = 0.0F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
