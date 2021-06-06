package teamdraco.unnamedanimalmod.common;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.WoodType;

import java.util.Set;
import java.util.stream.Stream;

public class UAMWoodType extends WoodType {
    private static final Set<WoodType> VALUES = new ObjectArraySet<>();
    public static final WoodType MANGROVE = register(new UAMWoodType("mangrove"));
    private final String name;

    protected UAMWoodType(String p_i225775_1_) {
        super(p_i225775_1_);
        this.name = p_i225775_1_;
    }

    public static UAMWoodType register(UAMWoodType p_227047_0_) {
        UAMWoodType.VALUES.add(p_227047_0_);
        return p_227047_0_;
    }

    public static Stream<WoodType> values() {
        return UAMWoodType.VALUES.stream();
    }

    public String name() {
        return this.name;
    }


    public static UAMWoodType create(String name) {
        return new UAMWoodType(name);
    }
}
