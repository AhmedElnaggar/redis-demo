package com.ahmed.redis.redisdemo.service;

import com.ahmed.redis.redisdemo.exception.InvoiceNotFoundException;
import com.ahmed.redis.redisdemo.model.Invoice;
import com.ahmed.redis.redisdemo.repo.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice saveInvoice(Invoice inv) {
        return invoiceRepository.save(inv);
    }

    @Override
    @CachePut(value="Invoice", key="#invId")
    public Invoice updateInvoice(Invoice inv, Integer invId) {
        Invoice invoice= invoiceRepository.findById(invId).orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found"));
        invoice.setInvAmount(inv.getInvAmount());
        invoice.setInvName(inv.getInvName());
        return invoiceRepository.save(invoice);
    }

    @Override
    @CacheEvict(value="Invoice", key="#invId")
    // @CacheEvict(value="Invoice", allEntries=true) //in case there are multiple entires to delete
    public void deleteInvoice(Integer invId) {
        Invoice invoice = invoiceRepository.findById(invId).orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found"));

         invoiceRepository.delete(invoice);
    }

    @Override
    @Cacheable(value="Invoice", key="#invId")
    public Invoice getOneInvoice(Integer invId) {
        Invoice invoice = invoiceRepository.findById(invId).orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found"));
        return invoice;
    }

    @Override
    @Cacheable(value="Invoice")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
