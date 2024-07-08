package com.evs.forms;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Username is required")
    private String name;
    @Email(message="Invalid Email")
    
    @NotBlank(message = "Enter email")
    private String email;
    // @Pattern(regexp="^[a-zA-Z0-9@]",message="Enter valid password")
    @Size(min = 8)
    private String password;
    @NotBlank(message = "Enter Mobile Number")
    @Size(min=10,message = "Enter valid Mobile number")
    private String phone;
    

}
