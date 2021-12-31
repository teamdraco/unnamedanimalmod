package teamdraco.unnamedanimalmod.common.entity.util.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

public class CapybaraAnimalAttractionGoal extends Goal {
    private final Animal entity;

    public CapybaraAnimalAttractionGoal(Animal entity) {
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
        for (Animal mobEntity : entity.level.getEntitiesOfClass(Animal.class, entity.getBoundingBox().inflate(5), e -> e != entity && e.getVehicle() == null)) {
            if (mobEntity.getBbWidth() <= 0.75f && mobEntity.getBbHeight() <= 0.75f) {
                mobEntity.getNavigation().moveTo(entity, mobEntity.getSpeed() + 0.4);
            }
        }
    }
}
