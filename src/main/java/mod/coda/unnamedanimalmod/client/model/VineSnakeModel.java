package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.VineSnakeEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class VineSnakeModel<T extends Entity> extends AgeableModel<VineSnakeEntity> {
    public ModelRenderer head;
    public ModelRenderer body1;
    public ModelRenderer jaw;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;

    public VineSnakeModel() {
        this.textureWidth = 20;
        this.textureHeight = 15;
        this.head = new ModelRenderer(this, 6, 0);
        this.head.setRotationPoint(0.0F, 23.0F, -16.0F);
        this.head.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 5);
        this.body4.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.body4.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 5);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 5);
        this.body3.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.body3.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 5);
        this.body5.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.body5.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 0, 0);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 5);
        this.body2.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.body2.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body3.addChild(this.body4);
        this.head.addChild(this.body1);
        this.body2.addChild(this.body3);
        this.body4.addChild(this.body5);
        this.head.addChild(this.jaw);
        this.body1.addChild(this.body2);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(head);
    }

    @Override
    public void setRotationAngles(VineSnakeEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.5f;
        this.head.rotateAngleY = MathHelper.cos(2.0F + f * speed * 0.3F) * degree * 0.3F * f1;
        this.body1.rotateAngleY = MathHelper.cos(f * speed * 0.3F) * degree * 0.8F * f1;
        this.body2.rotateAngleY = MathHelper.cos(f * speed * 0.3F) * degree * -0.8F * f1;
        this.body3.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.8F * f1;
        this.body4.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1;
        this.body5.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.8F * f1;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}