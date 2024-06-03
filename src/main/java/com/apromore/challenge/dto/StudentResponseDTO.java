package com.apromore.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private Set<Long> courseIds;
}
