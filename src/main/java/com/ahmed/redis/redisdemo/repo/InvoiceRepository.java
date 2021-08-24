package com.ahmed.redis.redisdemo.repo;

import com.ahmed.redis.redisdemo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
}
