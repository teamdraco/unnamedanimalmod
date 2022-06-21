package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.MangroveSnakeEntity;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeModel<T extends Entity> extends AgeableModel<MangroveSnakeEntity> {
    public ModelPart head;
    public ModelPart jaw;
    public ModelPart body1;
    public ModelPart tongue;
    public ModelPart body2;
    public ModelPart body3;
    public ModelPart tail;

    public MangroveSnakeModel() {
        this.texWidth = 48;
        this.texHeight = 32;
        this.body2 = new ModelPart(this, 24, 21);
        this.body2.setPos(0.0F, 0.0F, 9.0F);
        this.body2.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.tongue = new ModelPart(this, 8, 5);
        this.tongue.setPos(0.0F, 0.0F, -4.0F);
        this.tongue.addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body3 = new ModelPart(this, 24, 9);
        this.body3.setPos(0.0F, 0.0F, 9.0F);
        this.body3.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.body1 = new ModelPart(this, 0, 21);
        this.body1.setPos(0.0F, 1.0F, 0.0F);
        this.body1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelPart(this, 0, 15);
        this.jaw.setPos(0.0F, 1.0F, 0.0F);
        this.jaw.addBox(-2.0F, 0.0F, -5.0F, 4.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 7);
        this.head.setPos(0.0F, 22.0F, -9.0F);
        this.head.addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 13, 24);
        this.tail.setPos(0.0F, 0.5F, 9.0F);
        this.tail.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body1.addChild(this.body2);
        this.jaw.addChild(this.tongue);
        this.body2.addChild(this.body3);
        this.head.addChild(this.body1);
        this.head.addChild(this.jaw);
        this.body3.addChild(this.tail);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(head);
    }

    @Override
    public void setupAnim(MangroveSnakeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.5f;
        this.body1.yRot = Mth.cos(limbSwing * speed * 0.3F) * degree * -0.4F * limbSwingAmount;
        this.head.yRot = Mth.cos(2.0F + limbSwing * speed * 0.3F) * degree * 0.3F * limbSwingAmount;
        this.body2.yRot = Mth.cos(-1.0F + limbSwing * speed * 0.3F) * degree * 0.8F * limbSwingAmount;
        this.body3.yRot = Mth.cos(-1.0F + limbSwing * speed * 0.3F) * degree * -0.8F * limbSwingAmount;
        this.tail.yRot = Mth.cos(limbSwing * speed * 0.3F) * degree * 0.8F * limbSwingAmount;
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
