package com.loucaskreger.tux.init;

import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntitySpawns {
	private static List<Biome> biomesToSpawnIn = Arrays.asList(Biomes.FROZEN_RIVER, Biomes.SNOWY_BEACH,
			Biomes.SNOWY_TUNDRA);

	public static void SpawnPenguin() {
		ForgeRegistries.BIOMES.getValues().stream().filter(biome -> biomesToSpawnIn.contains(biome))
				.forEach(biome -> biome.getSpawns(EntityClassification.CREATURE)
						.add(new SpawnListEntry(ModEntities.PENGUIN_ENTITY.get(), 7, 3, 8)));
	}

}
