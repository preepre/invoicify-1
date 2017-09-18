package com.theironyard.invoicify.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.models.InvoiceLineItem;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
	
	private BillingRecordRepository billingRepo;
	private CompanyRepository companyRepo;
	private InvoiceRepository invoiceRepo;
	
	public InvoiceController(CompanyRepository companyRepo, 
			BillingRecordRepository billingRepo, 
			InvoiceRepository invoiceRepo) {
		this.companyRepo = companyRepo;
		this.billingRepo = billingRepo;
		this.invoiceRepo = invoiceRepo;
	}
	
	@GetMapping("")
	public ModelAndView list(Authentication auth) {
		User user = (User) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("invoices/list");
		List<Invoice> invoices = invoiceRepo.findAll();
		mv.addObject("user", user);
		mv.addObject("showTable", invoices.size() > 0);
		mv.addObject("invoices", invoices );
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView newInvoice() {
		ModelAndView mv = new ModelAndView("invoices/step-1");
	
		mv.addObject("companies", companyRepo.findAll());

		return mv;
	}
	
	@PostMapping("/new")
	public ModelAndView goToStep2(long clientId) {
		ModelAndView mv = new ModelAndView("invoices/step-2");
		mv.addObject("clientId", clientId);
		mv.addObject("records", billingRepo.findByClientIdAndLineItemIsNull(clientId));
		return mv;	
	}

	
	@PostMapping("/create")
	public ModelAndView createInvoice(Invoice invoice, long clientId, 
			long[] recordIds, Authentication auth) {
		
		ModelAndView mv;
//
		try {
			User creator = (User) auth.getPrincipal();
			List<BillingRecord> records = billingRepo.findByIdIn(recordIds); 
			long nowish = Calendar.getInstance().getTimeInMillis();
			Date now = new Date(nowish);
			
			//
			billingRepo.findAll();
			
			List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
			
			for(BillingRecord record: records) {
				InvoiceLineItem lineItem = new InvoiceLineItem();
				lineItem.setBillingRecord(record);
				lineItem.setCreatedBy(creator);
				lineItem.setCreatedOn(now);
				lineItem.setInvoice(invoice);
				items.add(lineItem);
			}
			
			invoice.setLineItems(items);
			invoice.setCompany(companyRepo.findOne(clientId));
			invoice.setCreatedBy(creator);
			invoice.setCreatedOn(now);
			
			invoiceRepo.save(invoice);
			
			mv = new ModelAndView("redirect:/invoices");
			
		}
		
		catch(InvalidDataAccessApiUsageException e){
//			System.out.println(e.getMessage());					
			mv = new ModelAndView("/invoices/step-2");
			mv.addObject("clientId", clientId);
			mv.addObject("billingRecords", billingRepo.findByClientIdAndLineItemIsNull(clientId));
			mv.addObject("errorMessage", "Please select at least one billing record");	
			
		}
		
		return mv;
		
		
	
		
	}
	
}

















