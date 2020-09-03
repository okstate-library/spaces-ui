package com.okstatelibrary.spacesui.util;

import java.util.List;

public class StringUtil {

	public static String convertToPipeSeperatedString(List<String> list) {

		if (list.size() > 0) {
			StringBuilder nameBuilder = new StringBuilder();

			for (String n : list) {

				nameBuilder.append(n).append(" |");
			}

			nameBuilder.deleteCharAt(nameBuilder.length() - 1);

			return nameBuilder.toString();
		} else {
			return "";
		}

	}

	public static String convertToCommaSeperatedString(String[] list) {

		if (list.length > 0) {
			StringBuilder nameBuilder = new StringBuilder();

			for (String n : list) {

				nameBuilder.append(n).append(",");
			}

			nameBuilder.deleteCharAt(nameBuilder.length() - 1);

			return nameBuilder.toString();
		} else {
			return "";
		}

	}
}
