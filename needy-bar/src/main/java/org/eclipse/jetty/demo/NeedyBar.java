package org.eclipse.jetty.demo;

public class NeedyBar implements BarService
{
    private final String owner;

    // Intentionally have a constructor with parameter and no default constructor
    public NeedyBar(String owner)
    {
        this.owner = owner;
    }

    @Override
    public String getType()
    {
        return "Needy (owned by " + owner + ")";
    }
}
