package com.theironyard.invoicify.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/admin/companies")
public class CompanyController {
	
	private CompanyRepository companyRepo;
	private InvoiceRepository invoiceRepo;
	
	public CompanyController(CompanyRepository companyRepo, 
			InvoiceRepository invoiceRepo) {
		this.companyRepo = companyRepo;
		this.invoiceRepo = invoiceRepo;
		
	}
	
	@GetMapping("")
	public ModelAndView companyList() {
		ModelAndView mv = new ModelAndView("companies/companyList");
		mv.addObject("companies", companyRepo.findAllByOrderByNameAsc());
		return mv;
		
	}
	
	@PostMapping("")
	public ModelAndView create(String name) {
			
		List<Invoice> invoices = new ArrayList<Invoice>();
		Company company = new Company();
		
		company.setName(name);
		company.setInvoices(invoices);
		
		companyRepo.save(company);
		
		return new ModelAndView("redirect:/admin/companies");
		
	}
	
	

}
