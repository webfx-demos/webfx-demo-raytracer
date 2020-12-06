# Live demo

The demo is published on [this page][demo-live-link].

A demo very similar to the [Mandelbrot demo][demo-mandelbrot-repo] but with ray tracing, which requires even more computation resources.

# Highlighted features

## Reusability

This demo demonstrates how you can reuse some Java code from existing JavaFx applications.
For example, The ray tracing computation code was taken from this [JavaFx application][raytracer-computation-source].

## Web workers and WebAssembly

Since JavaFx was not originally designed for the web, WebFx provides some additional APIs to work with web-specific concepts
such as web workers and WebAssembly modules, which can be useful for applications requiring heavy background tasks.
WebFx can interact with third-party web workers and WebAssembly modules or you can write your own in Java.
In this demo, they are written in Java and compiled with [TeaVM][teavm-website].

[demo-live-link]: https://webfx-raytracer-demo.netlify.app
[demo-mandelbrot-repo]: https://github.com/webfx-project/webfx-demo-mandelbrot
[raytracer-computation-source]: https://github.com/steventrowland/JavaFX-Ray-Tracer
[teavm-website]: http://teavm.org/
