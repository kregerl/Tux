package com.loucaskreger.tux.entities;

import java.util.Set;
import com.loucaskreger.tux.Tux;
import com.loucaskreger.tux.init.ModEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Tux.MODID)
public class ModEntitySpawns {

	@SubscribeEvent
	public static void spawnPenguin(BiomeLoadingEvent event) {
		ResourceLocation name = event.getName();
		if (name != null) {
			Biome biomeReg = ForgeRegistries.BIOMES.getValue(name);
			if (biomeReg != null) {
				if (isCorrectBiome(name)) {
					event.getSpawns().getSpawner(EntityClassification.CREATURE)
							.add(new MobSpawnInfo.Spawners(ModEntities.PENGUIN_ENTITY.get(), 7, 4, 10));
				}
			}
		}
	}

	public static boolean isCorrectBiome(ResourceLocation loc) {
		RegistryKey<Biome> key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, loc);
		Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(key);
		if (biomeTypes.stream().anyMatch(b -> b == BiomeDictionary.Type.SNOWY)) {
			return true;
		}
		return false;
	}

}
