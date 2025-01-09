/**
 * @author Kenny
 */
package src.TextEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A very limited text parser, editor, and printer
 *
 * Necessary functionalities are all called from
 * public methods
 */
public class PoorTextEditor {

	private Map<String, Object> repository = new LinkedHashMap<>();

	/**
	 * Takes a text file in the format of:
	 *
	 * e.g. FruitsInventory.txt
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 * A ":" indicates the start of an array item
	 * A "/" indicates the separation of a key value pair
	 *
	 * When an array item (item1) has been successfully added to a HashMap
	 * and its contents into a nested HashMap, it stops further updating
	 * to the array item when encountering a new array item (item2) as
	 * indicated by ":"
	 *
	 * Repository is updated with information gathered from text file
	 *
	 * @param filePath path of file, relative or absolute
	 */
	public void processTextFile(String filePath) {

		repository = new LinkedHashMap<>();
		parseTextToRepo(filePath);
	}

	public Map<String, Object> getRepositoryStrings() {
		return repository;
	}

	/**
	 * Writes the data from the repository (HashMap) to a text file.
	 * The output format will look like:
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 * @param filePath desired path of the file to write to
	 */
	public void writeToTextFile(String filePath) {

		writeDataToFile(filePath);
	}


	/**
	 * Writes the data from the repository (HashMap) to a text file.
	 * The output format will look like:
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 *
	 * @param filePath desired path of the file to write to
	 */
	public void writeToTextFile(Map<String, Object> givenRepo, String filePath) {
		writeDataToFile(givenRepo, filePath);
	}

	/**
	 * @return the HashMap repository
	 */
	public Map<String, Object> getRepository() {

		return repository;
	}

	/**
	 * Sets the current repository with given HashMap repository
	 * @param repository given HashMap repository
	 */
	public void setRepository(Map<String, Object> repository) {

		this.repository = repository;
	}

	//sets the HashMap<String, Map<String,String>> repo after converting it to
	//HashMap<String, Object> repo
	public void setRepositoryStrings(Map<String, Map<String,String>> repo) {
		Map<String, Object> result = new HashMap<>();
		result.putAll(repo);
		repository=result;
	}

	public Map<String, Map<String,String>> getRepositoryString() {

		Map<String,Map<String,String>> result = new HashMap<>();

		for (Map.Entry<String, Object> entry : repository.entrySet()) {
			result.put(entry.getKey(), getArrayItemListString(entry.getKey()));
		}
		return result;
	}

