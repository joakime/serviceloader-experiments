package org.eclipse.jetty.demo;

public class LostBar implements BarService
{
    public LostBar()
    {
        throw new IllegalStateException("This Bar is Closed (lost our license)");
    }

    @Override
    public String getType()
    {
        return "Lost";
    }
}
