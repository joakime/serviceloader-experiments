package org.eclipse.jetty.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DependentBar implements BarService
{
    private static final Log LOG = LogFactory.getLog(DependentBar.class);

    public DependentBar()
    {
        LOG.info("Constructed DependentBar");
    }

    @Override
    public String getType()
    {
        return "Dependent";
    }
}
