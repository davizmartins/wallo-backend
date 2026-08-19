package com.wallo.wallo_api.controller;

import com.wallo.wallo_api.dto.transaction.TransactionRequest;
import com.wallo.wallo_api.dto.transaction.TransactionResponse;
import com.wallo.wallo_api.security.UserDetailsImpl;
import com.wallo.wallo_api.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
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
    public ResponseEntity<List<TransactionResponse>> list(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(transactionService.list(userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        transactionService.delete(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}