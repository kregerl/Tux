package com.loucaskreger.tux;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.loucaskreger.tux.entities.ModEntitySpawns;
import com.loucaskreger.tux.entities.render.PenguinEntityRender;
import com.loucaskreger.tux.init.ModBlocks;
import com.loucaskreger.tux.init.ModEntities;
import com.loucaskreger.tux.init.ModItems;
import com.loucaskreger.tux.init.ModSoundEvents;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("deprecation")
@Mod(Tux.MODID)
public class Tux {
	public static final String MODID = "tux";
	public static final Logger LOGGER = LogManager.getLogger();
	
    public static final ItemGroup TUX_TAB = new ItemGroup("tuxTab") {
    	@Override
    	public ItemStack createIcon() {
    		return new ItemStack(ModItems.PENGUIN_EGG.get());
    	}
    };
	

	public Tux() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModBlocks.BLOCKS.register(bus);
		ModItems.ITEMS.register(bus);
		ModSoundEvents.SOUND_EVENTS.register(bus);
		ModEntities.ENTITIES.register(bus);
		bus.addListener(this::setupClient);
		bus.addListener(this::setupCommon);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		//Deprecated
		DeferredWorkQueue.runLater(new Runnable() {

			@Override
			public void run() {
				ModEntitySpawns.SpawnPenguin();
			}

		});

	}

	private void setupClient(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.PENGUIN_ENTITY.get(), PenguinEntityRender::new);
	}

}
