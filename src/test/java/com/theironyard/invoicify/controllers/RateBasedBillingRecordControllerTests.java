package com.theironyard.invoicify.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.RateBasedBillingRecord;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;

public class RateBasedBillingRecordControllerTests {
	
	private RateBasedBillingRecordController controller;
	private BillingRecordRepository repo;
	private CompanyRepository companyRepo;

	
	@Before
	public void setup() {
		repo = mock(BillingRecordRepository.class);	
		companyRepo = mock(CompanyRepository.class);
		
		controller = new RateBasedBillingRecordController(repo, companyRepo);
	}

	@Test
	public void test_create() {
		
//		RateBasedBillingRecord rateRecord = new RateBasedBillingRecord();
//		rateRecord.setId(4L);
//		
//		Company client = new Company();
//		client.setId(4L);
//		rateRecord.setClient(client);
//		
//		Authentication auth = mock(Authentication.class);
//		
//		repo.save(rateRecord);
//		
//		ModelAndView actual = controller.create(rateRecord, client.getId(), auth);
//		
		//assertThat(actual).
		
		

	}

}
