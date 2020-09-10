package com.loucaskreger.tux.entity.renderer;

import com.loucaskreger.tux.Tux;
import com.loucaskreger.tux.entity.PenguinEntity;
import com.loucaskreger.tux.entity.model.PenguinModel;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PenguinRenderer extends MobEntityRenderer<PenguinEntity, PenguinModel>{

	public PenguinRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new PenguinModel(), 0.5f);
	}

	@Override
	public Identifier getTexture(PenguinEntity entity) {
		return new Identifier(Tux.MODID, "textures/entity/penguin/penguin_entity");
	}

}
