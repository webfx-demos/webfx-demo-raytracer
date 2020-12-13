package dev.webfx.demo.raytracer.webworker.teavm;

import dev.webfx.demo.raytracer.webworker.RayTracerWebWorker;
import dev.webfx.platform.teavm.services.webworker.spi.impl.TeaVmRunningWebWorker;

/**
 * @author Bruno Salmon
 */
public class TeaVmRayTracerWorkerEntryPoint {

    public static void main(String[] args) {
        TeaVmRunningWebWorker.executeJavaCodedWorker(new RayTracerWebWorker());
    }
}
