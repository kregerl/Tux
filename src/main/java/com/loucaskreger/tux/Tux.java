package com.loucaskreger.tux;

import com.loucaskreger.tux.entity.PenguinEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Tux implements ModInitializer {
	public static final String MODID = "tux";

	public static final EntityType<PenguinEntity> PENGUIN = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(MODID, "penguin"),
			FabricEntityTypeBuilder.<PenguinEntity>create(SpawnGroup.CREATURE, PenguinEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.createMobAttributes());
	}
}
