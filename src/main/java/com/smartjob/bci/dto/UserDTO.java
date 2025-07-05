package com.smartjob.bci.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.smartjob.bci.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String name;
    private String email;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate lastLogin;
    
    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.created = user.getCreated();
        this.modified = user.getModified();
        this.lastLogin = user.getLastLogin();
    }

}
