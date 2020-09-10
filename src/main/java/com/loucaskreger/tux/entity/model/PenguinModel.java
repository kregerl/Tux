package com.loucaskreger.tux.entity.model;

import com.loucaskreger.tux.entity.PenguinEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PenguinModel extends EntityModel<PenguinEntity> {

	private final ModelPart penguin;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart beak;
	private final ModelPart foot;
	private final ModelPart foot2;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public PenguinModel() {
		super(RenderLayer::getEntityCutoutNoCull);

		textureWidth = 64;
		textureHeight = 64;

		penguin = new ModelPart(this);
		penguin.setPivot(0.0F, 24.0F, 0.0F);
		setRotationAngle(penguin, 0.0F, 3.1416F, 0.0F);

		body = new ModelPart(this);
		body.setPivot(0.0F, -5.0F, 0.0F);
		penguin.addChild(body);
		body.setTextureOffset(0, 0).addCuboid(-4.0F, -6.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F);

		head = new ModelPart(this);
		head.setPivot(0.5F, -10.5F, -0.5F);
		penguin.addChild(head);
		head.setTextureOffset(0, 18).addCuboid(-4.0F, -6.5F, -3.0F, 7.0F, 6.0F, 7.0F, 0.0F);

		beak = new ModelPart(this);
		beak.setPivot(-0.5F, -1.5F, 4.5F);
		head.addChild(beak);
		beak.setTextureOffset(0, 0).addCuboid(-1.5F, -1.5F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F);

		foot = new ModelPart(this);
		foot.setPivot(-2.5F, -0.925F, 2.075F);
		penguin.addChild(foot);
		setRotationAngle(foot, 0.0F, -0.4363F, 0.0F);
		foot.setTextureOffset(24, 4).addCuboid(-0.9167F, -0.075F, -0.1907F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		foot2 = new ModelPart(this);
		foot2.setPivot(2.7131F, -0.775F, 1.9238F);
		penguin.addChild(foot2);
		setRotationAngle(foot2, 0.0F, 0.4363F, 0.0F);
		foot2.setTextureOffset(24, 0).addCuboid(-1.2533F, -0.225F, -0.1286F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		leftWing = new ModelPart(this);
		leftWing.setPivot(-3.1139F, -9.1035F, 0.0F);
		penguin.addChild(leftWing);
		setRotationAngle(leftWing, 0.0F, 0.0F, 0.7854F);
		leftWing.setTextureOffset(28, 14).addCuboid(-1.5707F, -0.2854F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);

		rightWing = new ModelPart(this);
		rightWing.setPivot(3.8861F, -9.6035F, 0.0F);
		penguin.addChild(rightWing);
		setRotationAngle(rightWing, 0.0F, 0.0F, 2.3562F);
		rightWing.setTextureOffset(28, 14).addCuboid(-0.5454F, -6.3965F, -2.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.pivotX = x;
		modelRenderer.pivotY = y;
		modelRenderer.pivotZ = z;
	}

	@Override
	public void setAngles(PenguinEntity entity, float limbAngle, float limbDistance, float animationProgress,
			float headYaw, float headPitch) {
		this.head.pivotX = headPitch * -((float) Math.PI / 180F);
		this.head.pivotY = headYaw * ((float) Math.PI / 180F);
		this.leftWing.pivotZ = 0.261799F + (MathHelper.cos(limbAngle * 0.6662F) * 0.35F * limbDistance) * 2.0F;
		this.leftWing.pivotX = 0.174533F * limbDistance;
		this.rightWing.pivotZ = 2.879793F
				+ (MathHelper.cos(limbAngle * 0.6662F + ((float) Math.PI)) * 0.35F * limbDistance) * 2.0F;
		this.rightWing.pivotX = 0.174533F * limbDistance;
		this.foot2.pivotX = MathHelper.cos(limbAngle * 0.6662F) * 0.34F * limbDistance;
		this.foot.pivotX = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 0.34F * limbDistance;
		this.penguin.pivotZ = MathHelper.cos(limbAngle * 0.6662F) * 0.261799F * limbDistance * 1.5F;

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
			float blue, float alpha) {
		if (this.child) {
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0.0D, 1.5F, 2.0F / 16D);
			penguin.render(matrices, vertices, light, overlay, red, green, blue, alpha);
			matrices.pop();
		} else {
			penguin.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}

	}

}
