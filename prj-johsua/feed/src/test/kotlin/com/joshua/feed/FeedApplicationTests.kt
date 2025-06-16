package com.joshua.feed

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [TestConfig::class])
class FeedApplicationTests {

	@Test
	fun contextLoads() {
	}

}
