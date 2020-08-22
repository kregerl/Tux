package com.loucaskreger.tux.entities.render;

import com.loucaskreger.tux.Tux;
import com.loucaskreger.tux.entities.PenguinEntity;
import com.loucaskreger.tux.entities.model.PenguinEntityModel;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PenguinEntityRender extends MobRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(Tux.MODID,
			"textures/entity/penguin_entity.png");
	
	public PenguinEntityRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PenguinEntityModel<PenguinEntity>(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(PenguinEntity entity) {
		return TEXTURE;
	}

	

}
