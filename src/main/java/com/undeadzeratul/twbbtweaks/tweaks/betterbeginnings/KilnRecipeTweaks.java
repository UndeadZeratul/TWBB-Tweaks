package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import java.util.Map.Entry;

import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.FurnaceRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class KilnRecipeTweaks extends AbstractBBTweaks
{
    private static final String ANY_LOG = "logWood";

    public KilnRecipeTweaks ()
    {
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        if (Loader.isModLoaded(ModIds.ENVIROMINE))
        {
            nerfEnviromineRecipes();
        }

        if (Loader.isModLoaded(ModIds.LYCANITES_JUNGLE_MOBS))
        {
            nerfJungleMobRecipes();
        }

        if (Loader.isModLoaded(ModIds.METALLURGY))
        {
            nerfMetallurgyRecipes();
        }

        if (Loader.isModLoaded(ModIds.SALTY_MOD))
        {
            nerfTSaltyModRecipes();
        }

        if (Loader.isModLoaded(ModIds.TIC))
        {
            nerfTConstructRecipes();
        }

        if (Loader.isModLoaded(ModIds.TISTEELWORKS))
        {
            nerfTiSteelworksRecipes();
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
                nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "coal"), 1, 1),
                               oreStack, 0.15F);
            }
        }
    }

    private void nerfEnviromineRecipes ()
    {
        // Dirty Water Bottle -> Water Bottle
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "potion")),
                       new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "badWaterBottle")),
                       0.0F);

        // Cold Water Bottle -> Water Bottle
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "potion")),
                       new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "coldWaterBottle")),
                       0.0F);
    }

    private void nerfJungleMobRecipes ()
    {
        // Propolis -> Hardened Clay
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "hardened_clay")),
                       new ItemStack(GameRegistry.findItem(ModIds.LYCANITES_JUNGLE_MOBS, "propolis")),
                       0.1F);

        // Veswax -> Sugar
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "sugar"), 6),
                       new ItemStack(GameRegistry.findItem(ModIds.LYCANITES_JUNGLE_MOBS, "veswax")),
                       0.3F);
    }

    private void nerfMetallurgyRecipes ()
    {
        // Bitumen -> Tar
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.METALLURGY, "tar")),
                       new ItemStack(GameRegistry.findItem(ModIds.METALLURGY, "utility.item"), 1, 4),
                       0.1F);
    }

    private void nerfTSaltyModRecipes ()
    {
        // Saltwort -> Effervescent Ash
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.SALTY_MOD, "soda")),
                       new ItemStack(GameRegistry.findItem(ModIds.SALTY_MOD, "saltWortSeed")),
                       0.1F);
    }

    private void nerfTConstructRecipes ()
    {
        // Seared Cobblestone -> Seared Stone
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "Smeltery"), 1, 4),
                       new ItemStack(GameRegistry.findItem(ModIds.TIC, "Smeltery"), 1, 5),
                       0.1F);

        // Seared Brick
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "materials"), 1, 2),
                       new ItemStack(GameRegistry.findItem(ModIds.TIC, "CraftedSoil"), 1, 1),
                       0.35F);
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "materials"), 1, 37),
                       new ItemStack(GameRegistry.findItem(ModIds.TIC, "CraftedSoil"), 1, 6),
                       0.35F);

        // Graveyard Soil -> Consecrated Soil
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "CraftedSoil"), 1, 4),
                       new ItemStack(GameRegistry.findItem(ModIds.TIC, "CraftedSoil"), 1, 3),
                       0.35F);
    }

    private void nerfTiSteelworksRecipes ()
    {
        // Scorched Cobblestone -> Scorched Stone
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "HighOven"), 1, 4),
                       new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "HighOven"), 1, 5),
                       0.1F);

        // Limestone Cobblestone -> Limestone
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "Limestone")),
                       new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "Limestone"), 1, 1),
                       0.1F);

        // Limestone -> Limestone Brick
        nerfKilnRecipe(new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "Materials"), 1, 1),
                       new ItemStack(GameRegistry.findItem(ModIds.TISTEELWORKS, "Limestone")),
                       0.1F);
    }

    private void nerfKilnRecipe (final ItemStack outputStack, final ItemStack inputStack, final float experience)
    {
        Entry<ItemStack, ItemStack> furnaceRecipe = FurnaceRecipeHandler.getSmeltingRecipe(inputStack);

        if (furnaceRecipe != null && furnaceRecipe.getKey() != null && furnaceRecipe.getValue() != null)
        {
            FurnaceRecipeHandler.removeSmeltingRecipes(furnaceRecipe.getKey());
        }

        BetterBeginningsHandler.addNerfedKilnRecipe(inputStack, outputStack, experience);
    }
}
