package com.vinz.energy.EnergyForge;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.vinz.energy.EnergyForge.db.enums.Effect;

@SpringBootTest
class EnergyForgeApplicationTests {

	@Test
	void contextLoads() {
		List<Effect> effects = new ArrayList<Effect>();
		effects.add(Effect.REPEL);
		effects.add(Effect.REPEL);
		effects.add(Effect.REPEL);
		effects.add(Effect.REPEL);
		
		effects.remove(Effect.REPEL);
		System.out.println(effects);
	}

}
