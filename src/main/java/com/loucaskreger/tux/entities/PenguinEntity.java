package com.loucaskreger.tux.entities;

import java.util.Random;
import com.loucaskreger.tux.block.PenguinEggBlock;
import com.loucaskreger.tux.init.ModBlocks;
import com.loucaskreger.tux.init.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PenguinEntity extends AnimalEntity {
	private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(PenguinEntity.class,
			DataSerializers.BOOLEAN);

	public PenguinEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		PenguinEntity entity = new PenguinEntity(ModEntities.PENGUIN_ENTITY.get(), this.world);
		entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)),
				SpawnReason.SPAWN_EGG, (ILivingEntityData) null, (CompoundNBT) null);
		return entity;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PenguinEntity.MateGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new PenguinEntity.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new TemptGoal(this, 1.1D, Ingredient.fromItems(Items.COD), false));
		this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(HAS_EGG, false);
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
	}

	public boolean hasEgg() {
		return this.dataManager.get(HAS_EGG);
	}

	private void setHasEgg(boolean hasEgg) {
		this.dataManager.set(HAS_EGG, hasEgg);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("HasEgg", this.hasEgg());

	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.COD;
	}

	/**
	 * Use this to make penguin stay within 25 blocks of egg.
	 * 
	 * @Override public void livingTick() {
	 * 
	 *           }
	 **/

	static class MateGoal extends BreedGoal {
		private final PenguinEntity penguin;

		MateGoal(PenguinEntity penguin, double speedIn) {
			super(penguin, speedIn);
			this.penguin = penguin;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		@Override
		public boolean shouldExecute() {
			return super.shouldExecute() && !this.penguin.hasEgg();
		}

		/**
		 * Spawns a baby animal of the same type.
		 */
		@Override
		protected void spawnBaby() {
			ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
			if (serverplayerentity == null && this.targetMate.getLoveCause() != null) {
				serverplayerentity = this.targetMate.getLoveCause();
			}

			if (serverplayerentity != null) {
				serverplayerentity.addStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.targetMate,
						(AgeableEntity) null);
			}

			this.penguin.setHasEgg(true);
			this.animal.resetInLove();
			this.targetMate.resetInLove();
			Random random = this.animal.getRNG();
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
				this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(),
						this.animal.getPosZ(), random.nextInt(7) + 1));
			}

		}
	}

	static class LayEggGoal extends MoveToBlockGoal {
		private final PenguinEntity penguin;

		LayEggGoal(PenguinEntity penguin, double speedIn) {
			super(penguin, speedIn, 16);
			this.penguin = penguin;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		@Override
		public boolean shouldExecute() {
			return this.penguin.hasEgg() ? super.shouldExecute() : false;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && this.penguin.hasEgg();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			super.tick();
			BlockPos blockpos = new BlockPos(this.penguin);
			World world = this.penguin.world;
			world.playSound((PlayerEntity) null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS,
					0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
			world.setBlockState(this.destinationBlock.up(), ModBlocks.PENGUIN_EGG.get().getDefaultState()
					.with(PenguinEggBlock.EGGS, Integer.valueOf(this.penguin.rand.nextInt(4) + 1)), 3);
			this.penguin.setHasEgg(false);
			this.penguin.setInLove(600);

		}

		@Override
		protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			if (!worldIn.isAirBlock(pos.up())) {
				return false;
			} else {
//				Block block = worldIn.getBlockState(pos).getBlock();
				return true;
			}
		}

	}
}
