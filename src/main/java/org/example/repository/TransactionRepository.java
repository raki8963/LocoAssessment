package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT T.id FROM Transaction T where T.type= :type")
    List<Long> findByType(String type);

    @Query("SELECT T FROM Transaction T where T.parent_id = :id")
    List<Transaction> findByParentId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Transaction T SET T.total_sum = :totalAmount WHERE T.id = :id")
    void updateTransactionSum(Long id, Double totalAmount);

}
