package com.apromore.challenge.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {

    @NotEmpty(message = "Student name cannot be empty")
    @Size(max = 100, message = "Student name cannot be more than 100 characters")
    @Pattern(regexp = "^[A-Za-z\\s']+$", message = "Student name should only contain uppercase, lowercase and space")
    private String name;

    @NotNull
    @Min(5)
    @Max(60)
    private Integer age;

    @Size(min = 1)
    private Set<Long> courseIds;
}
