package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.SpottedGardenEelEntity;

@OnlyIn(Dist.CLIENT)
public class SpottedGardenEelHidingModel extends EntityModel<SpottedGardenEelEntity> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer head;

    public SpottedGardenEelHidingModel() {
        this.texWidth = 32;
        this.texHeight = 38;
        this.body = new ModelRenderer(this, 0, 6);
        this.body.setPos(0.0F, 25.0F, 1.0F);
        this.body.addBox(-1.0F, -1.0F, -12.0F, 2.0F, 2.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -1.5707963267948966F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 22);
        this.tail.setPos(0.0F, 0.0F, 2.0F);
        this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 8, 0);
        this.head.setPos(0.0F, 1.0F, -11.0F);
        this.head.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 1.5707963267948966F, 0.0F, 0.0F);
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
    public void setupAnim(SpottedGardenEelEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void prepareMobModel(SpottedGardenEelEntity entityIn, float f, float f1, float partialTick) {
        super.prepareMobModel(entityIn, f, f1, partialTick);
        if(entityIn.isInWater()) {
            float speed = 1.0f;
            float degree = 1.0f;
            this.body.zRot = MathHelper.cos(entityIn.tickCount * speed * 0.1F) * degree * 0.3F;
            this.tail.visible = false;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}