// File managed by WebFX (DO NOT EDIT MANUALLY)
package dev.webfx.platform.resource.j2cl;

import org.treblereel.j2cl.processors.annotations.GWT3Resource;
import org.treblereel.j2cl.processors.common.resources.ClientBundle;
import org.treblereel.j2cl.processors.common.resources.TextResource;
import dev.webfx.platform.resource.spi.impl.j2cl.J2clResourceBundleBase;

@GWT3Resource
public interface J2clEmbedResourcesBundle extends ClientBundle {

    J2clEmbedResourcesBundle R = J2clEmbedResourcesBundleImpl.INSTANCE;

    @Source("/dev/webfx/platform/meta/exe/exe.properties")
    TextResource r1();

    final class ProvidedJ2clResourceBundle extends J2clResourceBundleBase {
        public ProvidedJ2clResourceBundle() {
            registerResource("dev/webfx/platform/meta/exe/exe.properties", () -> R.r1().getText());
        }
    }
}