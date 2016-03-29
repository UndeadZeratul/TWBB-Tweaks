package com.undeadzeratul.twbbtweaks.tweaks;

import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.ArmorRecipeTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.BrickOvenRecipeTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.CampfireRecipeTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.KilnRecipeTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings.MiscRecipeTweaks;
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

            if (Settings.BetterBeginnings.nerfCraftingRecipes)
            {
                if (Settings.BetterBeginnings.nerfAllArmorRecipes)
                {
                    ArmorRecipeTweaks armorTweaks = new ArmorRecipeTweaks();
                    armorTweaks.nerfRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllToolRecipes)
                {
                    ToolRecipeTweaks toolTweaks = new ToolRecipeTweaks();
                    toolTweaks.nerfRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllMiscRecipes)
                {
                    MiscRecipeTweaks miscRecipeTweaks = new MiscRecipeTweaks();
                    miscRecipeTweaks.nerfRecipes();
                }

                if (Settings.BetterBeginnings.nerfAllCookingRecipes)
                {
                    if (Settings.BetterBeginnings.nerfAllBrickOvenRecipes)
                    {
                        BrickOvenRecipeTweaks brickOvenTweaks = new BrickOvenRecipeTweaks();
                        brickOvenTweaks.nerfRecipes();
                    }

                    if (Settings.BetterBeginnings.nerfAllCampfireRecipes)
                    {
                        CampfireRecipeTweaks campfireTweaks = new CampfireRecipeTweaks();
                        campfireTweaks.nerfRecipes();
                    }

                    if (Settings.BetterBeginnings.nerfAllKilnRecipes)
                    {
                        KilnRecipeTweaks kilnTweaks = new KilnRecipeTweaks();
                        kilnTweaks.nerfRecipes();
                    }
                }
            }
        }
    }

    private static void disableBBSmelter ()
    {
        CraftingRecipeHandler.removeCraftingRecipes(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "smelter"));
    }
}
