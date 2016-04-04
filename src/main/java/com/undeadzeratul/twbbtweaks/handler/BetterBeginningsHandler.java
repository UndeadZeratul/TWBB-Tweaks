package com.undeadzeratul.twbbtweaks.handler;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;

import cpw.mods.fml.common.registry.GameRegistry;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfirePanRecipes;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipes;
import net.einsteinsci.betterbeginnings.register.recipe.IBrickOvenRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipes;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.oredict.OreDictionary;

public class BetterBeginningsHandler
{
    private static AdvancedCraftingHandler advCraftingInstance;
    private static BrickOvenRecipeHandler  brickOvenRecipeInstance;
    private static CampfireRecipes         campfireRecipeInstance;
    private static CampfirePanRecipes      campfirePanRecipeInstance;
    private static KilnRecipes             kilnRecipeInstance;
    private static SmelterRecipeHandler    smelterRecipeInstance;

    public static void init ()
    {
        advCraftingInstance = AdvancedCraftingHandler.crafting();
        brickOvenRecipeInstance = BrickOvenRecipeHandler.instance();
        campfireRecipeInstance = CampfireRecipes.smelting();
        campfirePanRecipeInstance = CampfirePanRecipes.smelting();
        kilnRecipeInstance = KilnRecipes.smelting();
        smelterRecipeInstance = SmelterRecipeHandler.smelting();
    }

