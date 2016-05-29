package com.undeadzeratul.twbbtweaks.tweaks.betterbeginnings;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.handler.BetterBeginningsHandler;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArmorRecipeTweaks extends AbstractBBTweaks
{
    private static final Object ANY_BRASS_INGOT       = "ingotBrass";
    private static final Object ANY_BRASS_NUGGET      = "nuggetBrass";
    private static final Object ANY_BRASS_PLATE       = "plateSteamcraftBrass";
    private static final String ANY_IRON_INGOT        = "ingotIron";
    private static final String ANY_IRON_NUGGET       = "nuggetIron";
    private static final String ANY_KREKNORITE_NUGGET = "nuggetKreknorite";
    private static final String ANY_METEORITE_NUGGET  = "nuggetMeteorite";
    private static final String ANY_STICK             = "stickWood";
    private static final String ANY_STRING            = "itemString";
    private static final String ANY_WOOD_LOG          = "logWood";

    private static final char   ITEM_NAME_SEPARATOR   = ':';

    private static Item         leather;
    private static Item         leatherStrip;
    private static Item         naturaItem;
    private static Item         woolBlock;

    public ArmorRecipeTweaks()
    {
        leather = GameRegistry.findItem(ModIds.MINECRAFT, "leather");
        leatherStrip = GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip");
        naturaItem = GameRegistry.findItem(ModIds.NATURA, "barleyFood");
        woolBlock = GameRegistry.findItem(ModIds.MINECRAFT, "wool");
    }

    @Override
    protected void nerfNonStandardRecipes ()
    {
        nerfChainArmor();

        if (Loader.isModLoaded(ModIds.BETTER_STORAGE))
        {
            nerfBetterStorageArmor();
        }

        if (Loader.isModLoaded(ModIds.ENVIROMINE))
        {
            nerfEnviromineArmor();
        }

        if (Loader.isModLoaded(ModIds.FALLING_METEORS))
        {
            nerfFallingMeteorsArmor();
        }

        if (Loader.isModLoaded(ModIds.FSP))
        {
            nerfFSPArmor();
        }

        if (Loader.isModLoaded(ModIds.HARVESTCRAFT))
        {
            nerfHarvestcraftArmor();
        }

        if (Loader.isModLoaded(ModIds.NATURA))
        {
            nerfNaturaArmor();
        }

        if (Loader.isModLoaded(ModIds.PNEUMATICCRAFT))
        {
            nerfPneumaticCraftArmor();
        }

        if (Loader.isModLoaded(ModIds.RANDOM_THINGS))
        {
            nerfRandomThingsArmor();
        }

        if (Loader.isModLoaded(ModIds.TIC))
        {
            nerfTconstructArmor();
        }

        if (Loader.isModLoaded(ModIds.TWILIGHT_FOREST))
        {
            nerfTwilightforestArmor();
        }
    }

    @Override
    protected void nerfStandardRecipes ()
    {
        for (Item item : (Iterable<Item>) GameData.getItemRegistry())
        {
            if (item instanceof ItemArmor)
            {
                ItemArmor armor = (ItemArmor) item;
                ItemStack outputStack = new ItemStack(armor);
                String repairMaterial = BetterBeginningsHandler.getArmorRepairMaterial(armor);

                if (!Strings.isNullOrEmpty(repairMaterial))
                {
                    String ingot = repairMaterial.startsWith("ingot")
                            ? repairMaterial
                            : "ingot" + repairMaterial;
                    String nugget = repairMaterial.startsWith("nugget")
                            ? repairMaterial
                            : "nugget" + repairMaterial;

                    if (!OreDictionary.getOres(ingot).isEmpty() && !OreDictionary.getOres(nugget).isEmpty() && outputStack.getItem() instanceof ItemArmor)
                    {
                        nerfArmorRecipe(outputStack, ingot, nugget);
                    }
                }
            }
        }
    }

    private static void nerfChainArmor ()
    {
        Object mainCost = Loader.isModLoaded(ModIds.MINE_AND_BLADE)
                ? new ItemStack(GameRegistry.findItem(ModIds.MINE_AND_BLADE, "chain"))
                : ANY_IRON_NUGGET;

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_helmet")), mainCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_chestplate")), mainCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_leggings")), mainCost);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "chainmail_boots")), mainCost);
    }

    private static void nerfBetterStorageArmor ()
    {
        // Backpack
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "backpack")),
                        new Object[] {
                            "lll",
                            "l l",
                            "lll",
                            'l',
                            new ItemStack(leather)
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 6,
                            new ItemStack(leatherStrip, 4),
                            ANY_STRING, 2,
                            new ItemStack(woolBlock, 4, OreDictionary.WILDCARD_VALUE)
                        });

        // Cardboard
        ItemStack cardboardSheet = new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardSheet"));

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardHelmet")), cardboardSheet,
                        new Object[]
                        {
                            ANY_STRING, 3,
                            new ItemStack(woolBlock, 2, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardChestplate")), cardboardSheet,
                        new Object[]
                        {
                            ANY_STRING, 2,
                            new ItemStack(woolBlock, 4, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardLeggings")), cardboardSheet,
                        new Object[]
                        {
                            ANY_STRING, 4,
                            new ItemStack(woolBlock, 1, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.BETTER_STORAGE, "cardboardBoots")), cardboardSheet,
                        new Object[]
                        {
                            ANY_STRING, 2,
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE)
                        });
    }

    private void nerfEnviromineArmor ()
    {
        // Camel Pack (Empty)
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.ENVIROMINE + ITEM_NAME_SEPARATOR + "camelPack", 0, 1,
                                                   "{camelPackFill: 0, camelPackMax: 100, isCamelPack: true}"),
                        new Object[]
                        {
                            "lll",
                            "lbl",
                            "lll",
                            'l',
                            new ItemStack(leather),
                            'b',
                            new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "glass_bottle"))
                        },
                        new Object[]
                        {
                            new ItemStack(leatherStrip, 4),
                            ANY_STRING, 4
                        });

        // Camel Pack (Quarter Full)
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.ENVIROMINE + ITEM_NAME_SEPARATOR + "camelPack", 0, 1,
                                                   "{camelPackFill: 25, camelPackMax: 100, isCamelPack: true}"),
                        new Object[]
                        {
                            "lll",
                            "lbl",
                            "lll",
                            'l',
                            new ItemStack(leather),
                            'b',
                            new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "potion"))
                        },
                        new Object[]
                        {
                            new ItemStack(leatherStrip, 4),
                            ANY_STRING, 4
                        });

        // Hard Hat
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "hardHat")), ANY_IRON_INGOT,
                        new Object[]
                        {
                            new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "redstone_lamp")),
                            "dyeYellow", 2,
                            new ItemStack(leatherStrip, 3),
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE)
                        });

        // Respirator
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "gasMask")),
                        new Object[]
                        {
                            "iii",
                            "igi",
                            "fif",
                            'i',
                            ANY_IRON_INGOT,
                            'g',
                            "paneGlassColorless",
                            'f',
                            new ItemStack(GameRegistry.findItem(ModIds.ENVIROMINE, "airFilter"))
                        },
                        new Object[]
                        {
                            new ItemStack(leatherStrip, 3),
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE)
                        });
    }

    private static void nerfFallingMeteorsArmor ()
    {
        // Meteorite
        ItemStack meteoriteIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "MeteoriteIngot"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "MetHelm", 0, 1,
                                                   "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                        meteoriteIngot, ANY_METEORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "MetBody", 0, 1,
                                                   "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                        meteoriteIngot, ANY_METEORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "MetLegs", 0, 1,
                                                   "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                        meteoriteIngot, ANY_METEORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "MetBoots", 0, 1,
                                                   "{ench: [{lvl: 1, id: 157}], enchant-set: true}"),
                        meteoriteIngot, ANY_METEORITE_NUGGET);

        // Frozen Iron
        ItemStack frozenIronIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "FrozenIron"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "FrezHelm", 0, 1,
                                                   "{ench: [{lvl: 3, id: 5}], enchant-set: true}"),
                        frozenIronIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "FrezBody", 0, 1,
                                                   "{ench: [{lvl: 1, id: 6}], enchant-set: true}"),
                        frozenIronIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "FrezLegs", 0, 1,
                                                   "{ench: [{lvl: 1, id: 158}], enchant-set: true}"),
                        frozenIronIngot, ANY_IRON_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "FrezBoots", 0, 1,
                                                   "{ench: [{lvl: 1, id: 158}], enchant-set: true}"),
                        frozenIronIngot, ANY_IRON_NUGGET);

        // Kreknorite
        ItemStack kreknoriteIngot = new ItemStack(GameRegistry.findItem(ModIds.FALLING_METEORS, "KreknoriteIngot"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "KrekHelm", 0, 1,
                                                   "{ench: [{lvl: 4, id: 1}], enchant-set: true}"),
                        kreknoriteIngot, ANY_KREKNORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "KrekBody", 0, 1,
                                                   "{ench: [{lvl: 4, id: 1}], enchant-set: true}"),
                        kreknoriteIngot, ANY_KREKNORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "KrekLegs", 0, 1,
                                                   "{ench: [{lvl: 4, id: 1}], enchant-set: true}"),
                        kreknoriteIngot, ANY_KREKNORITE_NUGGET);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.FALLING_METEORS + ITEM_NAME_SEPARATOR + "KrekBoots", 0, 1,
                                                   "{ench: [{lvl: 4, id: 1}], enchant-set: true}"),
                        kreknoriteIngot, ANY_KREKNORITE_NUGGET);
    }

    private static void nerfFSPArmor ()
    {
        // Gilded Iron
        ItemStack gildedGoldIngot = new ItemStack(GameRegistry.findItem(ModIds.FSP, "steamcraftIngot"), 1, 3);

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "helmGildedGold")), gildedGoldIngot,
                        ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "chestGildedGold")), gildedGoldIngot,
                        ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "legsGildedGold")), gildedGoldIngot,
                        ANY_IRON_NUGGET);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "feetGildedGold")), gildedGoldIngot,
                        ANY_IRON_NUGGET);

        // Steam Exo-Suit
        ItemStack brassPipe = new ItemStack(GameRegistry.findItem(ModIds.FSP, "pipe"));
        ItemStack brassPiston = new ItemStack(GameRegistry.findItem(ModIds.FSP, "steamcraftCrafting"), 1, 0);

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorHead")),
                        new Object[] {
                            "pip",
                            "t t",
                            "pip",
                            'p',
                            brassPipe,
                            'i',
                            ANY_BRASS_INGOT,
                            't',
                            brassPiston
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 5,
                            new ItemStack(leatherStrip, 3),
                            new ItemStack(woolBlock, 2, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorBody")),
                        new Object[] {
                            "t t",
                            "pip",
                            "iii",
                            't',
                            brassPiston,
                            'p',
                            brassPipe,
                            'i',
                            ANY_BRASS_INGOT
                        },
                        new Object[] {
                            new ItemStack(GameRegistry.findItem(ModIds.FSP, "meter"), 1),
                            ANY_BRASS_NUGGET, 8,
                            new ItemStack(leatherStrip, 2),
                            new ItemStack(woolBlock, 4, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorLegs")),
                        new Object[] {
                            "pip",
                            "t t",
                            "i i",
                            'p',
                            brassPipe,
                            't',
                            brassPiston,
                            'i',
                            ANY_BRASS_INGOT
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 8,
                            new ItemStack(leatherStrip, 4),
                            new ItemStack(woolBlock, 1, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorFeet")),
                        new Object[] {
                            "t t",
                            "i i",
                            't',
                            brassPiston,
                            'i',
                            ANY_BRASS_INGOT
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 4,
                            new ItemStack(leatherStrip, 2),
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorHead")),
                        new Object[] {
                            "pip",
                            "t t",
                            "pip",
                            'p',
                            brassPipe,
                            'i',
                            ANY_BRASS_PLATE,
                            't',
                            brassPiston
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 5,
                            new ItemStack(leatherStrip, 3),
                            new ItemStack(woolBlock, 2, OreDictionary.WILDCARD_VALUE)
                        }, true);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorBody")),
                        new Object[] {
                            "t t",
                            "pip",
                            "iii",
                            't',
                            brassPiston,
                            'p',
                            brassPipe,
                            'i',
                            ANY_BRASS_PLATE
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 8,
                            new ItemStack(leatherStrip, 2),
                            new ItemStack(woolBlock, 4, OreDictionary.WILDCARD_VALUE)
                        }, true);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorLegs")),
                        new Object[] {
                            "pip",
                            "t t",
                            "i i",
                            'p',
                            brassPipe,
                            't',
                            brassPiston,
                            'i',
                            ANY_BRASS_PLATE
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 8,
                            new ItemStack(leatherStrip, 4),
                            new ItemStack(woolBlock, 1, OreDictionary.WILDCARD_VALUE)
                        }, true);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.FSP, "exoArmorFeet")),
                        new Object[] {
                            "t t",
                            "i i",
                            't',
                            brassPiston,
                            'i',
                            ANY_BRASS_PLATE
                        },
                        new Object[] {
                            ANY_BRASS_NUGGET, 4,
                            new ItemStack(leatherStrip, 2),
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE)
                        }, true);
    }

    private static void nerfHarvestcraftArmor ()
    {
        ItemStack cardboardSheet = new ItemStack(GameRegistry.findItem(ModIds.HARVESTCRAFT, "hardenedleatherItem"));

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.HARVESTCRAFT, "hardenedleatherhelmItem")),
                        cardboardSheet);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.HARVESTCRAFT, "hardenedleatherchestItem")),
                        cardboardSheet);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.HARVESTCRAFT, "hardenedleatherleggingsItem")),
                        cardboardSheet);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.HARVESTCRAFT, "hardenedleatherbootsItem")),
                        cardboardSheet);
    }

    private static void nerfNaturaArmor ()
    {
        ItemStack impLeather = new ItemStack(naturaItem, 1, 6);

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.NATURA + ITEM_NAME_SEPARATOR + "natura.armor.imphelmet", 0, 1,
                                                   "{ench: [{lvl: 1, id: 0}, {lvl: 1, id: 1}]}"),
                        impLeather, new Object[]
                        {
                            new ItemStack(naturaItem, 3, 7),
                            new ItemStack(woolBlock, 2, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.NATURA + ITEM_NAME_SEPARATOR + "natura.armor.impjerkin", 0, 1,
                                                   "{ench: [{lvl: 1, id: 3}, {lvl: 1, id: 1}]}"),
                        impLeather, new Object[]
                        {
                            new ItemStack(naturaItem, 2, 7),
                            new ItemStack(woolBlock, 4, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.NATURA + ITEM_NAME_SEPARATOR + "natura.armor.impleggings", 0, 1,
                                                   "{ench: [{lvl: 1, id: 4}, {lvl: 1, id: 1}]}"),
                        impLeather, new Object[]
                        {
                            new ItemStack(naturaItem, 4, 7),
                            new ItemStack(woolBlock, 1, OreDictionary.WILDCARD_VALUE)
                        });
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.NATURA + ITEM_NAME_SEPARATOR + "natura.armor.impboots", 0, 1,
                                                   "{ench: [{lvl: 1, id: 2}, {lvl: 1, id: 1}]}"),
                        impLeather, new Object[]
                        {
                            new ItemStack(naturaItem, 2, 7),
                            new ItemStack(woolBlock, 3, OreDictionary.WILDCARD_VALUE),
                        });
    }

    private static void nerfPneumaticCraftArmor ()
    {
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.PNEUMATICCRAFT + ITEM_NAME_SEPARATOR + "pneumaticHelmet", 0, 1,
                                                   "{volume: 12000, UpgradeInventory: {}, air: 0}"),
                        new ItemStack(GameRegistry.findItem(ModIds.PNEUMATICCRAFT, "ingotIronCompressed")),
                        new Object[]
                        {
                            new ItemStack(GameRegistry.findItem(ModIds.PNEUMATICCRAFT, "airCanister"), 4, 30000),
                            new ItemStack(GameRegistry.findItem(ModIds.PNEUMATICCRAFT, "printedCircuitBoard"), 1),
                            new ItemStack(leatherStrip, 3),
                            new ItemStack(woolBlock, 2, OreDictionary.WILDCARD_VALUE)
                        });
    }

    private static void nerfRandomThingsArmor ()
    {
        Item randomThingsIngredient = GameRegistry.findItem(ModIds.RANDOM_THINGS, "ingredient");
        ItemStack spectreIngot = new ItemStack(randomThingsIngredient, 1, 4);

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.RANDOM_THINGS, "spectreHelmet")),
                        spectreIngot, new ItemStack(randomThingsIngredient, 2, 3));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.RANDOM_THINGS, "spectreChestplate")),
                        spectreIngot, new ItemStack(randomThingsIngredient, 6, 3));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.RANDOM_THINGS, "spectreLeggings")),
                        spectreIngot, new ItemStack(randomThingsIngredient, 4, 3));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.RANDOM_THINGS, "spectreBoots")),
                        spectreIngot, new ItemStack(randomThingsIngredient, 3, 3));
    }

    private static void nerfTconstructArmor ()
    {
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "helmetWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "chestplateWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "leggingsWood")), ANY_WOOD_LOG, ANY_STICK);
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TIC, "bootsWood")), ANY_WOOD_LOG, ANY_STICK);
    }

    private static void nerfTwilightforestArmor ()
    {
        // Ironwood
        ItemStack ironwoodIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.ironwoodIngot"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.ironwoodHelm", 0, 1,
                                                   "{ench: [{lvl: 1, id: 6}]}"),
                        ironwoodIngot);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.ironwoodPlate", 0, 1,
                                                   "{ench: [{lvl: 1, id: 0}]}"),
                        ironwoodIngot);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.ironwoodLegs", 0, 1,
                                                   "{ench: [{lvl: 1, id: 0}]}"),
                        ironwoodIngot);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.ironwoodBoots", 0, 1,
                                                   "{ench: [{lvl: 1, id: 2}]}"),
                        ironwoodIngot);

        // Steeleaf
        ItemStack steeleaf = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.steeleafIngot"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.steeleafHelm", 0, 1,
                                                   "{ench: [{lvl: 2, id: 4}]}"),
                        steeleaf);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.steeleafPlate", 0, 1,
                                                   "{ench: [{lvl: 2, id: 3}]}"),
                        steeleaf);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.steeleafLegs", 0, 1,
                                                   "{ench: [{lvl: 2, id: 1}]}"),
                        steeleaf);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.steeleafBoots", 0, 1,
                                                   "{ench: [{lvl: 2, id: 2}]}"),
                        steeleaf);

        // Naga
        ItemStack nagaScale = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.nagaScale"));

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.plateNaga", 0, 1,
                                                   "{ench: [{lvl: 3, id: 1}]}"),
                        nagaScale);
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.legsNaga", 0, 1,
                                                   "{ench: [{lvl: 3, id: 0}]}"),
                        nagaScale);

        // Fiery
        ItemStack fieryIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryIngot"));

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryHelm")),
                        fieryIngot, new Object[]
                        {
                            new ItemStack(naturaItem, 3, 7),
                            new ItemStack(naturaItem, 2, 6)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryPlate")),
                        fieryIngot, new Object[]
                        {
                            new ItemStack(naturaItem, 2, 7),
                            new ItemStack(naturaItem, 4, 6)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryLegs")),
                        fieryIngot, new Object[]
                        {
                            new ItemStack(naturaItem, 4, 7),
                            new ItemStack(naturaItem, 1, 6)
                        });
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.fieryBoots")),
                        fieryIngot, new Object[]
                        {
                            new ItemStack(naturaItem, 2, 7),
                            new ItemStack(naturaItem, 3, 6)
                        });

        // Knightmetal
        ItemStack knightmetalIngot = new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightMetal"));
        Item knightmetalShard = GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.armorShards");

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyHelm")),
                        knightmetalIngot, new ItemStack(knightmetalShard, 2));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyPlate")),
                        knightmetalIngot, new ItemStack(knightmetalShard, 6));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyLegs")),
                        knightmetalIngot, new ItemStack(knightmetalShard, 4));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.knightlyBoots")),
                        knightmetalIngot, new ItemStack(knightmetalShard, 3));

        ItemStack leatherStack = new ItemStack(leather);

        // Arctic
        Item arcticFur = GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.arcticFur");

        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.arcticHelm")),
                        leatherStack, new ItemStack(arcticFur, 2));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.arcticPlate")),
                        leatherStack, new ItemStack(arcticFur, 6));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.arcticLegs")),
                        leatherStack, new ItemStack(arcticFur, 4));
        nerfArmorRecipe(new ItemStack(GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.arcticBoots")),
                        leatherStack, new ItemStack(arcticFur, 3));

        // Alpha Yeti
        Item alphaYetiFur = GameRegistry.findItem(ModIds.TWILIGHT_FOREST, "item.alphaFur");

        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.yetiHelm", 0, 1,
                                                   "{ench: [{lvl: 2, id: 0}]}"),
                        leatherStack, new ItemStack(alphaYetiFur, 2));
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.yetiPlate", 0, 1,
                                                   "{ench: [{lvl: 2, id: 0}]}"),
                        leatherStack, new ItemStack(alphaYetiFur, 6));
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.yetiLegs", 0, 1,
                                                   "{ench: [{lvl: 2, id: 0}]}"),
                        leatherStack, new ItemStack(alphaYetiFur, 4));
        nerfArmorRecipe(GameRegistry.makeItemStack(ModIds.TWILIGHT_FOREST + ITEM_NAME_SEPARATOR + "item.yetiBoots", 0, 1,
                                                   "{ench: [{lvl: 2, id: 0}, {lvl: 4, id: 2}]}"),
                        leatherStack, new ItemStack(alphaYetiFur, 3));
    }

    private static void nerfArmorRecipe (final ItemStack outputStack, final Object craftingMaterial)
    {
        nerfArmorRecipe(outputStack, craftingMaterial, StringUtils.EMPTY);
    }

    private static void nerfArmorRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                         final Object additionalMaterials)
    {
        nerfArmorRecipe(outputStack, craftingMaterial, additionalMaterials, false);
    }

    private static void nerfArmorRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                         final Object additionalMaterials, final boolean forceCraft)
    {
        if (CraftingRecipeHandler.craftingRecipeExists(outputStack))
        {
            CraftingRecipeHandler.removeCraftingRecipes(outputStack);
        }

        if (!BetterBeginningsHandler.advCraftingRecipeExists(outputStack) || forceCraft)
        {
            BetterBeginningsHandler.addNerfedArmorRecipe(outputStack, craftingMaterial, additionalMaterials);
        }
    }
}
