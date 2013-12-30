package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public abstract class BaseApi {
    protected Protocol protocol;

    /**
     * Constructor.
     * 
     * @param protocolInput protocol
     */
    public BaseApi(Protocol protocolInput) {
        protocol = protocolInput;
    }

    /**
     * 
     * @param method method
     * 
     * @return url parameters
     */
    protected HashMap<String, String> getUrlParameters(String method) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("method", method);
        return parameters;
    }
    
}