	public Map<String, Map<String,String>> getRepositoryStringMap() {

		Map<String,Map<String,String>> result = new HashMap<>();

		for (Map.Entry<String, Object> entry : repository.entrySet()) {
//			result.put(entry.getKey(), getArrayItemListString(entry.getKey()));
			String key = entry.getKey();
			Object value = entry.getValue();

			if(value instanceof Map<?,?>) {
				Map<?,?> tmp = (Map<?, ?>) value;
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> stringMap = (Map<String, String>) tmp;
					result.put(key, stringMap);
				} catch (ClassCastException e) {
					System.err.println("Key '" + key + "' contains a map with incompatible types.");
				}
			}
		}
		return result;
	}


	/**
	 * To retrieve a specific value for a specific key under a specific array
	 * item
	 * @param arrayName specific name of array item
	 * @param keyName specific name of key name
	 * @return value associated with key and array item
	 */
	public String retrieveValue(String arrayName, String keyName) {

		return retrieveValueFromKey(arrayName, keyName);
	}

	public double getDoubleValue(String arrayName, String keyName) {

		return retrieveDoubleFromKey(arrayName, keyName);
	}

	/**
	 * To set a specific value for a specific key under a specific array
	 * item
	 * @param arrayName specific name of array item
	 * @param keyName specific name of key name
	 * @param value replacement value
	 */
	public void setValue(String arrayName, String keyName, String value) {

		setValueFromKey(arrayName, keyName, value);
	}

	/**
	 * Retrieves a HashMap of Key Value data under a specified array
	 * item name
	 * arrayItemName specific array item name
	 * @return HashMap of Key Value data
	 */
	public Map<String, Object> getArrayItems(String arrayName){

		return getArrayItemList(arrayName);
	}

	/**
	 * Creates a String array of all array item names
	 * @return String array of all array item names
	 */
	public String[] getArrayNames() {

		return getArrayNamesList();
	}

	/**
	 * Remove an array item with the given array name
	 * @param arrayName
	 */
	public void removeArrayItem(String arrayName) {

		removeArrayItemByKey(arrayName);
	}

	/**
	 * To display all content in text file in pretty format
	 */
	public void prettyPrint(){

		prettyPrintRepo();
	}

	/**
	 * Retrieves a HashMap of Key Value data under a specified array
	 * item name
	 * @param arrayItemName specific array item name
	 * @return HashMap of Key Value data
	 */
	private Map<String, Object> getArrayItemList(String arrayItemName) {

		if (checkRepositoryData()) {

			return null;
		}

		Map<String, Object> arrayItem;

		if (repository.containsKey(arrayItemName)) {

			arrayItem = (Map<String, Object>) repository.get(arrayItemName);
		}
		else {

			System.out.println("ArrayItem: " + arrayItemName + " not found");
			return null;
		}
		return arrayItem;

	}

	private Map<String, String> getArrayItemListString(String arrayItemName) {

		if (checkRepositoryData()) {
			return null;
		}

		Map<String, String> arrayItem=new HashMap<>();

		if (repository.containsKey(arrayItemName)) {

			for (Map.Entry<String, Object> entry : ((Map<String, Object>) repository.get(arrayItemName)).entrySet()) {
				// Convert each value to a String and put it into the new map
				String key = entry.getKey();
				String value = entry.getValue() != null ? entry.getValue().toString() : null;

				arrayItem.put(key, value);
			}
		}
		else {

			System.out.println("ArrayItem: " + arrayItemName + " not found");
			return null;
		}
		return arrayItem;
	}

	/**
	 * To retrieve a specific value for a specific key under a specific array
	 * item
	 * @param arrayName specific name of array item
	 * @param keyName specific name of key name
	 * @return value associated with key and array item
	 */
	private String retrieveValueFromKey(String arrayName, String keyName) {

		if (checkRepositoryData()) {

			return null;
		}

		String value = null;

		if (repository.containsKey(arrayName)) {

			Map<String, Object> arrayItem = (Map<String, Object>) repository.get(arrayName);

			if (arrayItem.containsKey(keyName)) {

				value = (String) arrayItem.get(keyName);

				if (value == null) {

					System.out.println("Value not found for key: " + keyName + " under arrayItem: " + arrayName);
				}
			}
			else {
				System.out.println("Key: " + keyName + " not found under arrayItem: " + arrayName);
			}
		}
		else {
			System.out.println("ArrayItem: " + arrayName + " not found");
		}
		return value;
	}

	/**
	 * To retrieve a specific double value for a specific key under a specific
	 * array item
	 * @param arrayName specific name of array item
	 * @param keyName specific name of key name
	 * @return value associated with key and array item
	 */
	private double retrieveDoubleFromKey(String arrayName, String keyName) {

		if (checkRepositoryData()) {

			return -1;
		}

		String value = null;

		if (repository.containsKey(arrayName)) {

			Map<String, Object> arrayItem = (Map<String, Object>) repository.get(arrayName);

			if (arrayItem.containsKey(keyName)) {

				value = (String) arrayItem.get(keyName);

				if (value == null) {

					System.out.println("Value not found for key: " + keyName + " under arrayItem: " + arrayName);
				}
			}
			else {
				System.out.println("Key: " + keyName + " not found under arrayItem: " + arrayName);
			}
		}
		else {
			System.out.println("ArrayItem: " + arrayName + " not found");
		}
		return Double.parseDouble(value);
	}

	/**
	 * To set a specific value for a specific key under a specific array
	 * item
	 * @param arrayName specific name of array item
	 * @param keyName specific name of key name
	 * @param value replacement value
	 */
	private void setValueFromKey(String arrayName, String keyName, String value) {

		if (!checkRepositoryData()) {

			if (repository.containsKey(arrayName)) {

				Map<String, Object> arrayItem = (Map<String, Object>) repository.get(arrayName);

				if (arrayItem.containsKey(keyName)) {

					arrayItem.replace(keyName, value);
				}
				else {
					System.out.println("Key: " + keyName + " not found under arrayItem: " + arrayName);
				}
			}
			else {
				System.out.println("ArrayItem: " + arrayName + " not found");
			}
		}
	}

	/**
	 * Takes a text file in the format of:
	 *
	 * e.g. FruitsInventory.txt
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 * A ":" indicates the start of an array item
	 * A "/" indicates the separation of a key value pair
	 *
	 * When an array item (item1) has been successfully added to a HashMap
	 * and its contents into a nested HashMap, it stops further updating
	 * to the array item when encountering a new array item (item2) as
	 * indicated by ":"
	 *
	 * Repository is updated with information gathered from text file
	 *
	 * @param filePath path of file, relative or absolute
	 */
	private void parseTextToRepo(String filePath){

		// to store array items in HashMap
		Map<String, Object> arrayItemList = new LinkedHashMap<>();
		Map<String, Object> currentItem = null;
		String currentArrayItem = null;
		// more efficient compared to Scanner
		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(filePath));
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {

				// remove trailing whitespace
				currentLine = currentLine.trim();

				// check for new array item or reached the end of an array item
				if (currentLine.endsWith(":")){

					// if array item already exists
					if ( currentArrayItem != null && currentItem != null) {

						arrayItemList.put(currentArrayItem, currentItem);
					}
					// getting array item name without ":" or whitespace
					currentArrayItem = currentLine.substring(0, currentLine.length() - 1).trim();
					// create new array item to store items
					currentItem = new LinkedHashMap<>();
				}
				// otherwise process key and value pairs
				else if (currentItem != null && currentLine.contains("/")) {

					// split current line into key and value pair at ","
					String[] keyValue = currentLine.split("/", 2);

					if (keyValue.length == 2) {

						String key = keyValue[0].trim();
						String value = keyValue[1].trim();
						currentItem.put(key, value);
					}
				}
			}
			// last processed array item is added to list
			if (currentArrayItem != null && currentItem != null) {
				arrayItemList.put(currentArrayItem, currentItem);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		repository = arrayItemList;
	}

	/**
	 * Writes the data from the repository (HashMap) to a text file.
	 * The output format will look like:
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 * @param filePath path of the file to write to
	 */
	private void writeDataToFile(String filePath) {

		if (!checkRepositoryData()) {

			BufferedWriter writer = null;

			try {

				writer = new BufferedWriter(new FileWriter(filePath));

				// Iterate over the repository map
				for (Map.Entry<String, Object> entry : repository.entrySet()) {

					String arrayName = entry.getKey();
					Map<String, String> arrayItem = (Map<String, String>) entry.getValue();

					// writing the array name followed by ":"
					writer.write(arrayName + ":\n");

					// write all key-value pairs under array item
					for (Map.Entry<String, String> keyValueEntry : arrayItem.entrySet()) {
						//converting to string

						writer.write(keyValueEntry.getKey() + "/" + keyValueEntry.getValue() + "\n");
					}

					// adding blank line to separate array items
					writer.write("\n");
				}

				// debug
				System.out.println("Data written to " + filePath + " successfully.");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes the data from the repository (HashMap) to a text file.
	 * The output format will look like:
	 *
	 * item1:
	 * name/apple
	 * price/4.12
	 * quantity/500
	 *
	 * item2:
	 * name/banana
	 * price/1.32
	 * quantity/2000
	 *
	 * @param givenRepo hashmap repository
	 * @param filePath path of the file to write to
	 */
	private void writeDataToFile(Map<String, Object> givenRepo, String filePath) {

		if (!givenRepo.isEmpty()) {

			repository = givenRepo;

			BufferedWriter writer = null;

			try {

				writer = new BufferedWriter(new FileWriter(filePath));

				// Iterate over the repository map
				for (Map.Entry<String, Object> entry : givenRepo.entrySet()) {

					String arrayName = entry.getKey();
					Map<String, String> arrayItem = (Map<String, String>) entry.getValue();

					// writing the array name followed by ":"
					writer.write(arrayName + ":\n");

					// write all key-value pairs under array item
					for (Map.Entry<String, String> keyValueEntry : arrayItem.entrySet()) {
						writer.write(keyValueEntry.getKey() + "/" + keyValueEntry.getValue() + "\n");
					}

					// adding blank line to separate array items
					writer.write("\n");
				}

				// debug
				System.out.println("Data written to " + filePath + " successfully.");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Checks if repository is empty
	 * @return true if empty, otherwise false
	 */
	private boolean checkRepositoryData() {

		if (repository.isEmpty()) {
			System.out.println("Repository not initialized with data");
			return true;
		}
		return false;
	}

	/**
	 * Creates a String array of all array item names
	 * @return String array of all array item names
	 */
	private String[] getArrayNamesList() {

		String[] arrayNamesList = new String[repository.size()];

		int index = 0;
		for (Map.Entry<String, Object> entry : repository.entrySet()) {

			if (!entry.getKey().isEmpty()) {
				arrayNamesList[index] = entry.getKey();
				index++;
			}
		}
		return arrayNamesList;
	}

	/**
	 * Remove an array item with the given array name
	 * @param arrayName
	 */
	private void removeArrayItemByKey(String arrayName) {

		if (!checkRepositoryData()) {

			if (repository.containsKey(arrayName)) {

				repository.remove(arrayName);
				System.out.println("ArrayItem: " + arrayName + " has been successfully removed");
			}
			else {
				System.out.println("ArrayItem: " + arrayName + " not found");
			}
		}
	}

	/**
	 * To display all content in text file in pretty format
	 */
	private void prettyPrintRepo(){

		// Iterate over the repository map
		for (Map.Entry<String, Object> entry : repository.entrySet()) {

			String arrayName = entry.getKey();
			Map<String, String> arrayItem = (Map<String, String>) entry.getValue();

			// writing the array name followed by "{"
			System.out.println(arrayName + " {");

			// write all key-value pairs under array item
			for (Map.Entry<String, String> keyValueEntry : arrayItem.entrySet()) {
				System.out.println("    " + keyValueEntry.getKey() + " = " + keyValueEntry.getValue());
			}
			// end array item with "}"
			System.out.print("}\n");
		}
	}
}
