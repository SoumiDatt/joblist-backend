package com.soumi.joblistbackend.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApplicantDto {

    private Integer id;

    @NotEmpty
    @Size(min = 6, max = 8, message = "Username must be min of 6 characters and max of 8 characters")
    private String userName;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$", message = "Password must be of minimum 4 and maximum 12 characters containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;

    @Email(message = "Email address is not valid")
    private String email;

    private List<String> skills;
}
