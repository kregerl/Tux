package com.loucaskreger.tux.init;

import com.loucaskreger.tux.Tux;
import com.loucaskreger.tux.block.PenguinEggBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tux.MODID);

	public static final RegistryObject<Block> PENGUIN_EGG = BLOCKS.register("penguin_egg",
			() -> new PenguinEggBlock(Block.Properties.create(Material.DRAGON_EGG, MaterialColor.SAND)
					.hardnessAndResistance(0.5F).sound(SoundType.METAL).tickRandomly().notSolid()));

}
