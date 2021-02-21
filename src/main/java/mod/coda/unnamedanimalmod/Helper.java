package mod.coda.unnamedanimalmod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mod.coda.unnamedanimalmod.UnnamedAnimalMod.MOD_ID;

public class Helper
{
    public static boolean areWeOnClient(World world)
    {
        return world.isRemote;
    }
    
    public static boolean areWeOnServer(World world)
    {
        return !areWeOnClient(world);
    }
    
    public static ResourceLocation prefix(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }
    
    public static <T> ArrayList<T> toArrayList(T... items)
    {
        return new ArrayList<>(Arrays.asList(items));
    }
    
    public static <T> ArrayList<T> toArrayList(Stream<T> items)
    {
        return items.collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static String toTitleCase(String givenString, String regex)
    {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray)
        {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }
    
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items)
    {
        List<T> ret = Arrays.asList(items);
        for (T item : items)
        {
            if (!src.contains(item))
            {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret))
        {
            return Collections.emptyList();
        }
        return ret;
    }
    
    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred)
    {
        List<T> ret = new ArrayList<>();
        
        Iterator<T> iter = src.iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (pred.test(item))
            {
                iter.remove();
                ret.add(item);
            }
        }
        
        if (ret.isEmpty())
        {
            return Collections.emptyList();
        }
        return ret;
    }
}