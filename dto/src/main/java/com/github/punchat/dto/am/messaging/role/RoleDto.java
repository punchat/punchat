package com.github.punchat.dto.am.messaging.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {
    private String name;
    private List<String> permissions;
}
