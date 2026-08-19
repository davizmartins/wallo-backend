package com.wallo.wallo_api.controller;

import com.wallo.wallo_api.dto.transaction.TransactionRequest;
import com.wallo.wallo_api.dto.transaction.TransactionResponse;
import com.wallo.wallo_api.security.UserDetailsImpl;
import com.wallo.wallo_api.service.TransactionService;
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
 * Endpoints de gerenciamento de transações do usuário autenticado.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        TransactionResponse response = transactionService.create(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> list(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(transactionService.list(userDetails.getUser(), pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        transactionService.delete(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}