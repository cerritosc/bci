package com.smartjob.bci.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
	
	@NotBlank(message = "El número es obligatorio")
	private String number;
	
	@NotBlank(message = "El código de ciudad es obligatorio")
    private String citycode;
	
	@NotBlank(message = "El código de país es obligatorio")
    private String countrycode;

}
