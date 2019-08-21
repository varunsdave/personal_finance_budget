package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionProcessor {

    /**
     * Creates a new transaction based on input Transaction types. Creates a new id.
     * @param transaction transaction model
     * @return persisted model of the transaction
     */
    Transaction create(Transaction transaction, String accountId);

    /**
     * Deletes a transaction based on the id
     * @param id the id of the transaction to be deleted
     */
    void delete(String id);

    /**
     * Used to retrieve all the transactions based on the context of the processor
     * @return a list of all tranctions
     */
    List<Transaction>  getAll(String accountId);

    /**
     * Returns all transactions after a date time
     * @param date
     * @return
     */
    List<Transaction> getAllAfterDate(String accountId, Date date);

    /**
     * Updates a transaction based on the transaction id
     * @param id the id of the transaction to be updated
     * @param newTransaction new representation of the transaction
     * @return provides the updated transaction object
     */
    Transaction update(String id, Transaction newTransaction);

    /**
     * Returns the latest transaction for an account
     * @param accountId
     * @return newest transaction for account
     */
    Transaction getMostRecent(String accountId);
}
