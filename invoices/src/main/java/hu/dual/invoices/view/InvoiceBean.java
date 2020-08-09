package hu.dual.invoices.view;

import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hu.dual.invoices.entities.Invoice;
import hu.dual.invoices.entities.InvoiceItem;
import hu.dual.invoices.mnb.MNBArfolyamServiceSoap;
import hu.dual.invoices.mnb.MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage;
import hu.dual.invoices.mnb.MNBArfolyamServiceSoapImpl;
import hu.dual.invoices.service.InvoiceService;

@Named
@RequestScoped
public class InvoiceBean {
	
	@PostConstruct
	public void init() {
		invoices = invoiceService.listAllInvoices();
		getCurrentEurExchangeRateFromMNB();
	}
	
	@Inject
	private InvoiceService invoiceService;
	
	private List<Invoice> invoices;
	
	private Invoice currentInvoice;
	
	private List<InvoiceItem> currentInvoiceItems;
		
	private Double currentEurExchangeRate;
	
	public Double getCurrentEurExchangeRate() {
		return currentEurExchangeRate;
	}

	public void setCurrentEurExchangeRate(Double currentEurExchangeRate) {
		this.currentEurExchangeRate = currentEurExchangeRate;
	}

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

	public List<Invoice> getInvoices() {
		return invoices;
	}
	
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public String getItemsOfInvoice() {
		int currentInvoiceId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedInvoiceId"));
		currentInvoice = invoices.stream().filter(invoice -> invoice.getId() == currentInvoiceId).findFirst().get();
		currentInvoiceItems = invoiceService.listItemsOfInvoice(currentInvoiceId);
		return "/itemsOfInvoiceList";
	}
	
	public void getCurrentEurExchangeRateFromMNB() {
		MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
		MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();
		try {
			String resp = service.getCurrentExchangeRates();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(resp));
		    Document document =  builder.parse(is);
		    document.getDocumentElement().normalize();
		    NodeList nodeList = document.getElementsByTagName("Rate");
		    for (int temp = 0; temp < nodeList.getLength(); temp++) {
		    	Element element = (Element) nodeList.item(temp);
		    	if ("EUR".equals(element.getAttribute("curr"))) {
				    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		    		currentEurExchangeRate = format.parse(nodeList.item(temp).getTextContent()).doubleValue();
		    	}
		    }
		} catch (MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
