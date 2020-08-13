package mod.coda.unnamedanimalmod.util;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.entity.MuskOxEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {

    @SubscribeEvent
    public static void lootAdd(LootTableLoadEvent event) {
        if (!event.getName().toString().equals("minecraft:chests/desert_pyramid")) {
            return;
        }
        String lootLocation = event.getName().toString().replace("minecraft:chests/", "");

        event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(Main.MOD_ID, "inject/desert_pyramid")).weight(1)).bonusRolls(0, 0).name("uam_inject").build());
    }

    @SubscribeEvent
    public static void spawnEntity(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof WolfEntity) {
            ((WolfEntity) entity).targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((MobEntity) entity, MuskOxEntity.class, true));
        }
    }
}
