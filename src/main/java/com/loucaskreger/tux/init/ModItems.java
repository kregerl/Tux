package com.loucaskreger.tux.init;

import com.loucaskreger.tux.Tux;
import com.loucaskreger.tux.item.PenguinSpawnEggItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tux.MODID);

	public static final RegistryObject<Item> PENGUIN_EGG = ITEMS.register("penguin_egg", () -> new BlockItem(ModBlocks.PENGUIN_EGG.get(), new Item.Properties().group(Tux.TUX_TAB)));
	
	public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new PenguinSpawnEggItem(ModEntities.PENGUIN_ENTITY, 1447446, 15198183));

}
