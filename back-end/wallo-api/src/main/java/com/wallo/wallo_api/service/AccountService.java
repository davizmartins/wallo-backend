package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.account.AccountRequest;
import com.wallo.wallo_api.dto.account.AccountResponse;
import com.wallo.wallo_api.exception.BusinessException;
import com.wallo.wallo_api.model.Account;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Regras de negócio de contas, sempre no escopo do usuário autenticado.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse create(AccountRequest request, User user) {
        if (accountRepository.existsByNameAndUser(request.name(), user)) {
            throw new BusinessException("Já existe uma conta com esse nome");
        }

        Account account = new Account();
        account.setName(request.name());
        account.setType(request.type());
        // Se o saldo inicial não for informado, começa em zero
        account.setBalance(request.initialBalance() != null ? request.initialBalance() : BigDecimal.ZERO);
        account.setUser(user);

        return AccountResponse.fromEntity(accountRepository.save(account));
    }

    public Page<AccountResponse> list(User user, Pageable pageable) {
        return accountRepository.findByUser(user, pageable)
                .map(AccountResponse::fromEntity);
    }

    public AccountResponse update(Long id, AccountRequest request, User user) {
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        account.setName(request.name());
        account.setType(request.type());

        return AccountResponse.fromEntity(accountRepository.save(account));
    }

    public AccountResponse findById(Long id, User user) {
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        return AccountResponse.fromEntity(account);
    }

    public void delete(Long id, User user) {
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        accountRepository.delete(account);
    }
}