package primitives;

public class Material {
    public double kD = 0.0;
    public double kS = 0.0;


    /***
     * transparancy prefix
     */
    public double kR = 0.0;
    /***
     * reflectance prefix
     */
    public double kT = 0.0;

    public int nShininess = 0;

    //setters

    /***
     *
     * @param kD
     * @return builder pattern
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /***
     *
     * @param kS
     * @return builder pattern
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;

    }

    /***
     *
     * @param nShininess
     * @return builder pattern
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;

    }

    /***
     *
     * @param kR
     * @return builder pattern
     */
    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }

    /***
     *
     * @param kT
     * @return builder pattern
     */
    public Material setkT(double kT) {
        this.kT = kT;
        return this;
    }

}
