package dev.webfx.demo.raytracer.webworker;

import dev.webfx.demo.raytracer.math.*;
import dev.webfx.stack.platform.webassembly.WebAssembly;
import dev.webfx.stack.platform.webassembly.WebAssemblyInstance;
import dev.webfx.stack.platform.webassembly.WebAssemblyMemoryBufferReader;
import dev.webfx.stack.platform.json.Json;
import dev.webfx.stack.platform.json.JsonObject;
import dev.webfx.stack.platform.webworker.spi.base.JavaCodedWebWorkerBase;
import dev.webfx.stack.async.Future;

/**
 * @author Bruno Salmon
 */
public class RayTracerWebWorker extends JavaCodedWebWorkerBase {
    // These fields will store the input parameters passed by the JS UI through JSON
    private int width, height, placeIndex, frameIndex;
    // Mandelbrot view port (actually used only when computing here in JS, not when calling WebAssembly)
    private byte[] pixelRGBs;
    // Fields for WebAssembly management
    private boolean usingWebAssembly;
    private WebAssemblyInstance webAssemblyInstance;
    private WebAssemblyMemoryBufferReader outputBufferReader;
    private boolean webAssemblyInstanceInited;

    @Override
    public void onLoaded() {
        if (WebAssembly.isSupported()) {
            Future<WebAssemblyInstance> future = WebAssembly.loadAndInstantiate("classes.wasm");
            future.onComplete(ar -> {
                webAssemblyInstance = ar.result();
                outputBufferReader = webAssemblyInstance.getDataReader(0);
            });
        }
        setOnMessageHandler(data -> {
            JsonObject json = Json.createObject(data);
            int cy = json.getInteger("cy", 0); // TODO: fix bug returning null for 0 value in TeaVM implementation
            Integer iWidth = json.getInteger("width"); // TODO: fix json.has() not compiling with TeaVM
            boolean initFrame = iWidth != null;
            if (initFrame) {
                width = json.getInteger("width");
                height = json.getInteger("height");
                placeIndex = json.getInteger("placeIndex", 0);
                frameIndex = json.getInteger("frameIndex", 0);
                usingWebAssembly = json.isTrue("wasm");
            }
            if (usingWebAssembly && webAssemblyInstance != null) {
                Object result;
                if (initFrame || !webAssemblyInstanceInited) {
                    result = webAssemblyInstance.call("initAndComputeLinePixelRGBs", cy, width, height, placeIndex, frameIndex);
                    webAssemblyInstanceInited = true;
                } else
                    result = webAssemblyInstance.call("computeLinePixelRGBs", cy);
                int outputBufferOffset = ((Number) result).intValue();
                //System.out.println("outputBufferOffset = " + outputBufferOffset);
                outputBufferReader.setMemoryBufferOffset(outputBufferOffset);
                pixelRGBs = outputBufferReader.readByteArray(3 * width);
            } else {
                if (initFrame)
                    RayTracerMath.init(width, height, placeIndex, frameIndex);
                pixelRGBs = RayTracerMath.createLinePixelResultStorage(width, null);
                RayTracerMath.computeLinePixelRGBs(cy, pixelRGBs);
            }
            //System.out.println("pixelRGBs.length = " + pixelRGBs.length);
            postMessage(toNativeJsonArray(pixelRGBs));
            //System.out.println("Posted :-)");
        });
    }
}
