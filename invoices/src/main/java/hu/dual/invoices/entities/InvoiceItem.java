package hu.dual.invoices.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "INVOICE_ITEM")
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@NotNull
	@Column(name = "product_name")
	private String productName;
	
	@NotNull
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	
	@NotNull
	private BigDecimal quantity;
	
	@NotNull
	@Column(name = "total_item_price")
	private BigDecimal totalItemPrice;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "INVOICE_ID")
	private Invoice invoice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(BigDecimal totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
}
