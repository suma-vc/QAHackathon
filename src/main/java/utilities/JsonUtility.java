package utilities;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	private static final Gson gson = new Gson();


	/**
	 * Converts a String response to a JsonObject.
	 *
	 * @param response The response string to convert.
	 * @return The response as a JsonObject.
	 */
	public static JsonObject convertStringToJson(String response) {
		return gson.fromJson(response, JsonObject.class);
	}

	/**
	 * Converts a JsonObject to a String.
	 *
	 * @param jsonObject The JsonObject to convert.
	 * @return The JsonObject as a String.
	 */
	public static String convertJsonToString(JsonObject jsonObject) {
		return gson.toJson(jsonObject);
	}

	/**
	 * Parses a String into a JsonElement.
	 *
	 * @param jsonString The String to parse.
	 * @return The parsed JsonElement.
	 */
	public static JsonElement parseStringToJsonElement(String jsonString) {
		return JsonParser.parseString(jsonString);
	}

	/**
	 * Converts an object to a JsonObject.
	 *
	 * @param object The object to convert.
	 * @return The object as a JsonObject.
	 */
	public static JsonObject convertObjectToJson(Object object) {
		String jsonString = gson.toJson(object);
		return gson.fromJson(jsonString, JsonObject.class);
	}

	/**
	 * Retrieves a JsonElement from a JsonObject given a key.
	 *
	 * @param jsonObject The JsonObject to retrieve the element from.
	 * @param key The key of the element to retrieve.
	 * @return The JsonElement, or null if the key does not exist.
	 */
	public static JsonElement getElementFromObject(JsonObject jsonObject, String key) {
		return jsonObject.get(key);
	}

	/**
	 * Retrieves a JsonObject from a JsonArray given an index.
	 *
	 * @param jsonArray The JsonArray to retrieve the object from.
	 * @param index The index of the object to retrieve.
	 * @return The JsonObject, or null if the index is out of bounds.
	 */
	public static JsonObject getObjectFromArray(JsonArray jsonArray, int index) {
		return jsonArray.get(index).getAsJsonObject();
	}

	/**
	 * Retrieves a JsonArray from a JsonObject given a key.
	 *
	 * @param jsonObject The JsonObject to retrieve the array from.
	 * @param key The key of the array to retrieve.
	 * @return The JsonArray, or null if the key does not exist.
	 */
	public static JsonArray getArrayFromObject(JsonObject jsonObject, String key) {
		return jsonObject.getAsJsonArray(key);
	}
}
