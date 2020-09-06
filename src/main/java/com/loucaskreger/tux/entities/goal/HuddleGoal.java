package com.loucaskreger.tux.entities.goal;

import java.util.List;
import java.util.function.Predicate;
import com.loucaskreger.tux.entities.ModEntitySpawns;
import com.loucaskreger.tux.entities.PenguinEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

public class HuddleGoal extends Goal {

	private final PenguinEntity taskOwner;
	private int navigateTimer;
	private int field_222740_c;

	public HuddleGoal(PenguinEntity taskOwnerIn) {
		this.taskOwner = taskOwnerIn;
		this.field_222740_c = this.func_212825_a(taskOwnerIn);
	}

	protected int func_212825_a(PenguinEntity taskOwnerIn) {
		return 200 + taskOwnerIn.getRNG().nextInt(200) % 20;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		if (this.taskOwner.isGroupLeader() && isCorrectWeather()) {
			taskOwner.setMotionMultiplier(null, new Vec3d(0.25f, 0.25f, 0.25f));
//			taskOwner.addPotionEffect(new EffectInstance(Effects.GLOWING, 100));
			return false;
		} else if (this.taskOwner.hasGroupLeader() && isCorrectWeather()) {
			return true;
		} else if (this.field_222740_c > 0) {
			--this.field_222740_c;
			return false;
		} else {
			this.field_222740_c = this.func_212825_a(this.taskOwner);
			Predicate<PenguinEntity> predicate = (p_212824_0_) -> {
				return p_212824_0_.canGroupGrow() || !p_212824_0_.hasGroupLeader();
			};
			List<PenguinEntity> list = this.taskOwner.world.getEntitiesWithinAABB(this.taskOwner.getClass(),
					this.taskOwner.getBoundingBox().grow(8.0D, 8.0D, 8.0D), predicate);
			PenguinEntity abstractgroupfishentity = list.stream().filter(PenguinEntity::canGroupGrow).findAny()
					.orElse(this.taskOwner);
			abstractgroupfishentity.func_212810_a(list.stream().filter((p_212823_0_) -> {
				return !p_212823_0_.hasGroupLeader();
			}));
			return this.taskOwner.hasGroupLeader() && isCorrectWeather();
		}
	}

	private boolean isCorrectWeather() {
		BlockPos pos = taskOwner.getPosition();
		Biome biome = taskOwner.world.getBiome(pos);
		return this.taskOwner.world.isRaining() && this.taskOwner.world.canSeeSky(this.taskOwner.getPosition())
				&& ModEntitySpawns.biomesToSpawnIn.contains(biome);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return this.taskOwner.hasGroupLeader() && this.taskOwner.inRangeOfGroupLeader() && isCorrectWeather();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.navigateTimer = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		if (taskOwner.isGroupLeader())
			this.taskOwner.setMotionMultiplier(null, new Vec3d(1.0f, 1.0f, 1.0f));
		this.taskOwner.leaveGroup();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		if (--this.navigateTimer <= 0) {
			this.navigateTimer = 10;
			this.taskOwner.moveToGroupLeader();
		}
	}

}
