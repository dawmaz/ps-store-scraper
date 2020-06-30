package com.mrdave19.PSStoreGameScraperV3.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommonUtils {

	public static List<String> compareSequentObjects(Object obj1, Object obj2, List<String> ignoredFields) {
		if (obj1 == null || obj2 == null)
			return new ArrayList<>(Arrays.asList("Cannot operate on null objects"));
		if (!obj1.getClass().equals(obj2.getClass()))
			return new ArrayList<>(Arrays.asList("Cannot compare objects of diferent class"));
		Class<?> cls = obj1.getClass();
		Field[] fields = obj1.getClass().getDeclaredFields();
	
		List<String> changeStatus = new ArrayList<>();
	
		for (Field field : fields) {
			if (!ignoredFields.contains(field.getName())) {
				String change = fieldChangeMessage(cls, field, obj1, obj2);
				if (change != null)
					changeStatus.add(change);
			}
	
		}
		return changeStatus;
	}

	public static String fieldChangeMessage(Class<?> cls, Field field, Object obj1, Object obj2) {
		String change = null;
		try {
			Method method = cls.getDeclaredMethod(CommonUtils.produceGetMethodString(field.getName()));
			Object result1 = method.invoke(obj1);
			Object result2 = method.invoke(obj2);
			if (result1 == null || result1.equals(""))
				result1 = "null";
			if (result2 == null || result2.equals(""))
				result2 = "null";
			if (!result1.equals(result2)) {
				change = CommonUtils.toUpperFirstLetter(field.getName()).concat(" changed from: ").concat(result1.toString())
						.concat(" to: ").concat(result2.toString());
			}
	
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return change;
	}

	public static String produceGetMethodString(String fieldName) {
		String string = CommonUtils.toUpperFirstLetter(fieldName);
		return "get".concat(string);
	}

	public static String toUpperFirstLetter(String fieldName) {
		char[] c = fieldName.toCharArray();
		c[0] = Character.toUpperCase(c[0]);
		return String.copyValueOf(c);
	}
	
	public static String truncString(String string, int i) {
		return (string.length() > i + 3) ? string.substring(0, i).concat("...") : string;
	}
	

	
	

}
