package teamdraco.unnamedanimalmod.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;

public class UAMModelLayers {
	public static ModelLayerLocation CAPYBARA = createLocation("capybara");

	private static ModelLayerLocation createLocation(String identifier) {
		return new ModelLayerLocation(new ResourceLocation(UnnamedAnimalMod.MOD_ID, identifier), "main");
	}
}
