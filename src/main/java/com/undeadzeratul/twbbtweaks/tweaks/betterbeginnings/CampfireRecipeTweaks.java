package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import java.util.Map.Entry;

import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.FurnaceRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CampfireRecipeTweaks extends AbstractBBTweaks
{
    private static final String ANY_LOG      = "logWood";
    private static final String ANY_RAW_MEAT = "listAllmeatraw";
    private static final String ANY_RAW_FISH = "listAllfishraw";

    private static Item         charredMeat;

    public CampfireRecipeTweaks ()
    {
        charredMeat = GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "charredMeat");
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        if (Loader.isModLoaded(ModIds.HARVESTCRAFT))
        {
            nerfHarvestCraftRecipes();
        }
    }

    @Override
    protected void nerfStandardRecipes ()
    {
        // Charcoal
        if (OreDictionary.doesOreNameExist(ANY_LOG))
        {
            for (ItemStack oreStack : OreDictionary.getOres(ANY_LOG))
            {
                nerfCampfireRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "coal"), 1, 1),
                                   oreStack, 0.15F, false);
            }
        }

        // Cooked Meat & Fish
        if (OreDictionary.doesOreNameExist(ANY_RAW_MEAT))
        {
            for (ItemStack oreStack : OreDictionary.getOres(ANY_RAW_MEAT))
            {
                nerfCampfireRecipe(new ItemStack(charredMeat), oreStack, 0.1F, true);
            }
        }

        if (OreDictionary.doesOreNameExist(ANY_RAW_FISH))
        {
            for (ItemStack oreStack : OreDictionary.getOres(ANY_RAW_FISH))
            {
                nerfCampfireRecipe(new ItemStack(charredMeat), oreStack, 0.1F, true);
            }
        }
    }

    private void nerfHarvestCraftRecipes ()
    {
        // TODO Auto-generated method stub
    }

    private static void nerfCampfireRecipe (final ItemStack outputStack, final ItemStack inputStack,
                                            final float experience, final boolean addPanRecipe)
    {
        Entry<ItemStack, ItemStack> furnaceRecipe = FurnaceRecipeHandler.getSmeltingRecipe(inputStack);

        if (furnaceRecipe != null && furnaceRecipe.getKey() != null && furnaceRecipe.getValue() != null)
        {
            if (addPanRecipe)
            {
                BetterBeginningsHandler.addNerfedCampfirePanRecipe(furnaceRecipe.getKey(), furnaceRecipe.getValue(), experience);
            }

            FurnaceRecipeHandler.removeSmeltingRecipes(furnaceRecipe.getKey());
        }

        BetterBeginningsHandler.addNerfedCampfireRecipe(inputStack, outputStack, experience);
    }
}
