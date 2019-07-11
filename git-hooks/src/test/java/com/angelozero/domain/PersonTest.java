package com.angelozero.domain;

import org.junit.Assert;
import org.junit.Test;

public class PersonTest {
	
	@Test
	public void shouldBeSameName() {
		String name = "Angelo";
		Person person = new Person(name);

		// *** Teste passando
		Assert.assertEquals(person.getName(), name);
	}

}
