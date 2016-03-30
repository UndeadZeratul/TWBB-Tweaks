package com.undeadzeratul.twbbtweaks.handler;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class FurnaceRecipeHandler
{
    private static final Map<ItemStack, ItemStack> RECIPES = FurnaceRecipes.smelting().getSmeltingList();

    public static void removeSmeltingRecipes (final ItemStack inputStack)
    {
        removeSmeltingRecipes(inputStack.getItem());
    }

    public static void removeSmeltingRecipes (final Item input)
    {
        boolean recipeFound = true;

        while (recipeFound)
        {
            Entry<ItemStack, ItemStack> smeltingEntry = getSmeltingRecipe(input);

            if (smeltingEntry != null)
            {
                RECIPES.remove(smeltingEntry.getKey(), smeltingEntry.getValue());
            }
            else
            {
                recipeFound = false;
            }
        }
    }

    public static boolean smeltingRecipeExists (final ItemStack inputStack)
    {
        return smeltingRecipeExists(inputStack.getItem());
    }

    public static boolean smeltingRecipeExists (final Item input)
    {
        return getSmeltingRecipe(input) != null;
    }

    public static Entry<ItemStack, ItemStack> getSmeltingRecipe (final ItemStack inputStack)
    {
        return getSmeltingRecipe(inputStack.getItem());
    }

    public static Entry<ItemStack, ItemStack> getSmeltingRecipe (final Item input)
    {
        if (input != null)
        {
            String unlocalizedName = getUnlocalizedName(input);

            for (Entry<ItemStack, ItemStack> smeltingEntry : RECIPES.entrySet())
            {
                if (smeltingEntry != null
                    && smeltingEntry.getKey() != null
                    && smeltingEntry.getValue() != null
                    && StringUtils.equals(getUnlocalizedName(smeltingEntry.getKey().getItem()), unlocalizedName))
                {
                    return smeltingEntry;
                }
            }
        }

        return null;
    }

    private static String getUnlocalizedName (final Item output)
    {
        if (output instanceof ItemBlock)
        {
            return ((ItemBlock) output).getUnlocalizedName();
        }
        else
        {
            return output.getUnlocalizedName();
        }
    }
}
