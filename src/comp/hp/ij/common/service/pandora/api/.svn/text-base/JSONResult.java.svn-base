package comp.hp.ij.common.service.pandora.api;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

import org.json.*;

import comp.hp.ij.common.service.pandora.util.Logger;

import java.util.ArrayList;

/**
 * Result implementation that wraps the json JSONObject result.
 */
public class JSONResult implements Result {
	private final JSONObject jsonObject;

	public JSONResult(JSONObject resultObject) {
		this.jsonObject = resultObject;
	}

	public String getString(String key) {
		return (String) jsonObject.optString(key);
	}

	@SuppressWarnings("unchecked")
    public ArrayList getArray(String key) {
		JSONArray jsonArray = jsonObject.optJSONArray(key);
		ArrayList array = buildArrayList(jsonArray);
		return array;
	}

	public boolean containsKey(String key) {
		return jsonObject.has(key);
	}

	public boolean getBoolean(String key) {
		return (Boolean) jsonObject.optBoolean(key);
	}
	
    public Result getResult(String sKey) {
        Result resultReturn = null;
        try {
            resultReturn = new JSONResult((JSONObject) jsonObject.get(sKey));
        } catch (Exception e) {
            Logger.e(e);
        }
        return resultReturn;
    }

	@SuppressWarnings("unchecked")
	private ArrayList buildArrayList(JSONArray jsonArray) {
		ArrayList array = new ArrayList();
		for (int  i = 0; i < jsonArray.length(); i++) {
			Object obj = jsonArray.opt(i);
			if (obj instanceof JSONArray) {
				array.add(buildArrayList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				array.add(new JSONResult((JSONObject) obj));
			} else {
				array.add(obj);
			}
		}
		return array;
	}
}
