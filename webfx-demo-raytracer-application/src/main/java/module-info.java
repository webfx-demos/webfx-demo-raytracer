// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.demo.raytracer.application {

    // Direct dependencies modules
    requires javafx.graphics;
    requires webfx.demo.mandelbrot.tracerframework;
    requires webfx.demo.raytracer.math;
    requires webfx.demo.raytracer.webworker;
    requires webfx.platform.shared.json;

    // Exported packages
    exports dev.webfx.demo.raytracer;

    // Provided services
    provides javafx.application.Application with dev.webfx.demo.raytracer.RayTracerApplication;

}