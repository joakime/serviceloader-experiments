package org.eclipse.jetty.demo;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Drinker
{
    private static final Logger LOG = LoggerFactory.getLogger(Drinker.class);

    public static void main(String[] args)
    {
        // == Works : sees all potential services
        demoOldIteration();

        // == Fails at first ServiceConfigurationError
        // demoIterateServicesSmaller();

        // == Fails at first lazy init on hasNext
        // demoSelectService();
        // demoIterateServices();

        // == Doesn't work after first failure
        // demoSelectServiceIgnoringErrors();
    }

    private static void demoSelectServiceIgnoringErrors()
    {
        Optional<BarService> service = ServiceLoader.load(BarService.class).stream()
            .map((provider) ->
            {
                // attempt to load service
                try
                {
                    // will either return a service, throw an error, or return null
                    return provider.get();
                }
                catch (ServiceConfigurationError error)
                {
                    LOG.warn("BarService failed to load", error);
                }
                return null;
            })
            .filter((bar) -> bar != null) // filter out empty / error services
            .findFirst();

        BarService barService = service.orElseThrow(() -> new IllegalStateException("Unable to find a BarService"));
        LOG.info("Using Service ({}) is type [{}]", barService.getClass().getName(), barService.getType());
    }

    private static void demoSelectService()
    {
        List<BarService> barServiceList = ServiceLoader.load(BarService.class).stream()
            .map(TypeUtilDemo::supplies)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        barServiceList.forEach(barService ->
            LOG.info("Using Service ({}) is type [{}]", barService.getClass().getName(), barService.getType())
        );
    }

    private static void demoIterateServices()
    {
        ServiceLoader.load(BarService.class).stream().forEach((barProvider) ->
        {
            try
            {
                LOG.info("Provider [{}] - Provider.type() = {}", barProvider, barProvider.type());
                BarService barService = barProvider.get();
                if (barService == null)
                {
                    LOG.warn("BarService returned null");
                }
                else
                {
                    LOG.info("Using Service ({}) is type [{}]", barService.getClass().getName(), barService.getType());
                }
            }
            catch (ServiceConfigurationError error)
            {
                LOG.warn("BarService failed to load", error);
            }
        });
    }

    private static void demoIterateServicesSmaller()
    {
        ServiceLoader.load(BarService.class)
            .stream().map(ServiceLoader.Provider::get)
            .forEach(service -> LOG.info("Using Service ({}) is type [{}]", service.getClass().getName(), service.getType()));
    }

    private static void demoOldIteration()
    {
        ServiceLoader serviceLoader = ServiceLoader.load(BarService.class);
        Iterator<BarService> serviceIterator = serviceLoader.iterator();
        while (serviceIterator.hasNext())
        {
            try
            {
                BarService barService = serviceIterator.next();
                if (barService == null)
                {
                    LOG.warn("BarService returned null");
                }
                else
                {
                    LOG.info("Using Service ({}) is type [{}]", barService.getClass().getName(), barService.getType());
                }
            }
            catch (ServiceConfigurationError error)
            {
                LOG.warn("BarService failed to load", error);
            }
        }
    }
}
