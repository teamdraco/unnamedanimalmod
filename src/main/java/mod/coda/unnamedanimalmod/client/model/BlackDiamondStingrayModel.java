package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.BlackDiamondStingrayEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class BlackDiamondStingrayModel<T extends Entity> extends SegmentedModel<BlackDiamondStingrayEntity> {
    public ModelRenderer body;
    public ModelRenderer finRight;
    public ModelRenderer finLeft;
    public ModelRenderer bodyBottom;
    public ModelRenderer tail;

    public BlackDiamondStingrayModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.tail = new ModelRenderer(this, 0, 12);
        this.tail.setRotationPoint(0.0F, -0.5F, 7.0F);
        this.tail.addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.finRight = new ModelRenderer(this, 27, 12);
        this.finRight.mirror = true;
        this.finRight.setRotationPoint(3.0F, 0.0F, 1.0F);
        this.finRight.addBox(0.0F, -1.0F, -5.0F, 3.0F, 1.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.bodyBottom = new ModelRenderer(this, 19, 0);
        this.bodyBottom.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.bodyBottom.addBox(-3.0F, -1.0F, -2.0F, 6.0F, 1.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 23.0F, -2.0F);
        this.body.addBox(-3.0F, -2.0F, -2.0F, 6.0F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.finLeft = new ModelRenderer(this, 27, 12);
        this.finLeft.setRotationPoint(-3.0F, 0.0F, 1.0F);
        this.finLeft.addBox(-3.0F, -1.0F, -5.0F, 3.0F, 1.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.tail);
        this.body.addChild(this.finRight);
        this.body.addChild(this.bodyBottom);
        this.body.addChild(this.finLeft);
    }

    @Override
    public void setRotationAngles(BlackDiamondStingrayEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 5.0f;
        float degree = 2.0f;

        this.finLeft.rotateAngleZ = MathHelper.cos(f * speed * 0.2F) * degree * 0.4F * f1 + 0.1F;
        this.finRight.rotateAngleZ = MathHelper.cos(f * speed * 0.2F) * degree * -0.4F * f1 - 0.1F;
        this.tail.rotateAngleY = MathHelper.cos(f * speed * 0.3F) * degree * 0.2F * f1;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }
}
