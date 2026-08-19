package com.wallo.wallo_api.controller;

import com.wallo.wallo_api.dto.account.AccountRequest;
import com.wallo.wallo_api.dto.account.AccountResponse;
import com.wallo.wallo_api.security.UserDetailsImpl;
import com.wallo.wallo_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

/**
 * Endpoints de gerenciamento de contas do usuário autenticado.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody AccountRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        AccountResponse response = accountService.create(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponse>> list(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(accountService.list(userDetails.getUser(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(accountService.update(id, request, userDetails.getUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(accountService.findById(id, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        accountService.delete(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}