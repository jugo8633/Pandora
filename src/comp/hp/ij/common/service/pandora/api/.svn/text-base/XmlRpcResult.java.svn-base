package comp.hp.ij.common.service.pandora.api;
import java.util.ArrayList;
import java.util.Map;

/**
 * Result implementation that wraps the xmlrcp Map result.
 */
@SuppressWarnings("unchecked")
public class XmlRpcResult implements Result {
	private final Map result;

	XmlRpcResult(Map result) {
		this.result = result;
	}

	public String getString(String key) {
		return (String) result.get(key);
	}

	public ArrayList getArray(String key) {
		return (ArrayList) result.get(key);
	}

	public boolean getBoolean(String key) {
		return (Boolean) result.get(key);
	}

	public boolean containsKey(String key) {
		return result.containsKey(key);
	}

	public int getInt(String key) {
		return (Integer) result.get(key);
	}

	public XmlRpcResult getResult(String key) {
		return (XmlRpcResult) result.get(key);
	}

	public void put(Object key, Object value) {
		result.put(key, value);
	}
}
