package com.undeadzeratul.twbbtweaks.proxy;

import com.undeadzeratul.twbbtweaks.handler.ConfigurationHandler;

import cpw.mods.fml.common.FMLCommonHandler;

public abstract class CommonProxy implements IProxy
{
    @Override
    public void registerEventHandlers ()
    {
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
    }
}
