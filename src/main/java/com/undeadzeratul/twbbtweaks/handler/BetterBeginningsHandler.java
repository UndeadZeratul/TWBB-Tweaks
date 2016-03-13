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

    public static void addNerfedArmorRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                              final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null && outputStack.getItem() instanceof ItemArmor)
        {
            advCraftingInstance.addAdvancedRecipe(outputStack, buildAdditionalArmorMaterials(outputStack, extraMaterial),
                                                      buildArmorCraftingPattern(outputStack, craftingMaterial));
        }
    }

    private static Object[] buildAdditionalArmorMaterials (final ItemStack outputStack, final Object extraMaterial)
    {
        if (extraMaterial instanceof Object[])
        {
            return (Object[]) extraMaterial;
        }

        AdditionalArmorCosts additionalCosts = AdditionalArmorCosts.values()[((ItemArmor) outputStack.getItem()).armorType];

        ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), additionalCosts.itemStack2);
        ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), additionalCosts.itemStack3, OreDictionary.WILDCARD_VALUE);

        return extraMaterial instanceof String
                ? !Strings.isNullOrEmpty((String) extraMaterial)
                        ? new Object[]
                        {
                            extraMaterial, additionalCosts.itemStack1,
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
    }

    private static Object[] buildArmorCraftingPattern (final ItemStack outputStack, final Object craftingMaterial)
    {
        if (craftingMaterial instanceof Object[])
        {
            return (Object[]) craftingMaterial;
        }

        switch (((ItemArmor) outputStack.getItem()).armorType)
        {
            case 0:
                return new Object[]
                {
                    "iii",
                    "i i",
                    'i',
                    craftingMaterial
                };
            case 1:
                return new Object[]
                {
                    "i i",
                    "iii",
                    "iii",
                    'i',
                    craftingMaterial
                };
            case 2:
                return new Object[]
                {
                    "iii",
                    "i i",
                    "i i",
                    'i',
                    craftingMaterial
                };
            case 3:
                return new Object[]
                {
                    "i i",
                    "i i",
                    'i',
                    craftingMaterial
                };
            default:
                return new Object[0];
        }
    }

    private enum AdditionalArmorCosts
    {
        HELMET(2, 3, 2),
        CHESTPLATE(6, 2, 4),
        LEGGINGS(4, 4, 1),
        BOOTS(3, 2, 3);

        private int itemStack1 = 0;
        private int itemStack2 = 0;
        private int itemStack3 = 0;

        AdditionalArmorCosts (final int itemStack1, final int itemStack2, final int itemStack3)
        {
            this.itemStack1 = itemStack1;
            this.itemStack2 = itemStack2;
            this.itemStack3 = itemStack3;
        }
    }
}
