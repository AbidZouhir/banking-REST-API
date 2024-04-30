package net.banking.bankingapp.repositories;

import net.banking.bankingapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account getAccountById(Long id);
}
