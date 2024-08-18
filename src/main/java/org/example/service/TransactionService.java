package org.example.service;

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

    public Transaction saveTransaction(Transaction transaction){
        if(transaction.getParent_id()!=null){
            Transaction check = transactionRepository.findById(transaction.getParent_id()).orElse(null);
            if(check==null){
                transaction.setParent_id(null);
            }
        }
        return transactionRepository.save(transaction);
    }

    public Transaction putTransaction(Long id,Double amount,String type,Long parent_id){

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(amount);

//        if(parent_id!=null){
//            Transaction check = transactionRepository.findById(parent_id).orElse(null);
//            if(check!=null){
//                transaction.setParent_id(parent_id);
//            }
//        }
        transaction.setParent_id(parent_id);
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(Long id){
        return transactionRepository.findById(id);
    }

    public List<Long> getTransactionByType(String type){

        return transactionRepository.findByType(type);
    }

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

}
