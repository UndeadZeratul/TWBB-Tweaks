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

    public static void removeCraftingRecipes (final ItemStack outputStack)
    {
        removeCraftingRecipes(outputStack.getItem());
    }

    public static void removeCraftingRecipes (final Item output)
    {
        boolean recipeFound = true;

        while (recipeFound)
        {
            IRecipe recipe = getCraftingRecipe(output);

            if (recipe != null)
            {
                RECIPES.remove(recipe);
            }
            else
            {
                recipeFound = false;
            }
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
            return ((ItemBlock) output).getUnlocalizedName();
        }
        else
        {
            return output.getUnlocalizedName();
        }
    }
}
