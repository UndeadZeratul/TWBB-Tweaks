package com.undeadzeratul.twbbtweaks.api;

import com.google.gson.JsonObject;

public interface ITweaksParser
{
    public ITweak parseTweak(String tweakName, JsonObject genObject);
}
