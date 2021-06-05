package teamdraco.unnamedanimalmod.common.entity.util.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ZCapybaraAnimalAttractionGoal extends Goal {
    private final MobEntity entity;

    public CapybaraAnimalAttractionGoal(MobEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return entity.tickCount % 60 == 0 && entity.getPassengers().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.tickCount % 80 != 0;
    }

    @Override
    public void start() {
        super.start();
        for (MobEntity mobEntity : entity.level.getEntitiesOfClass(MobEntity.class, entity.getBoundingBox().inflate(5), e -> e != entity && e.getVehicle() == null)) {
            if (mobEntity.getBbWidth() <= 0.75f && mobEntity.getBbHeight() <= 0.75f) {
                mobEntity.getNavigation().moveTo(entity, mobEntity.getSpeed() + 0.4);
            }
        }
    }
}