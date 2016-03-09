package com.undeadzeratul.twbbtweaks.handler;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.registry.GameRegistry;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BetterBeginningsHandler
{
    private static AdvancedCraftingHandler advCraftingInstance;
    private static SmelterRecipeHandler    smelterRecipeInstance;

    public static void init ()
    {
        advCraftingInstance = AdvancedCraftingHandler.crafting();
        smelterRecipeInstance = SmelterRecipeHandler.smelting();
    }

    public static boolean advCraftingRecipeExists (final Item item)
    {
        for (AdvancedRecipe recipe : advCraftingInstance.getRecipeList())
        {
            if (StringUtils.equalsIgnoreCase(recipe.getRecipeOutput().getItem().getUnlocalizedName(),
                                             item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static String getRepairMaterial (final ItemArmor armor)
    {
        for (String oreDictName : OreDictionary.getOreNames())
        {
            if (!Strings.isNullOrEmpty(oreDictName))
            {
                for (ItemStack ore : OreDictionary.getOres(oreDictName))
                {
                    if (ore != null && ore.getItem().equals(armor.getArmorMaterial().func_151685_b()))
                    {
                        return oreDictName.startsWith("ingot")
                                ? oreDictName.substring(5)
                                : oreDictName;
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    public static void addNerfedHelmetRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                               final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null)
        {
            ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), 3);
            ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), 2, OreDictionary.WILDCARD_VALUE);

            Object[] additionalMaterials = extraMaterial instanceof String
                    ? !Strings.isNullOrEmpty((String) extraMaterial)
                            ? new Object[]
                            {
                                extraMaterial, 2,
                                leatherStrips,
                                woolBlocks
                            }
                            : new Object[]
                            {
                                leatherStrips,
                                woolBlocks
                            }
                    : new Object[]
                    {
                        extraMaterial,
                        leatherStrips,
                        woolBlocks
                    };

            Object[] args = craftingMaterial instanceof Object[]
                    ? (Object[]) craftingMaterial
                    : new Object[]
                    {
                        "iii",
                        "i i",
                        'i',
                        craftingMaterial
                    };

            AdvancedCraftingHandler.addAdvancedRecipe(outputStack, additionalMaterials, args);
        }
    }

    public static void addNerfedChestplateRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                                   final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null)
        {
            ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), 2);
            ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), 4, OreDictionary.WILDCARD_VALUE);

            Object[] additionalMaterials = extraMaterial instanceof String
                    ? !Strings.isNullOrEmpty((String) extraMaterial)
                            ? new Object[]
                            {
                                extraMaterial, 6,
                                leatherStrips,
                                woolBlocks
                            }
                            : new Object[]
                            {
                                leatherStrips,
                                woolBlocks
                            }
                    : new Object[]
                    {
                        extraMaterial,
                        leatherStrips,
                        woolBlocks
                    };

            Object[] args = craftingMaterial instanceof Object[]
                    ? (Object[]) craftingMaterial
                    : new Object[]
                    {
                        "i i",
                        "iii",
                        "iii",
                        'i',
                        craftingMaterial
                    };

            AdvancedCraftingHandler.addAdvancedRecipe(outputStack, additionalMaterials, args);
        }
    }

    public static void addNerfedLeggingsRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                                 final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null)
        {
            ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), 4);
            ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), 1, OreDictionary.WILDCARD_VALUE);

            Object[] additionalMaterials = extraMaterial instanceof String
                    ? !Strings.isNullOrEmpty((String) extraMaterial)
                            ? new Object[]
                            {
                                extraMaterial, 4,
                                leatherStrips,
                                woolBlocks
                            }
                            : new Object[]
                            {
                                leatherStrips,
                                woolBlocks
                            }
                    : new Object[]
                    {
                        extraMaterial,
                        leatherStrips,
                        woolBlocks
                    };

            Object[] args = craftingMaterial instanceof Object[]
                    ? (Object[]) craftingMaterial
                    : new Object[]
                    {
                        "iii",
                        "i i",
                        "i i",
                        'i',
                        craftingMaterial
                    };

            AdvancedCraftingHandler.addAdvancedRecipe(outputStack, additionalMaterials, args);
        }
    }

    public static void addNerfedBootsRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                              final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null)
        {
            ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), 2);
            ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), 3, OreDictionary.WILDCARD_VALUE);

            Object[] additionalMaterials = extraMaterial instanceof String
                    ? !Strings.isNullOrEmpty((String) extraMaterial)
                            ? new Object[]
                            {
                                extraMaterial, 3,
                                leatherStrips,
                                woolBlocks
                            }
                            : new Object[]
                            {
                                leatherStrips,
                                woolBlocks
                            }
                    : new Object[]
                    {
                        extraMaterial,
                        leatherStrips,
                        woolBlocks
                    };

            Object[] args = craftingMaterial instanceof Object[]
                    ? (Object[]) craftingMaterial
                    : new Object[]
                    {
                        "i i",
                        "i i",
                        'i',
                        craftingMaterial
                    };

            AdvancedCraftingHandler.addAdvancedRecipe(outputStack, additionalMaterials, args);
        }
    }
}
