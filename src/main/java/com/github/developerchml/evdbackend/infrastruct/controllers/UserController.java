package com.github.developerchml.evdbackend.infrastruct.controllers;

import com.github.developerchml.evdbackend.core.services.CRUDService;
import com.github.developerchml.evdbackend.core.services.UserService;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements CRUDController<RequestUserDTO, ResponseUserDTO, Long> {
    private final CRUDService<RequestUserDTO, ResponseUserDTO, Long> userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{value}")
    @Override
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable Long value) {
        return ResponseEntity.ok(userService.findById(value));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ResponseUserDTO>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }

    @PostMapping
    @Override
    public ResponseEntity<ResponseUserDTO> save(@RequestBody RequestUserDTO dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping("/{value}")
    @Override
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long value, @RequestBody RequestUserDTO dto) {
        return ResponseEntity.ok(userService.update(value, dto));
    }

    @DeleteMapping("/{value}")
    @Override
    public ResponseEntity<Void> softDelete(@PathVariable Long value) {
        userService.softDelete(value);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/force-delete/{value}")
    @Override
    public ResponseEntity<Void> forceDelete(@PathVariable Long value) {
        userService.forceDelete(value);
        return ResponseEntity.noContent().build();
    }

}
