package com.edu.biz.common.util;

import java.util.ArrayList;
import java.util.List;

import com.edu.core.exception.ServiceException;

public class TermCodeUtil {

	public static String getTermCode(String grade, Integer termNum) {
		String prefix = grade.substring(grade.length() - 2);
		Integer num = (int) Math.ceil(termNum / 2);
		String firstCodePrefix = "";
		if (num == 1) {
			firstCodePrefix = prefix + "-" + (Integer.parseInt(prefix) + 1);
		}
		if (num == 2) {
			firstCodePrefix = (Integer.parseInt(prefix) + 1) + "-" + (Integer.parseInt(prefix) + 2);
		}
		if (num == 3) {
			firstCodePrefix = (Integer.parseInt(prefix) + 2) + "-" + (Integer.parseInt(prefix) + 3);
		}
		String code = firstCodePrefix + "-" + (isOdd(termNum) ? 1 : 2);

		if (firstCodePrefix == "") {
			return null;
		}
		return code;
	}

	public static List<String> getAllTermCode(String grade) {
		String prefix = grade.substring(grade.length() - 2);
		String firstCodePrefix = prefix + "-" + (Integer.parseInt(prefix) + 1);
		List<String> codes = new ArrayList<>();
		for (int i = 1; i <= 2; i++) {
			codes.add(firstCodePrefix + "-" + i);
		}
		String secondCodePrefix = (Integer.parseInt(prefix) + 1) + "-" + (Integer.parseInt(prefix) + 2);
		for (int i = 1; i <= 2; i++) {
			codes.add(secondCodePrefix + "-" + i);
		}
		String thirdCodePrefix = (Integer.parseInt(prefix) + 2) + "-" + (Integer.parseInt(prefix) + 3);
		for (int i = 1; i <= 2; i++) {
			codes.add(thirdCodePrefix + "-" + i);
		}
		return codes;
	}

	private static boolean isOdd(int i) {
		return i % 2 == 1;
	}

	public static String getCoursePeriod(String period, String type) {
		String prefix = getPeriodPrefix(type);
		if (prefix == null) {
			throw new ServiceException("404", "前缀不能为空");
		}

		return (prefix + "-" + period);
	}

	private static String getPeriodPrefix(String type) {
		String periodPrefix = null;
		switch (type) {
		case "morning":
			periodPrefix = "1";
			break;
		case "afternoon":
			periodPrefix = "2";
			break;
		case "night":
			periodPrefix = "3";
			break;
		}

		return periodPrefix;
	}
}
