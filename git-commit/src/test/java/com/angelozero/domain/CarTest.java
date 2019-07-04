package com.angelozero.domain;

import org.junit.Assert;
import org.junit.Test;

public class CarTest {

	@Test
	public void shouldBeSameModel() {
		String model = "Nissan Skyline R34 - GTR";
		Car car = new Car(model);

		Assert.assertEquals(car.getModel(), model+"i");
		
		
		
		
//		mvn test | grep -oP '(?<=Failures: )[0-9]+' | tail -1
	}

}
