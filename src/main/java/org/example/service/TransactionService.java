package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.Transaction;
import org.example.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction saveTransaction(Transaction transaction){
        transaction.setTotal_sum(transaction.getAmount());
        if(transaction.getId() == transaction.getParent_id()){
            transaction.setParent_id(null);
        }
        Transaction savedtransaction = transactionRepository.save(transaction);
        if(savedtransaction.getId()!= savedtransaction.getParent_id()){
            updateParentTransactions(savedtransaction,savedtransaction.getAmount());
        }else{
            savedtransaction.setParent_id(null);
        }
        return savedtransaction;
    }

    public Transaction putTransaction(Long id,Double amount,String type,Long parent_id){
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setParent_id(parent_id);
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(Long id){
        return transactionRepository.findById(id);
    }

    public List<Long> getTransactionByType(String type){

        return transactionRepository.findByType(type);
    }

    //Recursive Approach
    //Every Time It traverse whole tree
    public JSONObject getSum(Long id){

        Double sum = getChildNodesSum(id,getTransaction(id).get().getAmount());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sum",sum);

        return jsonObject;
    }

    public Double getChildNodesSum(Long id,Double s1){
        List<Transaction> listChild = transactionRepository.findByParentId(id);
        Double sum = s1;
        for(Transaction child:listChild){
            if(child.getId()!=id){
                sum += getChildNodesSum(child.getId(),child.getAmount());
            }
        }
        return sum;
    }

    //Directly Fetching Data From DB
    //O(1)
    public JSONObject getSumEfficientApproach(Long id){
        Optional<Transaction> transaction = transactionRepository.findById(id);
        Double total_sum = transaction.get().getTotal_sum();
        JSONObject res = new JSONObject();
        res.put("sum",total_sum);
        return res;
    }

    @Transactional
    private  void updateParentTransactions(Transaction transaction,Double amount){
        Long parentId = transaction.getParent_id();
        if(parentId!=null){
            Optional<Transaction> parentNode = transactionRepository.findById(parentId);
            transactionRepository.updateTransactionSum(parentNode.get().getId(),amount+ parentNode.get().getAmount());
            updateParentTransactions(parentNode.orElse(null),amount);
        }
    }



}
