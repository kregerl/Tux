package com.loucaskreger.tux.entities;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.loucaskreger.tux.block.PenguinEggBlock;
import com.loucaskreger.tux.entities.goal.HuddleGoal;
import com.loucaskreger.tux.init.ModBlocks;
import com.loucaskreger.tux.init.ModEntities;
import com.loucaskreger.tux.init.ModItems;
import com.loucaskreger.tux.init.ModSoundEvents;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PenguinEntity extends AnimalEntity {

	private static final Item BREEDING_ITEM = Items.COD;
	private PenguinEntity groupLeader;
	private int groupSize = 1;

	private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(PenguinEntity.class,
			DataSerializers.BOOLEAN);

	public PenguinEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	// public AgeableEntity createChild(AgeableEntity ageable)
	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity ageable) {
		PenguinEntity entity = new PenguinEntity(ModEntities.PENGUIN_ENTITY.get(), this.world);
		entity.onInitialSpawn(world, this.world.getDifficultyForLocation(new BlockPos(entity.getPosition())),
				SpawnReason.SPAWN_EGG, (ILivingEntityData) null, (CompoundNBT) null);
		return entity;
	}

//	@Override
//	public AgeableEntity createChild(AgeableEntity ageable) {
//		PenguinEntity entity = new PenguinEntity(ModEntities.PENGUIN_ENTITY.get(), this.world);
//		entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity.getPosition())),
//				SpawnReason.SPAWN_EGG, (ILivingEntityData) null, (CompoundNBT) null);
//		return entity;
//	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(1, new PenguinEntity.MateGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new PenguinEntity.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new HuddleGoal(this));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(BREEDING_ITEM), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(HAS_EGG, false);
	}

	public static MutableAttribute getAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
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
		if (stack.getItem() == BREEDING_ITEM) {
			return true;
		}
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.PENGUIN_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.PENGUIN_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.PENGUIN_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ModItems.PENGUIN_SPAWN_EGG.get());

	}

	public boolean isGroupLeader() {
		return this.groupSize > 1;
	}

	public boolean hasGroupLeader() {
		return this.groupLeader != null && this.groupLeader.isAlive();
	}

	public boolean canGroupGrow() {
		return this.isGroupLeader() && this.groupSize < this.getMaxGroupSize();
	}

	public int getMaxGroupSize() {
		return 10;
	}

	public PenguinEntity func_212803_a(PenguinEntity groupLeaderIn) {
		this.groupLeader = groupLeaderIn;
		groupLeaderIn.increaseGroupSize();
		return groupLeaderIn;
	}

	public void func_212810_a(Stream<PenguinEntity> p_212810_1_) {
		p_212810_1_.limit((long) (this.getMaxGroupSize() - this.groupSize)).filter((p_212801_1_) -> {
			return p_212801_1_ != this;
		}).forEach((p_212804_1_) -> {
			p_212804_1_.func_212803_a(this);
		});
	}

	private void increaseGroupSize() {
		++this.groupSize;
	}

	private void decreaseGroupSize() {
		--this.groupSize;
	}

	public boolean inRangeOfGroupLeader() {
		return this.getDistanceSq(this.groupLeader) <= 121.0D;
	}

	public PenguinEntity getGroupLeader() {
		return this.groupLeader;
	}

	public void moveToGroupLeader() {
		if (this.hasGroupLeader()) {
			this.getNavigator().tryMoveToEntityLiving(this.groupLeader, 1.0D);
		}

	}

	@Override
	public void tick() {
		super.tick();
		if (this.isGroupLeader() && this.world.rand.nextInt(200) == 1) {
			List<PenguinEntity> list = this.world.getEntitiesWithinAABB(this.getClass(),
					this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
			if (list.size() <= 1) {
				this.groupSize = 1;
			}
		}
	}

	public void leaveGroup() {
		this.groupLeader.decreaseGroupSize();
		this.groupLeader = null;
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		// Not sure why but 1.16 only works with null when 1.15 works with both??
		super.onInitialSpawn(worldIn, difficultyIn, reason, null/* spawnDataIn */, dataTag);
		if (spawnDataIn == null) {
			spawnDataIn = new PenguinEntity.GroupData(this);
		} else {
			this.func_212803_a(((PenguinEntity.GroupData) spawnDataIn).groupLeader);
		}

		return spawnDataIn;
	}

	public static class GroupData implements ILivingEntityData {
		public final PenguinEntity groupLeader;

		public GroupData(PenguinEntity groupLeaderIn) {
			this.groupLeader = groupLeaderIn;
		}
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
//			this.penguin.setInLove(600);
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
			BlockPos blockpos = new BlockPos(this.penguin.getPosition());
			World world = this.penguin.world;
			world.playSound((PlayerEntity) null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS,
					0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
			world.setBlockState(this.destinationBlock.up(), ModBlocks.PENGUIN_EGG.get().getDefaultState()
					.with(PenguinEggBlock.EGGS, Integer.valueOf(this.penguin.rand.nextInt(4) + 1)), 3);
			this.penguin.setHasEgg(false);
//			this.penguin.setInLove(600);
			this.penguin.resetInLove();

		}

		@Override
		protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			return (!worldIn.isAirBlock(pos) && worldIn.getBlockState(pos).getBlock() != ModBlocks.PENGUIN_EGG.get());
		}

	}
}
