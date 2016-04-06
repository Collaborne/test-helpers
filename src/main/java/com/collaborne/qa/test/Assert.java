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
package com.collaborne.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Common assertions for tests.
 * 
 * @author andreas
 * @see {@link org.junit.Assert}
 */
public class Assert {
	private Assert() {
		/* No instantiation */
	}
	
	public static void assertArrayEqualsNoOrder(Object[] first, Object[] second) {
		assertArrayEqualsNoOrder(null, first, second);
	}
	
	public static void assertArrayEqualsNoOrder(String failMessage, Object[] first, Object[] second) {
		assertEquals(failMessage, new HashSet<>(Arrays.asList(first)), new HashSet<>(Arrays.asList(second)));
	}
}
