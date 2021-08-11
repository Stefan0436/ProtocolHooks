package org.asf.cyan.api.config.serializing;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimitiveClassUtil {
	static final Map<Class<?>, Class<?>> PRIMITIVES = Map.of(
		byte.class, Byte.class,
		short.class, Short.class,
		int.class, Integer.class,
		long.class, Long.class,
		float.class, Float.class,
		double.class, Double.class,
		boolean.class, Boolean.class,
		char.class, Character.class
	);
	
	static final Map<Class<?>, Class<?>> REVERSEPRIMITIVES = PRIMITIVES.keySet().stream()
			.collect(Collectors.toMap(v -> PRIMITIVES.get(v),
					k -> PRIMITIVES.keySet().toArray(new Class<?>[0])[Arrays
							.asList(PRIMITIVES.keySet().stream().map(t -> t.getTypeName()).toArray())
							.indexOf(k.getTypeName())]));

	@SuppressWarnings("unchecked")
	public static <T, O> O getPrimitive(T input) {
		if (REVERSEPRIMITIVES.get(input.getClass()) != null) {
			try {
				return (O) input.getClass().getMethod(REVERSEPRIMITIVES.get(input.getClass()).getTypeName() + "Value")
						.invoke(input);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				return null;
			}
		} else {
			throw new IllegalArgumentException("Invalid input type!");
		}
	}

	public static Class<?> getPrimitiveClass(Class<?> input) {
		if (REVERSEPRIMITIVES.get(input) != null) {
			return REVERSEPRIMITIVES.get(input);
		} else {
			throw new IllegalArgumentException("Invalid input type!");
		}
	}

	public static Class<?> getWrapperClass(Class<?> input) {
		if (PRIMITIVES.get(input) != null) {
			return PRIMITIVES.get(input);
		} else {
			throw new IllegalArgumentException("Invalid input type!");
		}
	}

	@SuppressWarnings("unchecked")
	public static <T, O> O getWrapper(T input) {
		if (REVERSEPRIMITIVES.get(input.getClass()) != null) {
			return (O) input;
		} else {
			throw new IllegalArgumentException("Invalid input type!");
		}
	}

	public static boolean isSupportedPrimitive(Class<?> input) {
		return PRIMITIVES.containsKey(input);
	}

	public static boolean isSupportedWrapper(Class<?> input) {
		return PRIMITIVES.containsValue(input);
	}
}
