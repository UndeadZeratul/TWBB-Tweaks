package com.undeadzeratul.twbbtweaks.handler;

import java.util.Map.Entry;

import mantle.utils.ItemMetaWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.crafting.Smeltery;
import toops.tsteelworks.api.highoven.ISmeltingRegistry;

public class TSteelworksHandler
{
    private static ISmeltingRegistry smeltingRegistry;

    public static void init ()
    {
        smeltingRegistry = ISmeltingRegistry.INSTANCE;
    }

    public static void setMeltingTemp (final String fluidName, final int newTemp)
    {
        // If either the input or new temperature are null or invalid, back out.
        if (fluidName == null || newTemp <= 0)
        {
            return;
        }

        // Otherwise, find matching recipe and adjust its melting temperature.
        for (Entry<ItemMetaWrapper, FluidStack> meltingEntry : Smeltery.instance.getSmeltingList().entrySet())
        {
            // FluidStack's getUnlocalizedName method sticks "fluid." before the actual name,
            // so I guess we'll be doing the same thing here
            String prefixedFluidName = !fluidName.startsWith("fluid.")
                    ? "fluid." + fluidName
                    : fluidName;

            if (meltingEntry.getValue().getUnlocalizedName().equals(prefixedFluidName))
            {
                ItemMetaWrapper inputWrapper = meltingEntry.getKey();
                ItemStack inputStack = new ItemStack(inputWrapper.item, 1, inputWrapper.meta);

                if (inputStack != null)
                {
                    smeltingRegistry.addMeltable(inputStack, isOre(inputStack), meltingEntry.getValue(), newTemp);
                }
            }
        }
    }

    private static boolean isOre (final ItemStack inputStack)
    {
        for (String oreName : OreDictionary.getOreNames())
        {
            if (oreName.startsWith("ore"))
            {
                for (ItemStack oreStack : OreDictionary.getOres(oreName))
                {
                    if (inputStack.getItem().getUnlocalizedName().equals(oreStack.getItem().getUnlocalizedName()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
