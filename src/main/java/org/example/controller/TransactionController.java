package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Transaction;
import org.example.service.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/transaction")
    public Transaction saveTransaction(@RequestBody Transaction transaction){
        return service.saveTransaction(transaction);
    }

    @PutMapping("/transaction/{id}")
    public Transaction putTransaction(@PathVariable Long id,
                                      @RequestBody Map<String,Object> payLoad){
        Double amount = ((Number)payLoad.get("amount")).doubleValue();
        String type = (String) payLoad.get("type");
        Long parentId = payLoad.containsKey("parent_id") ? ((Number)payLoad.get("parent_id")).longValue():null;

        return service.putTransaction(id,amount,type,parentId);
    }

    @GetMapping("/transaction/{id}")
    public Optional<Transaction> getTransaction(@PathVariable Long id){
        return service.getTransaction(id);
    }

    @GetMapping("/transaction/types/{type}")
    public List<Long> getTransactionIdByType(@PathVariable String type){
        return service.getTransactionByType(type);
    }

    @GetMapping("/transaction/sum/{id}")
    public ResponseEntity<JsonNode> getSum(@PathVariable Long id) throws JsonProcessingException {
        JSONObject res = service.getSum(id);
        return new ResponseEntity<>(new ObjectMapper().readTree(res.toString()),HttpStatus.OK);
    }

}
