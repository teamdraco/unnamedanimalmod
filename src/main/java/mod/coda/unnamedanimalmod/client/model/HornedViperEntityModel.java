package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.HornedViperEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class HornedViperEntityModel<T extends Entity> extends AgeableModel<HornedViperEntity> {
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer tail1;
    public ModelRenderer head;
    public ModelRenderer horns;
    public ModelRenderer tail2;

    public HornedViperEntityModel() {
        this.textureWidth = 20;
        this.textureHeight = 16;
        this.neck = new ModelRenderer(this, 0, 0);
        this.neck.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.neck.addBox(-1.5F, -1.0F, -6.0F, 3.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 8);
        this.head.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.head.addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.horns = new ModelRenderer(this, 0, 0);
        this.horns.setRotationPoint(0.0F, -1.0F, -2.0F);
        this.horns.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horns, 0.2617993877991494F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 23.0F, -2.0F);
        this.body.addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.tail1 = new ModelRenderer(this, 0, 0);
        this.tail1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.tail1.addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 0, 0);
        this.tail2.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.tail2.addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.neck);
        this.neck.addChild(this.head);
        this.head.addChild(this.horns);
        this.body.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
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
    public void setRotationAngles(HornedViperEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.0f;
        this.body.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1;
        this.neck.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1;
        this.head.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.8F * f1;
        this.tail1.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1;
        this.tail2.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
