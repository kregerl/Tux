package com.loucaskreger.tux.init;

import com.loucaskreger.tux.Tux;

import com.loucaskreger.tux.entities.PenguinEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Tux.MODID);
	
	public static final RegistryObject<EntityType<PenguinEntity>> PENGUIN_ENTITY = ENTITIES.register("penguin_entity",
			() -> EntityType.Builder.<PenguinEntity>create(PenguinEntity::new, EntityClassification.CREATURE)
					.build(new ResourceLocation(Tux.MODID, "penguin_entity").toString()));

}
