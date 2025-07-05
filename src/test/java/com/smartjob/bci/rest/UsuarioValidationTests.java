package com.smartjob.bci.rest;

import com.smartjob.bci.config.JwtAuthenticationFilter;
import com.smartjob.bci.dto.UserResponse;
import com.smartjob.bci.exception.GlobalException;
import com.smartjob.bci.security.service.AuthenticationService;
import com.smartjob.bci.security.service.JwtService;
import com.smartjob.bci.security.service.UserDetailsServiceImpl;
import com.smartjob.bci.validation.PasswordConstraintValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, 
        classes = JwtAuthenticationFilter.class
    )
)
public class UsuarioValidationTests {
	
	@MockBean
	private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl usuarioService;

    private PasswordConstraintValidator passwordValidator;
    
    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        // Regex: mínimo 8 caracteres, una mayúscula, una minúscula, un número y un símbolo
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[\\W]).{8,}$";
        passwordValidator = new PasswordConstraintValidator(regex);
    }

    @Test
    void assertTrueWithValidPass() {
        assertTrue(passwordValidator.isValid("Contra123!", null));
    }

    @Test
    void assertFalseWithShortPass() {
        assertFalse(passwordValidator.isValid("Ab1!", null));
    }

    @Test
    void assertFalseWithNoUpperCasePass() {
        assertFalse(passwordValidator.isValid("contra123!", null));
    }

    @Test
    @WithMockUser
    void shouldCreateNewNewUser() throws Exception {
        when(usuarioService.register(any())).thenReturn(
        		UserResponse.builder()
                .id(UUID.randomUUID().toString())
                .created(LocalDate.now())
                .modified(null)
                .lastLogin(LocalDate.now())
                .token("token")
                .isActive(true)
                .build()
        		);

        mockMvc.perform(post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      \"email\": \"carlos.cerritos@smartjob.com\",
                      \"name\": \"Carlos Cerritos\",
                      \"password\": \"Contra123!\"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @WithMockUser
    void shouldGet409CreatingWhenEmailExists() throws Exception {
        when(usuarioService.register(any())).thenThrow(new GlobalException("Correo ya está registrado"));

        mockMvc.perform(post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      \"email\": \"test@correo.com\",
                      \"name\": \"Carlos\",
                      \"password\": \"Contra123!\"
                    }
                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("Correo ya está registrado"));
    }

    @Test
    @WithMockUser
    void shouldGetBadRequestCreatingWhenInvalidPass() throws Exception {
        mockMvc.perform(post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      \"email\": \"valido@correo.com\",
                      \"name\": \"Carlos\",
                      \"password\": \"abc\"
                    }
                """))
                .andExpect(status().isBadRequest());
    }
}