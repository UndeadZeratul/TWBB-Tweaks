package com.undeadzeratul.twbbtweaks.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class CraftingRecipeHandler
{
    private static final List<IRecipe> RECIPES = CraftingManager.getInstance().getRecipeList();

    public static void removeCraftingRecipe (final ItemStack outputStack)
    {
        removeCraftingRecipe(outputStack.getItem());
    }

    public static void removeCraftingRecipe (final Item output)
    {
        IRecipe recipe = getCraftingRecipe(output);

        if (recipe != null)
        {
            RECIPES.remove(recipe);
        }
    }

    public static boolean craftingRecipeExists (final ItemStack itemStack)
    {
        return craftingRecipeExists(itemStack.getItem());
    }

    public static boolean craftingRecipeExists (final Item item)
    {
        return getCraftingRecipe(item) != null;
    }

    public static IRecipe getCraftingRecipe (final ItemStack outputStack)
    {
        return getCraftingRecipe(outputStack.getItem());
    }

    public static IRecipe getCraftingRecipe (final Item output)
    {
        if (output != null)
        {
            String unlocalizedName = getUnlocalizedName(output);

            for (IRecipe recipe : RECIPES)
            {
                if (recipe != null && recipe.getRecipeOutput() != null && StringUtils.equals(getUnlocalizedName(recipe.getRecipeOutput().getItem()), unlocalizedName))
                {
                    return recipe;
                }
            }
        }

        return null;
    }

    private static String getUnlocalizedName (final Item output)
    {
        if (output instanceof ItemBlock)
        {
            ItemBlock outputBlock = (ItemBlock) output;

            return outputBlock.getUnlocalizedName();
        }
        else
        {
            return output.getUnlocalizedName();
        }
    }
}
