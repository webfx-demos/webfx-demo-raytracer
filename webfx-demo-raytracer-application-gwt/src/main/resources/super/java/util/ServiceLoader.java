// File managed by WebFX (DO NOT EDIT MANUALLY)
package java.util;

import java.util.Iterator;
import java.util.logging.Logger;
import dev.webfx.platform.util.function.Factory;

public class ServiceLoader<S> implements Iterable<S> {

    public static <S> ServiceLoader<S> load(Class<S> serviceClass) {
        switch (serviceClass.getName()) {
            case "dev.webfx.kit.launcher.spi.WebFxKitLauncherProvider": return new ServiceLoader<S>(dev.webfx.kit.launcher.spi.impl.gwt.GwtWebFxKitLauncherProvider::new);
            case "dev.webfx.kit.mapper.spi.WebFxKitMapperProvider": return new ServiceLoader<S>(dev.webfx.kit.mapper.spi.impl.gwt.GwtWebFxKitHtmlMapperProvider::new);
            case "dev.webfx.platform.arch.spi.ArchProvider": return new ServiceLoader<S>(dev.webfx.platform.arch.spi.impl.gwt.GwtArchProvider::new);
            case "dev.webfx.platform.boot.spi.ApplicationBooterProvider": return new ServiceLoader<S>(dev.webfx.platform.boot.spi.impl.gwt.GwtApplicationBooterProvider::new);
            case "dev.webfx.platform.boot.spi.ApplicationJob": return new ServiceLoader<S>();
            case "dev.webfx.platform.boot.spi.ApplicationModuleBooter": return new ServiceLoader<S>(dev.webfx.kit.launcher.WebFxKitLauncherModuleBooter::new, dev.webfx.platform.boot.spi.impl.ApplicationJobsBooter::new, dev.webfx.platform.resource.spi.impl.gwt.GwtResourceModuleBooter::new);
            case "dev.webfx.platform.console.spi.ConsoleProvider": return new ServiceLoader<S>(dev.webfx.platform.console.spi.impl.gwt.GwtConsoleProvider::new);
            case "dev.webfx.platform.json.spi.JsonProvider": return new ServiceLoader<S>(dev.webfx.platform.json.spi.impl.gwt.GwtJsonObject::create);
            case "dev.webfx.platform.os.spi.OperatingSystemProvider": return new ServiceLoader<S>(dev.webfx.platform.os.spi.impl.gwt.GwtOperatingSystemProvider::new);
            case "dev.webfx.platform.resource.spi.ResourceProvider": return new ServiceLoader<S>(dev.webfx.platform.resource.spi.impl.gwt.GwtResourceProvider::new);
            case "dev.webfx.platform.resource.spi.impl.gwt.GwtResourceBundle": return new ServiceLoader<S>(webfx.demo.raytracer.application.gwt.embed.EmbedResourcesBundle.ProvidedGwtResourceBundle::new);
            case "dev.webfx.platform.scheduler.spi.SchedulerProvider": return new ServiceLoader<S>(dev.webfx.platform.uischeduler.spi.impl.gwt.GwtUiSchedulerProvider::new);
            case "dev.webfx.platform.shutdown.spi.ShutdownProvider": return new ServiceLoader<S>(dev.webfx.platform.shutdown.spi.impl.gwt.GwtShutdownProvider::new);
            case "dev.webfx.platform.uischeduler.spi.UiSchedulerProvider": return new ServiceLoader<S>(dev.webfx.platform.uischeduler.spi.impl.gwt.GwtUiSchedulerProvider::new);
            case "dev.webfx.platform.useragent.spi.UserAgentProvider": return new ServiceLoader<S>(dev.webfx.platform.useragent.spi.impl.gwt.GwtUserAgentProvider::new);
            case "dev.webfx.platform.util.keyobject.formatter.spi.AstFormatterProvider": return new ServiceLoader<S>(dev.webfx.platform.json.tree.JsonTreeFormatterProvider::new);
            case "dev.webfx.platform.util.keyobject.parser.spi.AstParserProvider": return new ServiceLoader<S>(dev.webfx.platform.json.tree.JsonAstParserProvider::new);
            case "dev.webfx.platform.webworker.spi.WorkerServiceProvider": return new ServiceLoader<S>(dev.webfx.platform.webworker.spi.impl.gwt.GwtWorkerServiceProvider::new);
            case "javafx.application.Application": return new ServiceLoader<S>(dev.webfx.demo.raytracer.RayTracerApplication::new);

            // UNKNOWN SPI
            default:
                Logger.getLogger(ServiceLoader.class.getName()).warning("Unknown " + serviceClass + " SPI - returning no provider");
                return new ServiceLoader<S>();
        }
    }

    private final Factory[] factories;

    public ServiceLoader(Factory... factories) {
        this.factories = factories;
    }

    public Iterator<S> iterator() {
        return new Iterator<S>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < factories.length;
            }

            @Override
            public S next() {
                return (S) factories[index++].create();
            }
        };
    }
}