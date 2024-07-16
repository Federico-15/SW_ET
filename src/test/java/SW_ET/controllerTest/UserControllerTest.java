package SW_ET.controllerTest;/*
package SW_ET.controller;

import SW_ET.dto.UserDto;
import SW_ET.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testRegister_UserIdExists() throws Exception {
        when(userService.isUserIdExists(any())).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .param("userId", "testUser")
                        .param("userNickName", "닉네임")
                        .param("userPassword", "password")
                        .param("confirmPassword", "password")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "User ID already exists."));
    }

    @Test
    public void testRegister_UserNickNameExists() throws Exception {
        when(userService.isUserIdExists(any())).thenReturn(false);
        when(userService.isUserNickNameExists(any())).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .param("userId", "testUser")
                        .param("userNickName", "닉네임")
                        .param("userPassword", "password")
                        .param("confirmPassword", "password")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "User Nickname already exists."));
    }

    @Test
    public void testRegister_Success() throws Exception {
        when(userService.isUserIdExists(any())).thenReturn(false);
        when(userService.isUserNickNameExists(any())).thenReturn(false);

        mockMvc.perform(post("/users/register")
                        .param("userId", "testUser")
                        .param("userNickName", "닉네임")
                        .param("userPassword", "password")
                        .param("confirmPassword", "password")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        when(userService.validateUser(any())).thenReturn(false);

        mockMvc.perform(post("/users/login")
                        .param("userId", "testUser")
                        .param("userPassword", "wrongpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Invalid username or password."));
    }

    @Test
    public void testLogin_Success() throws Exception {
        when(userService.validateUser(any())).thenReturn(true);

        mockMvc.perform(post("/users/login")
                        .param("userId", "testUser")
                        .param("userPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/home"));
    }

    @Test
    public void testShowHomePage_NotLoggedIn() throws Exception {
        mockMvc.perform(get("/users/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Please login first."));
    }

    @Test
    public void testShowHomePage_LoggedIn() throws Exception {
        mockMvc.perform(get("/users/home")
                        .sessionAttr("user", new UserDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/home"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/users/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    public void testRegisterWithInvalidUserNickName() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId("string");
        userDto.setUserNickName("200");
        userDto.setUserPassword("string");
        userDto.setConfirmPassword("string");
        userDto.setUserEmail("string");

        mockMvc.perform(post("/users/register")
                        .flashAttr("user", userDto))
                .andExpect(status().isBadRequest());
    }
}
*/
