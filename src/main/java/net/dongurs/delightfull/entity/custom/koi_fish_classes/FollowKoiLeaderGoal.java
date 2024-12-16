//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.dongurs.delightfull.entity.custom.koi_fish_classes;

import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.entity.ai.goal.Goal;

public class FollowKoiLeaderGoal extends Goal {
    private static final int INTERVAL_TICKS = 200;
    private final AbstractSchoolingKoi mob;
    private int timeToRecalcPath;
    private int nextStartTick;

    public FollowKoiLeaderGoal(AbstractSchoolingKoi fish) {
        this.mob = fish;
        this.nextStartTick = this.nextStartTick(fish);
    }

    protected int nextStartTick(AbstractSchoolingKoi taskOwner) {
        return reducedTickDelay(200 + taskOwner.getRandom().nextInt(200) % 20);
    }

    public boolean canUse() {
        if (this.mob.hasFollowers()) {
            return false;
        } else if (this.mob.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<AbstractSchoolingKoi> predicate = (p_25258_) -> {
                return p_25258_.canBeFollowed() || !p_25258_.isFollower();
            };
            List<? extends AbstractSchoolingKoi> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate);
            AbstractSchoolingKoi abstractschoolingfish = (AbstractSchoolingKoi) DataFixUtils.orElse(list.stream().filter(AbstractSchoolingKoi::canBeFollowed).findAny(), this.mob);
            abstractschoolingfish.addFollowers(list.stream().filter((p_25255_) -> {
                return !p_25255_.isFollower();
            }));
            return this.mob.isFollower();
        }
    }

    public boolean canContinueToUse() {
        return this.mob.isFollower() && this.mob.inRangeOfLeader();
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.mob.stopFollowing();
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.mob.pathToLeader();
        }

    }
}
