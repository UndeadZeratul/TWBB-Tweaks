package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.einsteinsci.betterbeginnings.items.ItemFlintHatchet;
import net.einsteinsci.betterbeginnings.items.ItemKnifeBone;
import net.einsteinsci.betterbeginnings.items.ItemKnifeFlint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.OreDictionary;

public class ToolRecipeTweaks extends AbstractBBTweaks
{
    private static final String ANY_BLAZE_RON    = "itemBlazeRod";
    private static final String ANY_IRON_ROD     = "rodIron";
    private static final String ANY_OBSIDIAN_ROD = "rodObsidian";
    private static final String ANY_STICK        = "stickWood";
    private static final String ANY_STRING       = "itemString";

    private static Item         leatherStrip;
    private static Item         naturaItem;
    private static Item         naturaPlanks;
    private static Item         naturaStick;

    public ToolRecipeTweaks ()
    {
        leatherStrip = GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip");
        naturaItem = GameRegistry.findItem(ModIds.NATURA, "barleyFood");
        naturaPlanks = GameRegistry.findItem(ModIds.NATURA, "planks");
        naturaStick = GameRegistry.findItem(ModIds.NATURA, "natura.stick");
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        if (Loader.isModLoaded(ModIds.BETTER_STORAGE))
        {
            nerfBetterStorageTools();
        }

        if (Loader.isModLoaded(ModIds.FALLING_METEORS))
        {
            nerfFallingMeteorsTools();
        }

        if (Loader.isModLoaded(ModIds.HAMMERZ))
        {
            nerfHammerzTools();
        }

        if (Loader.isModLoaded(ModIds.MINE_AND_BLADE))
        {
            nerfMineAndBladeTools();
        }

        if (Loader.isModLoaded(ModIds.NATURA))
        {
            nerfNaturaTools();
        }

        if (Loader.isModLoaded(ModIds.RANDOM_THINGS))
        {
            nerfRandomThingsTools();
        }

        if (Loader.isModLoaded(ModIds.THERMAL_FOUNDATION))
        {
            nerfThermalFoundationTools();
        }

        if (Loader.isModLoaded(ModIds.TWILIGHT_FOREST))
        {
            nerfTwilightforestTools();
        }
    }

    @Override
    protected void nerfStandardRecipes ()
    {
        for (Item item : (Iterable<Item>) GameData.getItemRegistry())
        {
            if ((item instanceof ItemTool || item instanceof ItemSword) && !isBBTool(item))
            {
                String repairMaterial = BetterBeginningsHandler.getToolRepairMaterial(item);

                if (!Strings.isNullOrEmpty(repairMaterial) && !OreDictionary.getOres(repairMaterial).isEmpty())
                {
                    nerfToolRecipe(new ItemStack(item), repairMaterial);
                }
            }
        }
    }

