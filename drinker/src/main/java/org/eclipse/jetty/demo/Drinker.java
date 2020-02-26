package org.eclipse.jetty.demo;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class Drinker
{
    public static void main(String[] args)
    {
        ServiceLoader.load(BarService.class).stream().forEach((barProvider) ->
        {
            try
            {
                BarService service = barProvider.get();
                if (service == null)
                {
                    System.out.println("No service found.");
                }
                System.out.printf("Service (%s) is type %s%n", service.getClass().getName(), service.getType());
            }
            catch (ServiceConfigurationError error)
            {
                System.out.printf("Service failed to load: %s%n", error.getMessage());
                error.getCause().printStackTrace(System.out);
            }
        });
    }
}
