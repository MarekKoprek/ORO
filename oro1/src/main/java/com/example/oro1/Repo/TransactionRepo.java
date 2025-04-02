package com.example.oro1.Repo;

import com.example.oro1.Model.Customer;
import com.example.oro1.Model.Part;
import com.example.oro1.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    long countByPart(Part part);
    long countByCustomer(Customer customer);
    long countByCustomerEmail(String email);
}
