package com.undeadzeratul.twbbtweaks.tweaks.wrapper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MeltingRecipeWrapper
{
    private ItemStack  input;
    private FluidStack output;
    private int        meltingPoint;
    private ItemStack  block;

    public ItemStack getInput ()
    {
        return input;
    }

    public void setInput (final ItemStack input)
    {
        this.input = input;
    }

    public FluidStack getOutput ()
    {
        return output;
    }

    public void setOutput (final FluidStack output)
    {
        this.output = output;
    }

    public int getMeltingPoint ()
    {
        return meltingPoint;
    }

    public void setMeltingPoint (final int meltingPoint)
    {
        this.meltingPoint = meltingPoint;
    }

    public ItemStack getBlock ()
    {
        return block;
    }

    public void setBlock (final ItemStack block)
    {
        this.block = block;
    }
}