    private static void nerfBetterStorageTools ()
    {
        ItemStack cardboardSheet = new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardSheet"));

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardPickaxe")), cardboardSheet,
                       ANY_STICK, ANY_STRING);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardSword")), cardboardSheet,
                       ANY_STICK, ANY_STRING);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardAxe")), cardboardSheet,
                       ANY_STICK, ANY_STRING);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardShovel")), cardboardSheet,
                       ANY_STICK, ANY_STRING);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardHoe")), cardboardSheet,
                       ANY_STICK, ANY_STRING);
    }

    private static void nerfFallingMeteorsTools ()
    {
        // Meteorite
        ItemStack meteoriteIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "MeteoriteIngot"));

        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "MetPick", 0, 1,
                                                  "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                       meteoriteIngot, ANY_IRON_ROD);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "MetSword", 0, 1,
                                                  "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                       meteoriteIngot, ANY_IRON_ROD);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "MetAxe", 0, 1,
                                                  "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                       meteoriteIngot, ANY_IRON_ROD);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "MetSpade", 0, 1,
                                                  "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                       meteoriteIngot, ANY_IRON_ROD);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "MetHoe", 0, 1,
                                                  "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                       meteoriteIngot, ANY_IRON_ROD);

        // Frozen Iron
        ItemStack frozenIronIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrozenIron"));

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrezPick")), frozenIronIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrezSword")), frozenIronIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrezAxe")), frozenIronIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrezSpade")), frozenIronIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrezHoe")), frozenIronIngot);

        // Kreknorite
        ItemStack kreknoriteIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "KreknoriteIngot"));

        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ':' + "KrekSword", 0, 1,
                                                  "{ench: [{lvl: 2, id: 20}], enchant-set: true}"),
                       kreknoriteIngot, ANY_OBSIDIAN_ROD, new ItemStack(naturaItem, 2, 7));
    }

    private static void nerfHammerzTools ()
    {
        // TODO Auto-generated method stub

    }

    private static void nerfMineAndBladeTools ()
    {
        // TODO Auto-generated method stub

    }

    private static void nerfNaturaTools ()
    {
        ItemStack leatherStrips = new ItemStack(leatherStrip, 1);
        ItemStack flameString = new ItemStack(naturaItem, 1, 7);

        // Ghostwood
        ItemStack ghostwoodPlanks = new ItemStack(naturaPlanks, 1, 2);
        ItemStack ghostwoodStick = new ItemStack(naturaStick, 1, 2);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.pickaxe.ghostwood")), ghostwoodPlanks,
                       ghostwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.sword.ghostwood")), ghostwoodPlanks,
                       ghostwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.axe.ghostwood")), ghostwoodPlanks,
                       ghostwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.shovel.ghostwood")), ghostwoodPlanks,
                       ghostwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.bow.ghostwood")), ghostwoodStick,
                       flameString);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.kama.ghostwood")),
                       new Object[] {
                           "ii",
                           " s",
                           " s",
                           'i',
                           ghostwoodPlanks,
                           's',
                           ghostwoodStick
                       }, leatherStrips);

        // Bloodwood
        ItemStack bloodwoodPlanks = new ItemStack(naturaPlanks, 1, 4);
        ItemStack bloodwoodStick = new ItemStack(naturaStick, 1, 4);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.pickaxe.bloodwood")), bloodwoodPlanks,
                       bloodwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.sword.bloodwood")), bloodwoodPlanks,
                       bloodwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.axe.bloodwood")), bloodwoodPlanks,
                       bloodwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.shovel.bloodwood")), bloodwoodPlanks,
                       bloodwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.bow.bloodwood")), bloodwoodStick,
                       flameString);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.kama.bloodwood")),
                       new Object[] {
                           "ii",
                           " s",
                           " s",
                           'i',
                           bloodwoodPlanks,
                           's',
                           bloodwoodStick
                       }, leatherStrips);

        // Darkwood
        ItemStack darkwoodPlanks = new ItemStack(naturaPlanks, 1, 2);
        ItemStack darkwoodStick = new ItemStack(naturaStick, 1, 2);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.pickaxe.darkwood")), darkwoodPlanks,
                       darkwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.sword.darkwood")), darkwoodPlanks,
                       darkwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.axe.darkwood")), darkwoodPlanks,
                       darkwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.shovel.darkwood")), darkwoodPlanks,
                       darkwoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.bow.darkwood")), bloodwoodStick,
                       flameString);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.kama.darkwood")),
                       new Object[] {
                           "ii",
                           " s",
                           " s",
                           'i',
                           darkwoodPlanks,
                           's',
                           darkwoodStick
                       }, leatherStrips);

        // Fusewood
        ItemStack fusewoodPlanks = new ItemStack(naturaPlanks, 1, 11);
        ItemStack fusewoodStick = new ItemStack(naturaStick, 1, 11);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.pickaxe.fusewood")), fusewoodPlanks,
                       fusewoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.sword.fusewood")), fusewoodPlanks,
                       fusewoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.axe.fusewood")), fusewoodPlanks,
                       fusewoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.shovel.fusewood")), fusewoodPlanks,
                       fusewoodStick);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.bow.fusewood")), fusewoodStick,
                       flameString);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.kama.fusewood")),
                       new Object[] {
                           "ii",
                           " s",
                           " s",
                           'i',
                           fusewoodPlanks,
                           's',
                           fusewoodStick
                       }, leatherStrips);

        // Fusewood
        ItemStack quartzBlock = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "quartz_block"), 1,
                                              OreDictionary.WILDCARD_VALUE);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.pickaxe.netherquartz")), quartzBlock,
                       ANY_STICK);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.sword.netherquartz")), quartzBlock,
                       ANY_STICK);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.axe.netherquartz")), quartzBlock,
                       ANY_STICK);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.shovel.netherquartz")), quartzBlock,
                       ANY_STICK);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.NATURA, "natura.kama.netherquartz")),
                       new Object[] {
                           "ii",
                           " s",
                           " s",
                           'i',
                           quartzBlock,
                           's',
                           ANY_STICK
                       }, leatherStrips);
    }

    private static void nerfRandomThingsTools ()
    {
        Item randomThingsIngredient = GameRegistry.findItem(ModIds.RANDOM_THINGS, "ingredient");
        ItemStack spectreIngot = new ItemStack(randomThingsIngredient, 1, 4);

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.RANDOM_THINGS, "spectreSword")), spectreIngot,
                       ANY_OBSIDIAN_ROD);
    }

    private static void nerfThermalFoundationTools ()
    {
        String[] toolPrefixes = new String[]
        {
            "tool.pickaxe",
            "tool.axe",
            "tool.shovel",
            "tool.hoe",
            "tool.bow",
            "tool.shears",
            "tool.fishingRod",
            "tool.sickle"
        };
        String[] metals = new String[]
        {
            "Bronze",
            "Copper",
            "Electrum",
            "Invar",
            "Lead",
            "Nickel",
            "Platinum",
            "Silver",
            "Tin"
        };
        Object[][] toolCraftingPatterns = new Object[][]
        {
            new Object[] {
                "iii",
                " s ",
                " s ",
                'i',
                null,
                's',
                ANY_STICK
            },
            new Object[] {
                "ii",
                "is",
                " s",
                'i',
                null,
                's',
                ANY_STICK
            },
            new Object[] {
                "i",
                "s",
                "s",
                'i',
                null,
                's',
                ANY_STICK
            },
            new Object[] {
                "ii",
                " s",
                " s",
                'i',
                null,
                's',
                ANY_STICK
            },
            new Object[] {
                " is",
                "h s",
                " is",
                'i',
                null,
                'h',
                ANY_STICK,
                's',
                ANY_STRING
            },
            new Object[] {
                " i",
                "i ",
                'i',
                null
            },
            new Object[] {
                "  i",
                " is",
                "h s",
                'i',
                null,
                'h',
                ANY_STICK,
                's',
                ANY_STRING
            },
            new Object[] {
                " i ",
                "  i",
                "si ",
                'i',
                null,
                's',
                ANY_STICK
            }
        };

        for (String metal : metals)
        {
            for (int j = 0; j < toolPrefixes.length; j++)
            {
                nerfThermalFoundationTool(toolPrefixes[j], metal, toolCraftingPatterns[j]);
            }
        }
    }

    private static void nerfThermalFoundationTool (final String toolPrefix, final String metalName, final Object[] craftingPattern)
    {
        craftingPattern[craftingPattern.length == 4 ? 3 : 4] = "ingot" + metalName;

                if (toolPrefix.equals("tool.shears") || toolPrefix.equals("tool.fishingRod"))
                {
                    nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.THERMAL_FOUNDATION, toolPrefix + metalName)), craftingPattern, "nugget" + metalName);
                }
                else
                {
                    nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.THERMAL_FOUNDATION, toolPrefix + metalName)), craftingPattern);
                }
    }

    private static void nerfTwilightforestTools ()
    {
        // Ironwood
        ItemStack ironwoodIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.ironwoodIngot"));

        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.ironwoodPick", 0, 1,
                                                  "{ench: [{lvl: 1, id: 32}]}"),
                       ironwoodIngot);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.ironwoodSword", 0, 1,
                                                  "{ench: [{lvl: 1, id: 19}]}"),
                       ironwoodIngot);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.ironwoodAxe", 0, 1,
                                                  "{ench: [{lvl: 1, id: 35}]}"),
                       ironwoodIngot);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.ironwoodShovel", 0, 1,
                                                  "{ench: [{lvl: 1, id: 34}]}"),
                       ironwoodIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.ironwoodHoe")), ironwoodIngot);

        // Steeleaf
        ItemStack steeleaf = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.steeleafIngot"));

        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.steeleafPick", 0, 1,
                                                  "{ench: [{lvl: 2, id: 35}]}"),
                       steeleaf);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.steeleafSword", 0, 1,
                                                  "{ench: [{lvl: 2, id: 21}]}"),
                       steeleaf);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.steeleafAxe", 0, 1,
                                                  "{ench: [{lvl: 2, id: 32}]}"),
                       steeleaf);
        nerfToolRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ':' + "item.steeleafShovel", 0, 1,
                                                  "{ench: [{lvl: 2, id: 32}]}"),
                       steeleaf);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.steeleafHoe")), steeleaf);

        // Fiery
        ItemStack fieryIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryIngot"));

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryPick")),
                        fieryIngot, ANY_BLAZE_RON, new ItemStack(naturaItem, 2, 7));
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fierySword")),
                        fieryIngot, ANY_BLAZE_RON, new ItemStack(naturaItem, 2, 7));

        // Knightmetal
        Item knightmetalRing = GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightMetalRing");

        ItemStack knightmetalIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightMetal"));
        ItemStack knightmetalBlock = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightMetalBlock"));

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyPick")),
                       knightmetalIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlySword")),
                       knightmetalIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyAxe")),
                       knightmetalIngot);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.chainBlock")),
                       new Object[] {
                           " b ",
                           "  r",
                           "ir ",
                           'b',
                           knightmetalBlock,
                           'r',
                           new ItemStack(knightmetalRing),
                           'i',
                           knightmetalIngot
                       },
                       new Object[] {
                           new ItemStack(knightmetalRing, 2)
                       });

        // Giant
        ItemStack giantCobblestone = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "tile.GiantCobble"));
        ItemStack giantLog = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "tile.GiantLog"));

        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.giantPick")), giantCobblestone, giantLog);
        nerfToolRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.giantSword")), giantCobblestone, giantLog);
    }

    private static boolean isBBTool (final Item item)
    {
        return item instanceof ItemKnifeFlint
                || item instanceof ItemFlintHatchet
                || item instanceof ItemKnifeBone;
    }

    private static void nerfToolRecipe (final ItemStack outputStack, final Object craftingMaterial)
    {
        nerfToolRecipe(outputStack, craftingMaterial, ANY_STICK);
    }

    private static void nerfToolRecipe (final ItemStack outputStack, final Object[] craftingMaterial)
    {
        nerfToolRecipe(outputStack, craftingMaterial, StringUtils.EMPTY);
    }

    private static void nerfToolRecipe (final ItemStack outputStack, final Object[] craftingMaterial,
                                        final Object additionalMaterials)
    {
        nerfToolRecipe(outputStack, craftingMaterial, StringUtils.EMPTY, additionalMaterials);
    }

    private static void nerfToolRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                        final Object handleMaterial)
    {
        nerfToolRecipe(outputStack, craftingMaterial, handleMaterial, StringUtils.EMPTY);
    }

    private static void nerfToolRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                        final Object handleMaterial, final Object additionalMaterials)
    {
        if (!BetterBeginningsHandler.advCraftingRecipeExists(outputStack) &&
            CraftingRecipeHandler.craftingRecipeExists(outputStack))
        {
            CraftingRecipeHandler.removeCraftingRecipes(outputStack);
            BetterBeginningsHandler.addNerfedToolRecipe(outputStack, craftingMaterial, handleMaterial,
                                                        additionalMaterials);
        }
    }
}
