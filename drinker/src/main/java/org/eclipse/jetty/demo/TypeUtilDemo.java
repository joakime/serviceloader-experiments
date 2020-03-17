package org.eclipse.jetty.demo;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtilDemo
{
    private static final Logger LOG = LoggerFactory.getLogger(Drinker.class);

    public static <T> T supplies(Supplier<T> supplier)
    {
        try
        {
            return supplier.get();
        }
        catch (Throwable e)
        {
            LOG.warn("Unable to get Service", e);
        }
        return null;
    }
}
