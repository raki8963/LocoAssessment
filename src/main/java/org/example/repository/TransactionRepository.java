package org.example.repository;

import org.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT T.id FROM Transaction T where T.type= :type")
    List<Long> findByType(String type);

    @Query("SELECT T FROM Transaction T where T.parent_id = :id")
    List<Transaction> findByParentId(Long id);

}
