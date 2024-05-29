// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.demo.raytracer.application {

    // Direct dependencies modules
    requires javafx.graphics;
    requires webfx.demo.raytracer.math;
    requires webfx.demo.raytracer.webworker;
    requires webfx.kit.util.scene;
    requires webfx.lib.tracerframework;
    requires webfx.platform.ast;

    // Exported packages
    exports dev.webfx.demo.raytracer;

    // Provided services
    provides javafx.application.Application with dev.webfx.demo.raytracer.RayTracerApplication;

}