/**
 * Copyright (C) 2015 Collaborne B.V. (opensource@collaborne.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.collaborne.qa.test.suppliers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

import com.google.common.annotations.VisibleForTesting;

/**
 * {@link ParameterSupplier} for producing all possible enum values.
 * 
 * Usage: <code><pre>
 * 
 * @RunWith(Theories.class)
 * public class FooTest {
 *   @Theory
 *   public void stuffAlwaysWorks(@AllValues FooEnum foo) {
 *     // do something with 'foo'
 *   }
 * }
 * 
 * </pre></code>
 *
 * Note that this supplier is very closely related to the behavior in JUnit 4.12 that automatically
 * provides values for {@code boolean} and {@code enum} classes, however it also provides values
 * for "old-style" enums: if a class contains {@code public static final} constants with the same type as
 * the class itself then those values will be injected.
 * 
 * @author andreas.kohn@collaborne.com
 * @see @AllValues
 */
public class EnumValuesSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
		Object[] values = getValues(sig.getType());
		List<PotentialAssignment> result = new ArrayList<>(values.length);
		for (Object value : values) {
			result.add(PotentialAssignment.forValue(value.toString(), value));
		}
		return result;
	}
	
	@VisibleForTesting
	protected Object[] getValues(Class<?> type) {
		// Try as enum
		Object[] values = type.getEnumConstants();
		if (values != null) {
			return values;
		}
		
		// Try as a boolean
		if (Boolean.TYPE == type || Boolean.class.isAssignableFrom(type)) {
			return new Object[] { Boolean.TRUE, Boolean.FALSE };
		}
		
		// Try as a class with public static constants of that type
		List<Object> constantValues = getConstantValues(type);
		if (!constantValues.isEmpty()) {
			return constantValues.toArray(new Object[constantValues.size()]);
		}
		
		throw new IllegalArgumentException("Provided type " + type + " does not refer to an enumerated type");		
	}
	
	protected List<Object> getConstantValues(Class<?> type) {
		List<Object> result = new ArrayList<>();
		for (Field field : type.getFields()) {
			int modifiers = field.getModifiers();
			boolean isConstant = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers);
			if (isConstant && type.isAssignableFrom(field.getType())) {
				try {
					result.add(field.get(null));
				} catch (IllegalArgumentException|IllegalAccessException e) {
					// Ignore this one.
				}
			}
		}

		return result;
	}
}

