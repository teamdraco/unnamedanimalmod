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
public class SpottedGardenEelModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer head;

    public SpottedGardenEelModel() {
        this.texWidth = 32;
        this.texHeight = 38;
        this.tail = new ModelRenderer(this, 0, 22);
        this.tail.setPos(0.0F, 0.0F, 14.0F);
        this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 8, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 6);
        this.body.setPos(0.0F, 23.0F, -8.0F);
        this.body.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.tail);
        this.body.addChild(this.head);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 2.5f;
        float degree = 2.5f;
        this.body.yRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.3F * f1;
        this.head.yRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.3F * f1;
        this.tail.yRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.3F * f1;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}