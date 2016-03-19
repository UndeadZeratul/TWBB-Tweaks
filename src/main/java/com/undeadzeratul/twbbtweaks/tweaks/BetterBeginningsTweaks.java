package com.undeadzeratul.twbbtweaks.tweaks;

import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.ArmorRecipeTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.ToolRecipeTweaks;

import cpw.mods.fml.common.registry.GameRegistry;

public class BetterBeginningsTweaks
{
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
                    ArmorRecipeTweaks.init();
                    ArmorRecipeTweaks.nerfAllArmorRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllToolRecipes)
                {
                    ToolRecipeTweaks.init();
                    ToolRecipeTweaks.nerfAllToolRecipes();
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
        CraftingRecipeHandler.removeCraftingRecipe(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "smelter"));
    }

    private static void nerfAllMiscRecipes ()
    {
        // TODO Auto-generated method stub
    }
}
