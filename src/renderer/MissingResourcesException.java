package renderer;

/**
 * exception management
 */
public class MissingResourcesException extends Exception {
    public MissingResourcesException(String msg) {
        super(msg);
    }

    private String className;

    public MissingResourcesException() {

    }

    /**
     * constructor
     * @param missing_resource
     * @param className
     * @param s
     */
    public MissingResourcesException(String missing_resource, String className, String s) {
        this.className=className;

    }

    /***
     * getter
     * @return className
     */
    public String getClassName() {
        return this.className;
    }
}
