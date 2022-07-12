# Live demo

The demo is published on [this page][demo-live-link].

A demo very similar to the [Mandelbrot demo][demo-mandelbrot-repo] but with ray tracing, which requires even more computation resources.

# Highlighted features

## Reusability

This demo demonstrates how you can reuse some Java code from existing JavaFX applications.
For example, The ray tracing computation code was taken from this [JavaFX application][raytracer-computation-source].

## Web workers and WebAssembly

Since JavaFX was not originally designed for the web, WebFX provides some additional APIs to work with web-specific concepts
such as web workers and WebAssembly modules, which can be useful for applications requiring heavy background tasks.
WebFX can interact with third-party web workers and WebAssembly modules or you can write your own in Java.
In this demo, they are written in Java and compiled with [TeaVM][teavm-website].

[demo-live-link]: https://raytracer.webfx.dev
[demo-mandelbrot-repo]: https://github.com/webfx-project/webfx-demo-mandelbrot
[raytracer-computation-source]: https://github.com/steventrowland/JavaFX-Ray-Tracer
[teavm-website]: http://teavm.org/
