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

    public MissingResourcesException(String missing_resource, String className, String s) {
        this.className=className;

    }

    public String getClassName() {
        return this.className;
    }
}
