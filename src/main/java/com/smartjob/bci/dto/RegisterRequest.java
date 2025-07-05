package com.smartjob.bci.dto;

import java.time.LocalDate;
import java.util.List;

import com.smartjob.bci.common.Constants;
import com.smartjob.bci.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String name;
	
	@Pattern(regexp = Constants.EMAIL_RGX, message = "Correo debe tener un dominio válido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;
	
	@NotBlank(message = "La contraseña es obligatoria")
	@ValidPassword
    private String password;
    
    private List<PhoneDTO> phones;

}
