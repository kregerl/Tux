package com.loucaskreger.tux.init;

import com.loucaskreger.tux.Tux;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tux.MODID);
	private static final ResourceLocation RL_PENGUIN_AMBIENT = new ResourceLocation(Tux.MODID, "penguin_ambient");
	private static final ResourceLocation RL_PENGUIN_HURT = new ResourceLocation(Tux.MODID, "penguin_hurt");
	private static final ResourceLocation RL_PENGUIN_DEATH = new ResourceLocation(Tux.MODID, "penguin_death");
	private static final ResourceLocation RL_PENGUIN_STEP = new ResourceLocation(Tux.MODID, "penguin_step");
	
	public static final RegistryObject<SoundEvent> PENGUIN_AMBIENT = SOUND_EVENTS.register("penguin_ambient", () -> new SoundEvent(RL_PENGUIN_AMBIENT));
	public static final RegistryObject<SoundEvent> PENGUIN_HURT = SOUND_EVENTS.register("penguin_hurt", () -> new SoundEvent(RL_PENGUIN_HURT));
	public static final RegistryObject<SoundEvent> PENGUIN_DEATH = SOUND_EVENTS.register("penguin_death", () -> new SoundEvent(RL_PENGUIN_DEATH));
	public static final RegistryObject<SoundEvent> PENGUIN_STEP = SOUND_EVENTS.register("penguin_step", () -> new SoundEvent(RL_PENGUIN_STEP));

}
