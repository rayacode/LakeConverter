package ir.artlake.lakeconverter.jave;

/**
 *
 * @author a.schild
 */
public final class Version {

    private static final String VERSION = "${project.version}";
    private static final String GROUPID = "${project.groupId}";
    private static final String ARTIFACTID = "${project.artifactId}";
    private static final String REVISION = "${buildNumber}";

    public static String getVersion() {
        return getVERSION();
    }

    /**
     * @return the VERSION
     */
    public static String getVERSION() {
        return VERSION;
    }

    /**
     * @return the GROUPID
     */
    public static String getGROUPID() {
        return GROUPID;
    }

    /**
     * @return the ARTIFACTID
     */
    public static String getARTIFACTID() {
        return ARTIFACTID;
    }

    /**
     * @return the REVISION
     */
    public static String getREVISION() {
        return REVISION;
    }

}
