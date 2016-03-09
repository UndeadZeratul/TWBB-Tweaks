package com.undeadzeratul.twbbtweaks.tweaks;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.utility.LogHelper;

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

        ItemStack gildedGoldHelmet = new ItemStack(GameRegistry.findItem(ModIds.FSP, "helmGildedGold"));
        ItemStack gildedGoldChestPlate = new ItemStack(GameRegistry.findItem(ModIds.FSP, "chestGildedGold"));
        ItemStack gildedGoldLeggings = new ItemStack(GameRegistry.findItem(ModIds.FSP, "legsGildedGold"));
        ItemStack gildedGoldBoots = new ItemStack(GameRegistry.findItem(ModIds.FSP, "feetGildedGold"));

        CraftingRecipeHandler.removeCraftingRecipe(gildedGoldHelmet);
        CraftingRecipeHandler.removeCraftingRecipe(gildedGoldChestPlate);
        CraftingRecipeHandler.removeCraftingRecipe(gildedGoldLeggings);
        CraftingRecipeHandler.removeCraftingRecipe(gildedGoldBoots);

        BetterBeginningsHandler.addNerfedHelmetRecipe(gildedGoldHelmet, gildedGoldIngot, ANY_IRON_NUGGET);
        BetterBeginningsHandler.addNerfedChestplateRecipe(gildedGoldChestPlate, gildedGoldIngot, ANY_IRON_NUGGET);
        BetterBeginningsHandler.addNerfedLeggingsRecipe(gildedGoldLeggings, gildedGoldIngot, ANY_IRON_NUGGET);
        BetterBeginningsHandler.addNerfedBootsRecipe(gildedGoldBoots, gildedGoldIngot, ANY_IRON_NUGGET);
    }

    private static void nerfChainArmor ()
    {

        ItemStack chainmailHelmet = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_helmet"));
        ItemStack chainmailChestplate = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_chestplate"));
        ItemStack chainmailLeggings = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_leggings"));
        ItemStack chainmailBoots = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_boots"));

        CraftingRecipeHandler.removeCraftingRecipe(chainmailHelmet);
        CraftingRecipeHandler.removeCraftingRecipe(chainmailChestplate);
        CraftingRecipeHandler.removeCraftingRecipe(chainmailLeggings);
        CraftingRecipeHandler.removeCraftingRecipe(chainmailBoots);

        if (Loader.isModLoaded(ModIds.MINE_AND_BLADE))
        {
            ItemStack chain = new ItemStack(GameRegistry.findItem(ModIds.MINE_AND_BLADE, "chain"));

            BetterBeginningsHandler.addNerfedHelmetRecipe(chainmailHelmet, chain, ANY_IRON_NUGGET);
            BetterBeginningsHandler.addNerfedChestplateRecipe(chainmailChestplate, chain, ANY_IRON_NUGGET);
            BetterBeginningsHandler.addNerfedLeggingsRecipe(chainmailLeggings, chain, ANY_IRON_NUGGET);
            BetterBeginningsHandler.addNerfedBootsRecipe(chainmailBoots, chain, ANY_IRON_NUGGET);
        }
        else
        {
            BetterBeginningsHandler.addNerfedHelmetRecipe(chainmailHelmet, ANY_IRON_NUGGET, StringUtils.EMPTY);
            BetterBeginningsHandler.addNerfedChestplateRecipe(chainmailChestplate, ANY_IRON_NUGGET, StringUtils.EMPTY);
            BetterBeginningsHandler.addNerfedLeggingsRecipe(chainmailLeggings, ANY_IRON_NUGGET, StringUtils.EMPTY);
            BetterBeginningsHandler.addNerfedBootsRecipe(chainmailBoots, ANY_IRON_NUGGET, StringUtils.EMPTY);
        }
    }

    private static void nerfTconstructArmor ()
    {
        ItemStack woodHelmet = new ItemStack(GameRegistry.findItem(ModIds.TIC, "helmetWood"));
        ItemStack woodChestplate = new ItemStack(GameRegistry.findItem(ModIds.TIC, "chestplateWood"));
        ItemStack woodLeggings = new ItemStack(GameRegistry.findItem(ModIds.TIC, "leggingsWood"));
        ItemStack woodBoots = new ItemStack(GameRegistry.findItem(ModIds.TIC, "bootsWood"));

        CraftingRecipeHandler.removeCraftingRecipe(woodHelmet);
        CraftingRecipeHandler.removeCraftingRecipe(woodChestplate);
        CraftingRecipeHandler.removeCraftingRecipe(woodLeggings);
        CraftingRecipeHandler.removeCraftingRecipe(woodBoots);

        BetterBeginningsHandler.addNerfedHelmetRecipe(woodHelmet, ANY_WOOD_LOG, ANY_STICK);
        BetterBeginningsHandler.addNerfedChestplateRecipe(woodChestplate, ANY_WOOD_LOG, ANY_STICK);
        BetterBeginningsHandler.addNerfedLeggingsRecipe(woodLeggings, ANY_WOOD_LOG, ANY_STICK);
        BetterBeginningsHandler.addNerfedBootsRecipe(woodBoots, ANY_WOOD_LOG, ANY_STICK);
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

                    if (!OreDictionary.getOres(ingot).isEmpty() && !OreDictionary.getOres(nugget).isEmpty())
                    {
                        CraftingRecipeHandler.removeCraftingRecipe(outputStack);

                        switch (armor.armorType)
                        {
                            case 0:
                                BetterBeginningsHandler.addNerfedHelmetRecipe(outputStack, ingot, nugget);
                                break;
                            case 1:
                                BetterBeginningsHandler.addNerfedChestplateRecipe(outputStack, ingot, nugget);
                                break;
                            case 2:
                                BetterBeginningsHandler.addNerfedLeggingsRecipe(outputStack, ingot, nugget);
                                break;
                            case 3:
                                BetterBeginningsHandler.addNerfedBootsRecipe(outputStack, ingot, nugget);
                                break;
                            default:
                                LogHelper.error(String.format("Unknown Armor Type: %s", armor.armorType));
                                break;
                        }
                    }
                }
            }
        }
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
