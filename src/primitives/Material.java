package primitives;

public class Material {
    public double kD = 0;
    public double kS = 0;
    public int nShininess = 0;

    //setters

    /***
     *
     * @param kD
     * @return builder pattern
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /***
     *
     * @param kS
     * @return builder pattern
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;

    }

    /***
     *
     * @param nShininess
     * @return builder pattern
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;

    }


}
