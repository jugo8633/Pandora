package comp.hp.ij.common.service.pandora.api;

/**
 * Object representation of Ad attributes.
 */
public class Ad implements Item {

    private String adToken;

    /**
     * Constructor.
     * 
     * @param trackResult trackResult
     */
    public Ad(Result trackResult) {
        adToken = trackResult.getString("adToken");
    }

    /**
     * 
     * @return ad token
     */
    public String getAdToken() {
        return adToken;
    }

}
