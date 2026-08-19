package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.transaction.TransactionRequest;
import com.wallo.wallo_api.dto.transaction.TransactionResponse;
import com.wallo.wallo_api.enums.TransactionType;
import com.wallo.wallo_api.exception.BusinessException;
import com.wallo.wallo_api.model.Account;
import com.wallo.wallo_api.model.Category;
import com.wallo.wallo_api.model.Transaction;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.AccountRepository;
import com.wallo.wallo_api.repository.CategoryRepository;
import com.wallo.wallo_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Regras de negócio de transações. Ao criar ou remover uma transação,
 * o saldo da conta associada é atualizado de forma consistente.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TransactionResponse create(TransactionRequest request, User user) {
        // Busca conta e categoria garantindo que pertencem ao usuário
        Account account = accountRepository.findByIdAndUser(request.accountId(), user)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        Category category = categoryRepository.findByIdAndUser(request.categoryId(), user)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.description());
        transaction.setAmount(request.amount());
        transaction.setType(request.type());
        transaction.setDate(request.date());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setUser(user);

        // Atualiza o saldo da conta conforme o tipo
        applyToBalance(account, request.type(), request.amount());
        accountRepository.save(account);

        Transaction saved = transactionRepository.save(transaction);
        return TransactionResponse.fromEntity(saved);
    }

    public List<TransactionResponse> list(User user) {
        return transactionRepository.findByUser(user)
                .stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void delete(Long id, User user) {
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Transação não encontrada"));

        // Reverte o efeito da transação no saldo antes de remover
        Account account = transaction.getAccount();
        reverseFromBalance(account, transaction.getType(), transaction.getAmount());
        accountRepository.save(account);

        transactionRepository.delete(transaction);
    }

    /** Receita soma; despesa subtrai. */
    private void applyToBalance(Account account, TransactionType type, BigDecimal amount) {
        BigDecimal current = account.getBalance();
        if (type == TransactionType.INCOME) {
            account.setBalance(current.add(amount));
        } else {
            account.setBalance(current.subtract(amount));
        }
    }

    /** Operação inversa de applyToBalance, usada ao remover uma transação. */
    private void reverseFromBalance(Account account, TransactionType type, BigDecimal amount) {
        BigDecimal current = account.getBalance();
        if (type == TransactionType.INCOME) {
            account.setBalance(current.subtract(amount));
        } else {
            account.setBalance(current.add(amount));
        }
    }
}