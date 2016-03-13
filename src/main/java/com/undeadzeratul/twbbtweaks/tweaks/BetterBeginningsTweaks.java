package com.undeadzeratul.twbbtweaks.tweaks;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BetterBeginningsTweaks
{
    private static final String ANY_IRON_NUGGET = "nuggetIron";
    private static final String ANY_WOOD_LOG    = "logWood";
    private static final String ANY_STICK       = "stickWood";

    public static void init ()
    {
        if (Settings.BetterBeginnings.enableBBTweaks)
        {
            if (Settings.BetterBeginnings.disableBBSmelter)
            {
                disableBBSmelter();
            }

            if (Settings.BetterBeginnings.nerfAllRecipes)
            {
                if (Settings.BetterBeginnings.nerfAllArmorRecipes)
                {
                    nerfAllArmorRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllToolRecipes)
                {
                    nerfAllToolRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllMiscRecipes)
                {
                    nerfAllMiscRecipes();
                }
            }
        }
    }

    private static void disableBBSmelter ()
    {
        CraftingRecipeHandler
                .removeCraftingRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "smelter")));
    }

    private static void nerfAllArmorRecipes ()
    {
        nerfNonStandardArmorRecipes();

        nerfStandardArmorRecipes();
    }

    private static void nerfNonStandardArmorRecipes ()
    {
        nerfChainArmor();

        if (Loader.isModLoaded(ModIds.BETTER_STORAGE))
        {
            nerfBetterStorageArmor();
        }

        if (Loader.isModLoaded(ModIds.FSP))
        {
            nerfFSPArmor();
        }

        if (Loader.isModLoaded(ModIds.TIC))
        {
            nerfTconstructArmor();
        }
    }

    private static void nerfBetterStorageArmor ()
    {

    }

    private static void nerfFSPArmor ()
    {
        ItemStack gildedGoldIngot = new ItemStack(GameRegistry.findItem(ModIds.FSP, "steamcraftIngot"), 1, 3);

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "helmGildedGold")), gildedGoldIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "chestGildedGold")), gildedGoldIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "legsGildedGold")), gildedGoldIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "feetGildedGold")), gildedGoldIngot, ANY_IRON_NUGGET);
    }

    private static void nerfChainArmor ()
    {
        Object mainCost = Loader.isModLoaded(ModIds.MINE_AND_BLADE)
                ? new ItemStack(GameRegistry.findItem(ModIds.MINE_AND_BLADE, "chain"))
                : ANY_IRON_NUGGET;
        Object additionalCost = Loader.isModLoaded(ModIds.MINE_AND_BLADE)
                ? ANY_IRON_NUGGET
                : StringUtils.EMPTY;

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_helmet")), mainCost, additionalCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_chestplate")), mainCost, additionalCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_leggings")), mainCost, additionalCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_boots")), mainCost, additionalCost);
    }

    private static void nerfTconstructArmor ()
    {
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "helmetWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "chestplateWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "leggingsWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "bootsWood")), ANY_WOOD_LOG, ANY_STICK);
    }

    private static void nerfStandardArmorRecipes ()
    {
        for (Item item : (Iterable<Item>) GameData.getItemRegistry())
        {
            if (item instanceof ItemArmor
                && !BetterBeginningsHandler.advCraftingRecipeExists(item)
                && CraftingRecipeHandler.craftingRecipeExists(item))
            {
                ItemArmor armor = (ItemArmor) item;
                ItemStack outputStack = new ItemStack(armor);
                String repairMaterial = BetterBeginningsHandler.getRepairMaterial(armor);

                if (!Strings.isNullOrEmpty(repairMaterial))
                {
                    String ingot = repairMaterial.startsWith("ingot")
                            ? repairMaterial
                            : "ingot" + repairMaterial;
                    String nugget = repairMaterial.startsWith("nugget")
                            ? repairMaterial
                            : "nugget" + repairMaterial;

                    if (!OreDictionary.getOres(ingot).isEmpty() && !OreDictionary.getOres(nugget).isEmpty() && outputStack.getItem() instanceof ItemArmor)
                    {
                        nerfArmorRecipe(outputStack, ingot, nugget);
                    }
                }
            }
        }
    }

    private static void nerfArmorRecipe (final ItemStack outputStack, final Object ingot, final Object nugget)
    {
        CraftingRecipeHandler.removeCraftingRecipe(outputStack);
        BetterBeginningsHandler.addNerfedArmorRecipe(outputStack, ingot, nugget);
    }

    private static void nerfAllToolRecipes ()
    {
        // TODO Auto-generated method stub
    }

    private static void nerfAllMiscRecipes ()
    {
        // TODO Auto-generated method stub
    }
}
