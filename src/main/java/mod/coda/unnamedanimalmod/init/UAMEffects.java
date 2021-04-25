package mod.coda.unnamedanimalmod.init;


import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.effects.FruitRageEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMEffects
{
    public static final DeferredRegister<Effect> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, UnnamedAnimalMod.MOD_ID);
    public static final RegistryObject<Effect> FRUIT_RAGE = REGISTRY.register("fruit_rate", FruitRageEffect::new);

}
