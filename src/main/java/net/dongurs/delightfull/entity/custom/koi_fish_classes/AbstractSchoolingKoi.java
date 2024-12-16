//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.dongurs.delightfull.entity.custom.koi_fish_classes;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractSchoolingKoi extends AbstractKoiFish {
    @Nullable
    private AbstractSchoolingKoi leader;
    private int schoolSize = 1;

    public AbstractSchoolingKoi(EntityType<? extends AbstractSchoolingKoi> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowKoiLeaderGoal(this));
    }

    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    protected boolean canRandomSwim() {
        return !this.isFollower();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public AbstractSchoolingKoi startFollowing(AbstractSchoolingKoi leader) {
        this.leader = leader;
        leader.addFollower();
        return leader;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.schoolSize;
    }

    private void removeFollower() {
        --this.schoolSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends AbstractKoiFish> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }

    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0);
        }

    }

    public void addFollowers(Stream<? extends AbstractSchoolingKoi> followers) {
        followers.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter((p_27538_) -> {
            return p_27538_ != this;
        }).forEach((p_27536_) -> {
            p_27536_.startFollowing(this);
        });
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        super.finalizeSpawn(level, difficulty, spawnType, (SpawnGroupData)spawnGroupData);
        if (spawnGroupData == null) {
            spawnGroupData = new SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((SchoolSpawnGroupData)spawnGroupData).leader);
        }

        return (SpawnGroupData)spawnGroupData;
    }



    public static class SchoolSpawnGroupData implements SpawnGroupData {
        public final AbstractSchoolingKoi leader;

        public SchoolSpawnGroupData(AbstractSchoolingKoi leader) {
            this.leader = leader;
        }
    }
}
