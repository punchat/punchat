package com.github.punchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WorkspaceEmailValidationResult {
    private String email;
    private EmailValidationResult result;
}
