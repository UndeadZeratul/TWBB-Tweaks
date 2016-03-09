package com.undeadzeratul.twbbtweaks.handler;

import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class CraftingRecipeHandler
{
    private static final List<IRecipe> RECIPES = CraftingManager.getInstance().getRecipeList();

    public static void removeCraftingRecipe (final ItemStack output)
    {
        if (output != null)
        {
            Iterator iter = RECIPES.iterator();
            while (iter.hasNext())
            {
                if (ItemStack.areItemStacksEqual(((IRecipe) iter.next()).getRecipeOutput(), output))
                {
                    iter.remove();
                }
            }
        }
    }

    public static boolean craftingRecipeExists (final Item item)
    {
        ItemStack output = new ItemStack(item);

        if (output != null)
        {
            Iterator iter = RECIPES.iterator();
            for (IRecipe recipe : RECIPES)
            {
                if (ItemStack.areItemStacksEqual(((IRecipe) iter.next()).getRecipeOutput(), output))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
