package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.LeafySeadragonEntity;

@OnlyIn(Dist.CLIENT)
public class LeafySeadragonModel<T extends Entity> extends EntityModel<LeafySeadragonEntity> {
    public ModelPart body;
    public ModelPart tail;
    public ModelPart neck;
    public ModelPart leafRightBottom;
    public ModelPart leafLeftTop;
    public ModelPart leafLeftBottom;
    public ModelPart leafRightTop;
    public ModelPart leafTail;
    public ModelPart head;
    public ModelPart snout;
    public ModelPart leafHead;

    public LeafySeadragonModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.leafLeftBottom = new ModelPart(this, 0, 4);
        this.leafLeftBottom.mirror = true;
        this.leafLeftBottom.setPos(1.0F, 0.5F, -0.5F);
        this.leafLeftBottom.addBox(0.0F, 0.0F, -2.5F, 0.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leafLeftBottom, 0.0F, 0.0F, -0.2617993877991494F);
        this.leafHead = new ModelPart(this, 0, 17);
        this.leafHead.setPos(0.0F, -2.0F, -2.0F);
        this.leafHead.addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 23.5F, -1.0F);
        this.body.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 0.5235987755982988F, 0.0F, 0.0F);
        this.neck = new ModelPart(this, 34, 0);
        this.neck.setPos(0.0F, -3.0F, -3.2F);
        this.neck.addBox(-0.5F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, 0.4363323129985824F, 0.0F, 0.0F);
        this.leafTail = new ModelPart(this, 28, 8);
        this.leafTail.setPos(0.0F, 0.5F, -1.0F);
        this.leafTail.addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.leafRightBottom = new ModelPart(this, 0, 4);
        this.leafRightBottom.setPos(-1.0F, 0.5F, -0.5F);
        this.leafRightBottom.addBox(0.0F, 0.0F, -2.5F, 0.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leafRightBottom, 0.0F, 0.0F, 0.2617993877991494F);
        this.head = new ModelPart(this, 12, 0);
        this.head.setPos(0.0F, 0.0F, -0.3F);
        this.head.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.9738937092970305F, 0.0F, 0.0F);
        this.leafLeftTop = new ModelPart(this, 0, 21);
        this.leafLeftTop.setPos(1.0F, -1.5F, -0.5F);
        this.leafLeftTop.addBox(0.0F, -4.0F, -2.5F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leafLeftTop, 0.0F, 0.0F, 0.5235987755982988F);
        this.snout = new ModelPart(this, 44, 0);
        this.snout.setPos(0.0F, -0.5F, -2.0F);
        this.snout.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leafRightTop = new ModelPart(this, 0, 21);
        this.leafRightTop.setPos(-1.0F, -1.5F, -0.5F);
        this.leafRightTop.addBox(0.0F, -4.0F, -2.5F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leafRightTop, 0.0F, 0.0F, -0.5235987755982988F);
        this.tail = new ModelPart(this, 18, 0);
        this.tail.setPos(0.0F, -1.5F, 2.0F);
        this.tail.addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -1.0471975511965976F, 0.0F, 0.0F);
        this.body.addChild(this.leafLeftBottom);
        this.head.addChild(this.leafHead);
        this.body.addChild(this.neck);
        this.tail.addChild(this.leafTail);
        this.body.addChild(this.leafRightBottom);
        this.neck.addChild(this.head);
        this.body.addChild(this.leafLeftTop);
        this.head.addChild(this.snout);
        this.body.addChild(this.leafRightTop);
        this.body.addChild(this.tail);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(LeafySeadragonEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 2.8f;
        float degree = 1.6f;
        this.body.xRot = Mth.cos(f * speed * 0.4F) * degree * 0.2F * f1 + 0.5F;
        this.tail.xRot = Mth.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 1.0F;
        this.neck.xRot = Mth.cos(f * speed * 0.4F) * degree * 0.4F * f1 + 0.6F;
        this.body.yRot = Mth.cos(f * speed * 0.2F) * degree * 0.4F * f1;
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
