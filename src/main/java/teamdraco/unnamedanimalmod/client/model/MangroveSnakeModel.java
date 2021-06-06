package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.MangroveSnakeEntity;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeModel<T extends Entity> extends AgeableModel<MangroveSnakeEntity> {
    public ModelRenderer head;
    public ModelRenderer jaw;
    public ModelRenderer body1;
    public ModelRenderer tongue;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer tail;

    public MangroveSnakeModel() {
        this.texWidth = 48;
        this.texHeight = 32;
        this.body2 = new ModelRenderer(this, 24, 21);
        this.body2.setPos(0.0F, 0.0F, 9.0F);
        this.body2.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.tongue = new ModelRenderer(this, 8, 5);
        this.tongue.setPos(0.0F, 0.0F, -4.0F);
        this.tongue.addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body3 = new ModelRenderer(this, 24, 9);
        this.body3.setPos(0.0F, 0.0F, 9.0F);
        this.body3.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 21);
        this.body1.setPos(0.0F, 1.0F, 0.0F);
        this.body1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 0, 15);
        this.jaw.setPos(0.0F, 1.0F, 0.0F);
        this.jaw.addBox(-2.0F, 0.0F, -5.0F, 4.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 7);
        this.head.setPos(0.0F, 22.0F, -9.0F);
        this.head.addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 13, 24);
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
    protected Iterable<ModelRenderer> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(head);
    }

    @Override
    public void setupAnim(MangroveSnakeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.5f;
        this.body1.yRot = MathHelper.cos(limbSwing * speed * 0.3F) * degree * -0.4F * limbSwingAmount;
        this.head.yRot = MathHelper.cos(2.0F + limbSwing * speed * 0.3F) * degree * 0.3F * limbSwingAmount;
        this.body2.yRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.3F) * degree * 0.8F * limbSwingAmount;
        this.body3.yRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.3F) * degree * -0.8F * limbSwingAmount;
        this.tail.yRot = MathHelper.cos(limbSwing * speed * 0.3F) * degree * 0.8F * limbSwingAmount;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
