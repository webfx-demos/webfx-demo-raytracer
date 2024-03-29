package dev.webfx.demo.raytracer;

import dev.webfx.platform.json.JsonArray;
import dev.webfx.platform.json.JsonObject;
import javafx.scene.paint.Color;
import dev.webfx.lib.tracerframework.PixelComputer;
import dev.webfx.lib.tracerframework.TracerThumbnail;
import dev.webfx.lib.tracerframework.TracerEngine;
import dev.webfx.demo.raytracer.math.RayTracerMath;
import dev.webfx.demo.raytracer.math.Views;
import dev.webfx.demo.raytracer.webworker.RayTracerWebWorker;
import dev.webfx.platform.json.Json;
import dev.webfx.platform.json.ReadOnlyJsonObject;

/**
 * @author Bruno Salmon
 */
final class RayTracerPixelComputer implements PixelComputer {

    private int width, height;
    private int placeIndex, frameIndex, thumbnailPlaceIndex = -1;
    private boolean usingWebAssembly = false;

    RayTracerPixelComputer() {
    }

    private RayTracerPixelComputer(int thumbnailPlaceIndex) {
        this.thumbnailPlaceIndex = thumbnailPlaceIndex;
    }

    @Override
    public int getPlacesCount() {
        return Views.VIEWS.length;
    }

    @Override
    public int getFramesCount(int placeIndex) {
        return 200;
    }

    @Override
    public TracerThumbnail getPlaceThumbnail(int placeIndex) {
        TracerThumbnail placeThumbnail = new TracerThumbnail();
        placeThumbnail.setThumbnailTracer(new TracerEngine(placeThumbnail.getCanvas(), new RayTracerPixelComputer(placeIndex)));
        return placeThumbnail;
    }

    @Override
    public void setUsingWebAssembly(boolean usingWebAssembly) {
        this.usingWebAssembly = usingWebAssembly;
    }

    @Override
    public boolean isUsingWebAssembly() {
        return usingWebAssembly;
    }

    @Override
    public long getLastFrameIterations() {
        return 0;
    }

    @Override
    public void initFrame(int canvasWidth, int canvasHeight, int placeIndex, int frameIndex) {
        width = canvasWidth;
        height = canvasHeight;
        if (thumbnailPlaceIndex >= 0) {
            placeIndex = thumbnailPlaceIndex;
        }
        this.placeIndex = placeIndex;
        this.frameIndex = frameIndex;
        RayTracerMath.init(width, height, placeIndex, frameIndex);
    }

    @Override
    public void endFrame() {
    }

    @Override
    public Color getPixelResultColor(int x, int y, Object linePixelResultStorage) {
        byte[] rgbs = (byte[]) linePixelResultStorage;
        int index = 3 * x;
        return Color.rgb(
                rgbs[index++] - Byte.MIN_VALUE,
                rgbs[index++] - Byte.MIN_VALUE,
                rgbs[index] - Byte.MIN_VALUE);
    }

    @Override
    public Object createLinePixelResultStorage() {
        return RayTracerMath.createLinePixelResultStorage(width, null);
    }

    @Override
    public void computeAndStorePixelResult(int x, int y, Object linePixelResultStorage) {
        byte[] rgbs = (byte[]) linePixelResultStorage;
        RayTracerMath.computeAndStorePixelResult(x, y, rgbs);
    }

    @Override
    public Class<RayTracerWebWorker> getWorkerClass() {
        return RayTracerWebWorker.class;
    }

    @Override
    public ReadOnlyJsonObject getLineWorkerParameters(int y, boolean firstWorkerCall) {
        JsonObject json = Json.createObject().set("cy", y);
        if (firstWorkerCall) {
            json.set("width", width);
            json.set("height", height);
            json.set("wasm", usingWebAssembly);
            json.set("placeIndex", placeIndex);
            json.set("frameIndex", frameIndex);
        }
        return json;
    }

    @Override
    public Object getLinePixelResultStorage(Object workerResult) {
        //webfx.platform.shared.services.log.Logger.log("Received " + workerResult);
        byte[] values;
        if (workerResult instanceof byte[])
            values = (byte[]) workerResult;
        else {
            JsonArray array = Json.createArray(workerResult);
            int n = array.size();
            values = new byte[n];
            for (int i = 0; i < n; i++)
                values[i] = array.getInteger(i).byteValue();
        }
        //webfx.platform.shared.services.log.Logger.log("Converted to byte[] :-)");
        return values;
    }
}
