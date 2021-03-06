package com.github.punchat.messaging.domain.role;

import com.github.punchat.dto.messaging.role.RoleResponse;
import com.github.punchat.dto.messaging.role.RoleRequest;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RoleController {
    private final RoleFacadeService service;

    @ApiOperation("create role")
    @PostMapping("/roles")
    public RoleResponse create(@RequestBody RoleRequest request) {
        return service.create(request);
    }

    @ApiOperation("get role by id")
    @GetMapping("/roles/{id}")
    public RoleResponse getRoleById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @ApiOperation("edit role by name")
    @PutMapping("/roles/{id}")
    public RoleResponse edit(@PathVariable("id") Long id, @RequestBody RoleRequest request) {
        return service.edit(id, request);
    }
}
