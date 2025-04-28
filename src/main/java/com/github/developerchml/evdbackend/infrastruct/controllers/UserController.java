package com.github.developerchml.evdbackend.infrastruct.controllers;

import com.github.developerchml.evdbackend.core.services.UserService;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserCredentialDTO;
import com.github.developerchml.evdbackend.infrastruct.requests.RequestUserDTO;
import com.github.developerchml.evdbackend.infrastruct.responses.ResponseUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements CRUDController<RequestUserDTO, ResponseUserDTO, Long> {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{ucode}")
    @Override
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable Long ucode) {
        return ResponseEntity.ok(userService.findById(ucode));
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

    @PutMapping("/{ucode}")
    @Override
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long ucode, @RequestBody RequestUserDTO dto) {
        return ResponseEntity.ok(userService.update(ucode, dto));
    }

    @DeleteMapping("/{ucode}")
    @Override
    public ResponseEntity<Void> softDelete(@PathVariable Long ucode) {
        userService.softDelete(ucode);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{ucode}/force-delete")
    @Override
    public ResponseEntity<Void> forceDelete(@PathVariable Long ucode) {
        userService.forceDelete(ucode);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ucode}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long ucode) {
        userService.activate(ucode);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ucode}/block")
    public ResponseEntity<Void> block(@PathVariable Long ucode) {
        userService.block(ucode);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ucode}/credential")
    public ResponseEntity<Void> changeCredential(@PathVariable Long ucode, @RequestBody RequestUserCredentialDTO credentialDTO) {
        userService.changeCredential(ucode, credentialDTO);
        return ResponseEntity.noContent().build();
    }

}
