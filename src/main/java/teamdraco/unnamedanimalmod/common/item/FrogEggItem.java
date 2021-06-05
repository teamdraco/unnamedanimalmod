package teamdraco.unnamedanimalmod.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemUseContext;
import teamdraco.unnamedanimalmod.common.entity.TomatoFrogEntity;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FrogEggItem extends Item {
    private Supplier<EntityType<?>> entityTypeSupplier;

    public FrogEggItem(Supplier<EntityType<?>> entityType, Item.Properties builder) {
        super(builder);
        this.entityTypeSupplier = entityType;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getLevel();
        ItemStack itemstack = player.getItemInHand(hand);

        AnimalEntity frog = (AnimalEntity) entityTypeSupplier.get().create(world);
        if (!world.isClientSide && frog != null) {
            frog.setAge(-24000);
            frog.setPos(context.getClickedPos().getX() + 0.5F, context.getClickedPos().getY() + 1.5F, context.getClickedPos().getZ() + 0.5F);
            world.addFreshEntity(frog);
            world.playSound(player, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return super.useOn(context);
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.use(worldIn, playerIn, handIn);
    }
}