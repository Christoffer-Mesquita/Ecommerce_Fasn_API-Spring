package com.ecommerce.fashionshop;

import com.ecommerce.fashionshop.modules.dto.LoginDTO;
import com.ecommerce.fashionshop.modules.dto.UserDTO;
import com.ecommerce.fashionshop.modules.services.UserService;
import com.ecommerce.fashionshop.model.User;
import com.ecommerce.fashionshop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class FashionshopApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testRegisterUser() throws Exception {
		log.debug("Iniciando testRegisterUser...");
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Teste");
		userDTO.setEmail("teste@example.com");
		userDTO.setPassword("senha123");
		log.debug("Test User DTO: {}", userDTO);
		User user = new User();
		user.setId(1L);
		user.setName("Teste");
		user.setEmail("teste@example.com");
		user.setPassword("senhacriptografada");
		when(userService.registerUser(any(UserDTO.class))).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userDTO)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@example.com"));
		log.debug("testRegisterUser completado com sucesso.");
	}

	@Test
	void testLoginUser() throws Exception {
		log.debug("Iniciando testLoginUser...");
		LoginDTO loginDTO = new LoginDTO("teste@example.com", "senha123");
		log.debug("Test Login DTO: {}", loginDTO);
		User user = new User();
		user.setId(1L);
		user.setName("Teste");
		user.setEmail("teste@example.com");
		user.setPassword("senhacriptografada");
		when(userService.loginUser(any(LoginDTO.class))).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginDTO)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@example.com"));
		log.debug("testLoginUser completado com sucesso.");
	}
}