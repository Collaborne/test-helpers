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

import static com.collaborne.test.Assert.assertArrayEqualsNoOrder;

import org.junit.Test;

public class EnumValuesSupplierTest {
	private enum TestEnum {
		TRUE,
		FALSE,
		FILE_NOT_FOUND;
	}
	
	@Test
	public void getValuesEnumReturnsAllEnumValues() {
		EnumValuesSupplier supplier = new EnumValuesSupplier();
		assertArrayEqualsNoOrder(TestEnum.values(), supplier.getValues(TestEnum.class));
	}
	
	@Test
	public void getValuesBooleanReturnsTrueFalse() {
		EnumValuesSupplier supplier = new EnumValuesSupplier();
		assertArrayEqualsNoOrder(new Object[] { Boolean.FALSE, Boolean.TRUE }, supplier.getValues(Boolean.class));
	}

	@Test
	public void getValuesPrimitiveBooleanReturnsTrueFalse() {
		EnumValuesSupplier supplier = new EnumValuesSupplier();
		assertArrayEqualsNoOrder(new Object[] { Boolean.FALSE, Boolean.TRUE }, supplier.getValues(Boolean.TYPE));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getValuesUnknownThrowsIAE() {
		EnumValuesSupplier supplier = new EnumValuesSupplier();
		supplier.getValues(/* "Other" enough */ String.class);
	}
}
