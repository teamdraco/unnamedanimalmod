package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.FireSalamanderEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class  FireSalamanderEntityModel<T extends Entity> extends AgeableModel<FireSalamanderEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer tail;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;

    public FireSalamanderEntityModel() {
        this.textureWidth = 23;
        this.textureHeight = 16;
        this.legRight = new ModelRenderer(this, 0, 4);
        this.legRight.setRotationPoint(-1.5F, 0.5F, 2.5F);
        this.legRight.addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.head.addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 9);
        this.tail.setRotationPoint(0.0F, 0.5F, 3.0F);
        this.tail.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 4);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.5F, 0.5F, -1.5F);
        this.armLeft.addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 4);
        this.armRight.setRotationPoint(-1.5F, 0.5F, -1.5F);
        this.armRight.addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 4);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.5F, 0.5F, 2.5F);
        this.legLeft.addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 5, 0);
        this.body.setRotationPoint(0.0F, 23.0F, -2.0F);
        this.body.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.legRight);
        this.body.addChild(this.head);
        this.body.addChild(this.tail);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.armRight);
        this.body.addChild(this.legLeft);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setRotationAngles(FireSalamanderEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.0f;
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
//        this.body.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount;
        this.legLeft.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.2F;
        this.legRight.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.2F;
        this.armLeft.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.2F;
        this.armRight.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.2F;
        this.head.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
    }
}
