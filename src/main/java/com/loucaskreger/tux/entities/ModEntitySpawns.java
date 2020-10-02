package com.loucaskreger.tux.entities;

import java.util.Arrays;
import java.util.List;

import com.loucaskreger.tux.init.ModEntities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntitySpawns {
	public static List<Biome> biomesToSpawnIn = Arrays.asList(Biomes.FROZEN_RIVER, Biomes.SNOWY_BEACH,
			Biomes.SNOWY_TUNDRA, Biomes.ICE_SPIKES, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_OCEAN);

	public static void SpawnPenguin() {
		ForgeRegistries.BIOMES.getValues().stream().filter(biome -> biome.getTempCategory() == Biome.TempCategory.COLD)
				.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE)
						.add(new SpawnListEntry(ModEntities.PENGUIN_ENTITY.get(), 7, 4, 10)));
	}

}
