package hu.dual.invoices.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import hu.dual.invoices.entities.Invoice;
import hu.dual.invoices.entities.InvoiceItem;

@Stateless
public class InvoiceService {
	
	private  static EntityManager entityManager = Persistence.createEntityManagerFactory("invoices").createEntityManager();
	
	public List<Invoice> listAllInvoices() {
		return entityManager.createQuery("FROM Invoice i", Invoice.class).getResultList();
	}
	
	public List<InvoiceItem> listItemsOfInvoice(int id) {
		return entityManager.createQuery("FROM InvoiceItem i WHERE i.invoice.id =" + id, InvoiceItem.class).getResultList();
	}
	
}