    public static boolean advCraftingRecipeExists (final Item item)
    {
        for (AdvancedRecipe recipe : advCraftingInstance.getRecipeList())
        {
            if (StringUtils.equalsIgnoreCase(recipe.getRecipeOutput().getItem().getUnlocalizedName(),
                                             item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean advCraftingRecipeExists (final ItemStack itemStack)
    {
        return advCraftingRecipeExists(itemStack.getItem());
    }

    public static boolean brickOvenRecipeExists (final Item item)
    {
        for (IBrickOvenRecipe recipe : brickOvenRecipeInstance.getRecipeList())
        {
            if (StringUtils.equalsIgnoreCase(recipe.getRecipeOutput().getItem().getUnlocalizedName(),
                                             item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean brickOvenRecipeExists (final ItemStack itemStack)
    {
        return brickOvenRecipeExists(itemStack.getItem());
    }

    public static boolean campfireRecipeExists (final Item item)
    {
        for (ItemStack output : (Collection<ItemStack>) campfireRecipeInstance.getSmeltingList().values())
        {
            if (StringUtils.equalsIgnoreCase(output.getItem().getUnlocalizedName(),
                                             item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean campfireRecipeExists (final ItemStack itemStack)
    {
        return campfireRecipeExists(itemStack.getItem());
    }

    public static boolean kilnRecipeExists (final Item item)
    {
        for (ItemStack output : (Collection<ItemStack>) kilnRecipeInstance.getSmeltingList().values())
        {
            if (StringUtils.equalsIgnoreCase(output.getItem().getUnlocalizedName(), item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean kilnRecipeExists (final ItemStack itemStack)
    {
        return kilnRecipeExists(itemStack.getItem());
    }

    public static boolean smelterRecipeExists (final Item item)
    {
        for (SmelterRecipe recipe : smelterRecipeInstance.getRecipes())
        {
            if (StringUtils.equalsIgnoreCase(recipe.getOutput().getItem().getUnlocalizedName(),
                                             item.getUnlocalizedName()))
            {
                return true;
            }
        }

        return false;
    }

    public static boolean smelterRecipeExists (final ItemStack itemStack)
    {
        return smelterRecipeExists(itemStack.getItem());
    }

    public static String getArmorRepairMaterial (final ItemArmor armor)
    {
        for (String oreDictName : OreDictionary.getOreNames())
        {
            if (!Strings.isNullOrEmpty(oreDictName))
            {
                for (ItemStack ore : OreDictionary.getOres(oreDictName))
                {
                    if (armor.getIsRepairable(new ItemStack(armor), ore))
                    {
                        return oreDictName.startsWith("ingot")
                                ? oreDictName.substring(5)
                                : oreDictName;
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    public static void addNerfedArmorRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                             final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null &&
            outputStack.getItem() instanceof ItemArmor)
        {
            ItemArmor armor = (ItemArmor) outputStack.getItem();

            addNerfedCraftingRecipe(outputStack,
                            buildArmorCraftingPattern(armor, craftingMaterial),
                            buildAdditionalArmorMaterials(armor, extraMaterial));
        }
    }

    private static Object[] buildAdditionalArmorMaterials (final ItemArmor output, final Object extraMaterial)
    {
        if (extraMaterial instanceof Object[])
        {
            return (Object[]) extraMaterial;
        }

        AdditionalCosts additionalCosts = getAdditionalCosts(output);

        ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), additionalCosts.itemStack2);
        ItemStack woolBlocks = new ItemStack(GameRegistry.findItem(ModIds.MINECRAFT, "wool"), additionalCosts.itemStack3, OreDictionary.WILDCARD_VALUE);

        return extraMaterial instanceof String
                ? !Strings.isNullOrEmpty((String) extraMaterial)
                        ? new Object[]
                        {
                            extraMaterial, additionalCosts.itemStack1,
                            leatherStrips,
                            woolBlocks
                        }
                        : new Object[]
                        {
                            leatherStrips,
                            woolBlocks
                        }
                : new Object[]
                {
                    extraMaterial,
                    leatherStrips,
                    woolBlocks
                };
    }

    private static Object[] buildArmorCraftingPattern (final ItemArmor output, final Object craftingMaterial)
    {
        if (craftingMaterial instanceof Object[])
        {
            return (Object[]) craftingMaterial;
        }

        switch (output.armorType)
        {
            case 0:
                return new Object[]
                {
                    "iii",
                    "i i",
                    'i',
                    craftingMaterial
                };
            case 1:
                return new Object[]
                {
                    "i i",
                    "iii",
                    "iii",
                    'i',
                    craftingMaterial
                };
            case 2:
                return new Object[]
                {
                    "iii",
                    "i i",
                    "i i",
                    'i',
                    craftingMaterial
                };
            case 3:
                return new Object[]
                {
                    "i i",
                    "i i",
                    'i',
                    craftingMaterial
                };
            default:
                return new Object[0];
        }
    }

    public static String getToolRepairMaterial (final Item tool)
    {
        for (String oreDictName : OreDictionary.getOreNames())
        {
            if (!Strings.isNullOrEmpty(oreDictName))
            {
                for (ItemStack ore : OreDictionary.getOres(oreDictName))
                {
                    if (tool instanceof ItemTool && ((ItemTool) tool).getIsRepairable(new ItemStack(tool), ore))
                    {
                        return oreDictName;
                    }
                    else if (tool instanceof ItemSword && ((ItemSword) tool).getIsRepairable(new ItemStack(tool), ore))
                    {
                        return oreDictName;
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    public static void addNerfedToolRecipe (final ItemStack outputStack, final Object craftingMaterial,
                                            final Object handleMaterial, final Object extraMaterial)
    {
        if (craftingMaterial != null && extraMaterial != null && outputStack != null)
        {
            addNerfedCraftingRecipe(outputStack,
                            buildToolCraftingPattern(outputStack.getItem(), craftingMaterial, handleMaterial),
                            buildAdditionalToolMaterials(outputStack.getItem(), extraMaterial));
        }
    }

    private static Object[] buildAdditionalToolMaterials (final Item output, final Object extraMaterial)
    {
        if (extraMaterial instanceof Object[])
        {
            return (Object[]) extraMaterial;
        }

        AdditionalCosts additionalCosts = getAdditionalCosts(output);

        ItemStack leatherStrips = new ItemStack(GameRegistry.findItem(ModIds.BETTER_BEGINNINGS, "leatherStrip"), additionalCosts.itemStack1);

        return extraMaterial instanceof String
                ? !Strings.isNullOrEmpty((String) extraMaterial)
                        ? new Object[]
                        {
                            extraMaterial, additionalCosts.itemStack1
                        }
                        : new Object[]
                        {
                            leatherStrips
                        }
                : new Object[]
                {
                    extraMaterial
                };
    }

    private static Object[] buildToolCraftingPattern (final Item output, final Object craftingMaterial,
                                                      final Object handleMaterial)
    {
        Object[] ret = new Object[0];

        if (craftingMaterial instanceof Object[])
        {
            ret = (Object[]) craftingMaterial;
        }
        else if (output instanceof ItemPickaxe)
        {
            ret = new Object[]
            {
                "iii",
                " s ",
                " s ",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemSword)
        {
            ret = new Object[]
            {
                "i",
                "i",
                "s",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemAxe)
        {
            ret = new Object[]
            {
                "ii",
                "is",
                " s",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemSpade)
        {
            ret = new Object[]
            {
                "i",
                "s",
                "s",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemHoe)
        {
            ret = new Object[]
            {
                "ii",
                " s",
                " s",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemBow)
        {
            ret = new Object[]
            {
                " is",
                "i s",
                " is",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }
        else if (output instanceof ItemShears)
        {
            ret = new Object[]
            {
                " i",
                "i ",
                'i',
                craftingMaterial,
            };
        }
        else if (output instanceof ItemFishingRod)
        {
            ret = new Object[]
            {
                "  i",
                " is",
                "i s",
                'i',
                craftingMaterial,
                's',
                handleMaterial
            };
        }

        return ret;
    }

    private static AdditionalCosts getAdditionalCosts (final Item output)
    {
        int index = 0;

        if (output instanceof ItemArmor)
        {
            index = ((ItemArmor) output).armorType;
        }
        else if (output instanceof ItemPickaxe)
        {
            index = 4;
        }
        else if (output instanceof ItemSword)
        {
            index = 5;
        }
        else if (output instanceof ItemAxe)
        {
            index = 6;
        }
        else if (output instanceof ItemSpade)
        {
            index = 7;
        }
        else if (output instanceof ItemHoe)
        {
            index = 8;
        }
        else if (output instanceof ItemBow)
        {
            index = 9;
        }
        else if (output instanceof ItemShears)
        {
            index = 10;
        }
        else if (output instanceof ItemFishingRod)
        {
            index = 11;
        }

        return AdditionalCosts.values()[index];
    }

    private enum AdditionalCosts
    {
        HELMET(2, 3, 2),
        CHESTPLATE(6, 2, 4),
        LEGGINGS(4, 4, 1),
        BOOTS(3, 2, 3),
        PICKAXE(3),
        SWORD(2),
        AXE(2),
        SHOVEL(2),
        HOE(1),
        BOW(3),
        SHEARS(1),
        FISHING_ROD(1);

        private int itemStack1 = 0;
        private int itemStack2 = 0;
        private int itemStack3 = 0;

        AdditionalCosts (final int itemStack1)
        {
            this(itemStack1, 0);
        }

        AdditionalCosts (final int itemStack1, final int itemStack2)
        {
            this(itemStack1, itemStack2, 0);
        }

        AdditionalCosts (final int itemStack1, final int itemStack2, final int itemStack3)
        {
            this.itemStack1 = itemStack1;
            this.itemStack2 = itemStack2;
            this.itemStack3 = itemStack3;
        }
    }

    public static void addNerfedCampfireRecipe (final ItemStack inputStack, final ItemStack outputStack,
                                                final float experience)
    {
        if (inputStack != null && outputStack != null)
        {
            campfireRecipeInstance.addRecipe(inputStack, outputStack, experience);
        }
    }

    public static void addNerfedCampfirePanRecipe (final ItemStack inputStack, final ItemStack outputStack,
                                                   final float experience)
    {
        if (inputStack != null && outputStack != null)
        {
            campfirePanRecipeInstance.addRecipe(inputStack, outputStack, experience);
        }
    }

    public static void addNerfedRecipe (final ItemStack outputStack, final Object[] craftingPattern,
                                        final Object[] additionalMaterials)
    {
        advCraftingInstance.addAdvancedRecipe(outputStack, additionalMaterials, craftingPattern);
    }

    public static void removeNerfedCraftingRecipes (final ItemStack outputStack)
    {
        Iterator<AdvancedRecipe> iter = advCraftingInstance.getRecipeList().iterator();

        while (iter.hasNext())
        {
            AdvancedRecipe recipe = iter.next();

            if (recipe.getRecipeOutput().isItemEqual(outputStack))
            {
                iter.remove();
            }
        }
    }
}
