package com.loucaskreger.tux;

import com.loucaskreger.tux.entity.renderer.PenguinRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class TuxClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(Tux.PENGUIN, (dispatcher, context) -> {
			return new PenguinRenderer(dispatcher);
		});

	}

}
