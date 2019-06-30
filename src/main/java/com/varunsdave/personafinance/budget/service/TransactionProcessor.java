package com.varunsdave.personafinance.budget.service;

import com.varunsdave.personafinance.budget.model.Transaction;

import java.util.List;

public interface TransactionProcessor {

    /**
     * Create a new transaction for the current date and time.
     * @param amount : the transaction amount
     * @return transaction object with the amount
     */
    Transaction create(int amount);

    /**
     * Deletes a transaction based on the id
     * @param id the id of the transaction to be deleted
     */
    void delete(String id);

    /**
     * Used to retrieve all the transactions based on the context of the processor
     * @return a list of all tranctions
     */
    List<Transaction>  getAll();

    /**
     * Updates a transaction based on the transaction id
     * @param id the id of the transaction to be updated
     * @param newTransaction new representation of the transaction
     * @return provides the updated transaction object
     */
    Transaction update(String id, Transaction newTransaction);
}
