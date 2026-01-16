package com.example.users_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(properties = "JWT_SECRET=my_super_secret_jwt_key_1234567890!!")
class UsersApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
