package org.eclipse.jetty.demo;

public class FancyBar implements BarService
{
    @Override
    public String getType()
    {
        return "Fancy";
    }
}
