package com.loucaskreger.tux.entities.model;



import com.google.common.collect.ImmutableList;
import com.loucaskreger.tux.entities.PenguinEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class PenguinEntityModel<T extends PenguinEntity> extends EntityModel<T> {
	private final ModelRenderer penguin;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer beak;
	private final ModelRenderer foot;
	private final ModelRenderer foot2;
	private final ModelRenderer leftWing;
	private final ModelRenderer rightWing;

	public PenguinEntityModel() {
		super(RenderType::getEntityCutoutNoCull);

		penguin = new ModelRenderer(this);
		penguin.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(penguin, 0.0F, 3.1416F, 0.0F);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -5.0F, 0.0F);
		penguin.addChild(body);
		body.setTextureOffset(0, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.5F, -10.5F, -0.5F);
		penguin.addChild(head);
		head.setTextureOffset(0, 18).addBox(-4.0F, -6.5F, -3.0F, 7.0F, 6.0F, 7.0F, 0.0F);

		beak = new ModelRenderer(this);
		beak.setRotationPoint(-0.5F, -1.5F, 4.5F);
		head.addChild(beak);
		beak.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F);

		foot = new ModelRenderer(this);
		foot.setRotationPoint(-2.5F, -0.925F, 2.075F);
		penguin.addChild(foot);
		setRotationAngle(foot, 0.0F, -0.4363F, 0.0F);
		foot.setTextureOffset(24, 4).addBox(-0.9167F, -0.075F, -0.1907F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		foot2 = new ModelRenderer(this);
		foot2.setRotationPoint(2.7131F, -0.775F, 1.9238F);
		penguin.addChild(foot2);
		setRotationAngle(foot2, 0.0F, 0.4363F, 0.0F);
		foot2.setTextureOffset(24, 0).addBox(-1.2533F, -0.225F, -0.1286F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		leftWing = new ModelRenderer(this);
		leftWing.setRotationPoint(-3.1139F, -9.1035F, 0.0F);
		penguin.addChild(leftWing);
		setRotationAngle(leftWing, 0.0F, 0.0F, 0.7854F);
		leftWing.setTextureOffset(28, 14).addBox(-1.5707F, -0.2854F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);

		rightWing = new ModelRenderer(this);
		rightWing.setRotationPoint(3.8861F, -9.6035F, 0.0F);
		penguin.addChild(rightWing);
		setRotationAngle(rightWing, 0.0F, 0.0F, 2.3562F);
		rightWing.setTextureOffset(28, 14).addBox(-0.5454F, -6.3965F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		/*
		 * Example: this.penguin.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) *
		 * 0.34F * limbSwingAmount; 0.34F is the angle in radians.
		 */
		this.head.rotateAngleX = headPitch * -((float) Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		this.leftWing.rotateAngleZ = 0.261799F + (MathHelper.cos(limbSwing * 0.6662F) * 0.35F * limbSwingAmount) * 2.0F;
		this.leftWing.rotateAngleX = 0.174533F * limbSwingAmount;
		this.rightWing.rotateAngleZ = 2.879793F + (MathHelper.cos(limbSwing * 0.6662F + ((float) Math.PI)) * 0.35F * limbSwingAmount) * 2.0F;
		this.rightWing.rotateAngleX = 0.174533F * limbSwingAmount;
		this.foot2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.34F * limbSwingAmount;
		this.foot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.34F * limbSwingAmount;
		this.penguin.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.261799F * limbSwingAmount * 1.5F;

	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		 if (this.isChild) {
			matrixStackIn.push();
	        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
	        matrixStackIn.translate(0.0D, 1.5F, 2.0F/16D);
	        penguin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        matrixStackIn.pop();
	      } else {
	        penguin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	      }
	}

	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head, this.beak);
	}

	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.foot, this.foot2, this.leftWing, this.rightWing);
	}

}
