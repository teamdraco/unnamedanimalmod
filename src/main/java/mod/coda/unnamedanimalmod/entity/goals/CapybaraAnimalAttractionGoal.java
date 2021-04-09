package mod.coda.unnamedanimalmod.entity.goals;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public class CapybaraAnimalAttractionGoal extends Goal {
    private final MobEntity entity;

    public CapybaraAnimalAttractionGoal(MobEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return entity.ticksExisted % 60 == 0 && entity.getPassengers().isEmpty();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return entity.ticksExisted % 80 != 0;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        for (MobEntity mobEntity : entity.world.getEntitiesWithinAABB(MobEntity.class, entity.getBoundingBox().grow(5), e -> e != entity && e.getRidingEntity() == null)) {
            if (mobEntity.getWidth() <= 0.75f && mobEntity.getHeight() <= 0.75f) {
                mobEntity.getNavigator().tryMoveToEntityLiving(entity, mobEntity.getAIMoveSpeed() + 0.4);
            }
        }
    }
}
