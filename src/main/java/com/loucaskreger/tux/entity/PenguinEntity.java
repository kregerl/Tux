package com.loucaskreger.tux.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class PenguinEntity extends PathAwareEntity {

	public PenguinEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

}
