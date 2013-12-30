package comp.hp.ij.common.service.pandora.api;
import java.util.HashMap;

/**
 *  Performs test.* package API calls
 */
public class TestRequester extends BaseApi {

	public TestRequester(Protocol protocol) {
		super(protocol);
	}

	public boolean checkLicensing() throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		Result result = protocol.sendRequest(false, getUrlParameters("test.checkLicensing"), params);
		return result.getBoolean("isAllowed");
	}
}
