package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

public abstract class AbstractBBTweaks
{
    public final void nerfRecipes ()
    {
        nerfNonStandardRecipes();

        nerfStandardRecipes();
    }

    protected abstract void nerfNonStandardRecipes ();

    protected abstract void nerfStandardRecipes ();
}
