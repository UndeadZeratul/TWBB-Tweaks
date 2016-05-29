package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;

public class BrickOvenRecipeTweaks extends AbstractBBTweaks
{
    public BrickOvenRecipeTweaks ()
    {
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        if (Loader.isModLoaded(ModIds.COOKING_FOR_BLOCKHEADS))
        {
            nerfCookingBookRecipes();
        }
    }

    @Override
    protected void nerfStandardRecipes ()
    {
        // no-op
    }

    private void nerfCookingBookRecipes ()
    {
        // TODO Auto-generated method stub
    }
}
