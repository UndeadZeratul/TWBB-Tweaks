package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class MiscRecipeTweaks extends AbstractBBTweaks
{
    private static final Object ANY_BLAZE_ROD         = "itemBlazeRod";
    private static final String ANY_BRASS_INGOT       = "ingotBrass";
    private static final String ANY_BRASS_NUGGET      = "nuggetBrass";
    private static final Object ANY_CLEAR_GLASS_BLOCK = "blockGlassColorless";
    private static final String ANY_CLEAR_GLASS_PANE  = "paneGlassColorless";
    private static final Object ANY_DIAMOND_BLOCK     = "blockDiamond";
    private static final Object ANY_NETHER_STAR       = "itemNetherStar";
    private static final Object ANY_OBSIDIAN_BLOCK    = "blockObsidian";
    private static final Object ANY_STEEL_BLOCK       = "blockSteel";
    private static final Object ANY_STEEL_INGOT       = "ingotSteel";
    private static final Object ANY_STEEL_NUGGET      = "nuggetSteel";
    private static final String ANY_TORCH             = "blockTorch";

    public MiscRecipeTweaks ()
    {
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        if (Loader.isModLoaded(ModIds.ENVIROMINE))
        {
            nerfEnviromineRecipes();
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
        // no-op
    }

    private void nerfEnviromineRecipes ()
    {
        nerfRecipe(new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "davy_lamp"), 1, 0),
                   new Object[]
                   {
                       "iii",
                       "gtg",
                       "iii",
                       'i',
                       ANY_BRASS_INGOT,
                       'g',
                       ANY_CLEAR_GLASS_PANE,
                       't',
                       ANY_TORCH
                   },
                   new Object[]
                   {
                       ANY_BRASS_NUGGET,
                       4
                   });
    }

    private void nerfTConstructRecipes ()
    {
        ItemStack beacon = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "beacon"));
        BetterBeginningsHandler.removeNerfedCraftingRecipes(beacon);
        nerfRecipe(beacon,
                   new Object[]
                   {
                       "ggg",
                       "gsg",
                       "ooo",
                       'g',
                       ANY_CLEAR_GLASS_BLOCK,
                       's',
                       ANY_NETHER_STAR,
                       'o',
                       ANY_OBSIDIAN_BLOCK
                   },
                   new Object[]
                   {
                       ANY_DIAMOND_BLOCK, 1,
                       ANY_BLAZE_ROD, 4,
                       new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "potion"), 1, 16)
                   });
    }

    private void nerfTiSteelworksRecipes ()
    {
        ItemStack anvil = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "anvil"));
        BetterBeginningsHandler.removeNerfedCraftingRecipes(anvil);
        nerfRecipe(anvil,
                   new Object[]
                   {
                       "bbb",
                       " i ",
                       "iii",
                       'b',
                       ANY_STEEL_BLOCK,
                       'i',
                       ANY_STEEL_INGOT
                   },
                   new Object[]
                   {
                       ANY_STEEL_NUGGET, 4
                   });
    }

    private static void nerfRecipe (final ItemStack outputStack, final Object[] craftingPattern,
                                    final Object[] additionalMaterials)
    {
        if (!BetterBeginningsHandler.advCraftingRecipeExists(outputStack) &&
            CraftingRecipeHandler.craftingRecipeExists(outputStack))
        {
            CraftingRecipeHandler.removeCraftingRecipes(outputStack);
            BetterBeginningsHandler.addNerfedCraftingRecipe(outputStack, craftingPattern, additionalMaterials);
        }
    }
}
