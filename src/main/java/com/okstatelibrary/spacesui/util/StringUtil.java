package com.okstatelibrary.spacesui.util;

import java.util.List;

/**
 * Utility class providing string manipulation functions commonly used for
 * formatting collections into delimited strings.
 *
 * <p>
 * This class includes static methods for converting lists or arrays of strings
 * into a single string with a specified delimiter (pipe `|` or comma `,`).
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * List<String> items = Arrays.asList("A", "B", "C");
 * String result = StringUtil.convertToPipeSeperatedString(items); // Output: "A |B |C"
 *
 * String[] names = { "John", "Jane", "Doe" };
 * String csv = StringUtil.convertToCommaSeperatedString(names); // Output: "John,Jane,Doe"
 * </pre>
 *
 * <p>
 * Note: This class is not intended to be instantiated.
 * </p>
 */
public class StringUtil {

	/**
	 * Converts a list of strings into a single string where each element is
	 * separated by a pipe (`|`).
	 *
	 * <p>
	 * For example, given {@code ["A", "B", "C"]}, the result will be
	 * {@code "A |B |C"}.
	 * </p>
	 *
	 * @param list the list of strings to join
	 * @return a pipe-separated string, or an empty string if the list is empty
	 */
	public static String convertToPipeSeperatedString(List<String> list) {
		if (list == null || list.isEmpty()) {
			return "";
		}

		StringBuilder nameBuilder = new StringBuilder();
		for (String n : list) {
			nameBuilder.append(n).append(" |");
		}

		// Remove the trailing pipe character
		nameBuilder.deleteCharAt(nameBuilder.length() - 1);

		return nameBuilder.toString();
	}

	/**
	 * Converts an array of strings into a comma-separated string.
	 *
	 * <p>
	 * For example, given {@code ["apple", "banana", "cherry"]}, the result will be
	 * {@code "apple,banana,cherry"}.
	 * </p>
	 *
	 * @param list the array of strings to join
	 * @return a comma-separated string, or an empty string if the array is empty
	 */
	public static String convertToCommaSeperatedString(String[] list) {
		if (list == null || list.length == 0) {
			return "";
		}

		StringBuilder nameBuilder = new StringBuilder();
		for (String n : list) {
			nameBuilder.append(n).append(",");
		}

		// Remove the trailing comma
		nameBuilder.deleteCharAt(nameBuilder.length() - 1);

		return nameBuilder.toString();
	}
}