package hu.dual.invoices.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import hu.dual.invoices.entities.Invoice;
import hu.dual.invoices.entities.InvoiceItem;
import hu.dual.invoices.service.InvoiceService;

@Named
@RequestScoped
public class InvoiceBean {
	
	@Inject
	private InvoiceService invoiceService;
	
	private List<Invoice> invoices;
	
	private Invoice currentInvoice;
	
	private List<InvoiceItem> currentInvoiceItems;

	public List<InvoiceItem> getCurrentInvoiceItems() {
		return currentInvoiceItems;
	}

	public void setCurrentInvoiceItems(List<InvoiceItem> currentInvoiceItems) {
		this.currentInvoiceItems = currentInvoiceItems;
	}

	public Invoice getCurrentInvoice() {
		return currentInvoice;
	}

	public void setCurrentInvoice(Invoice currentInvoice) {
		this.currentInvoice = currentInvoice;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	@PostConstruct
	public void init() {
		invoices = invoiceService.listAllInvoices();
	}
	
	public List<Invoice> getInvoices() {
		return invoices;
	}
	
	public String getItemsOfInvoice() {
		int currentInvoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedInvoiceId"));
		currentInvoice = invoices.stream().filter(invoice -> invoice.getId() == currentInvoiceId).findFirst().get();
		currentInvoiceItems = invoiceService.listItemsOfInvoice(currentInvoiceId);
		return "itemsOfInvoiceList";
	}
	
}
