package elements;

import primitives.Color;

/***
 * private abstract class
 * in charge of intensity of light
 * has a ctor
 * and a getter of the intensity
 */
abstract class Light {
    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
