package com.infosys.infytel.customer.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.LoginDTO;
import com.infosys.infytel.customer.dto.PlanDTO;
import com.infosys.infytel.customer.service.CustomerService;

@RestController
@CrossOrigin
public class CustomerController {
	
	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	CustomerService custService ;
	
	@Value("${friend.uri}")
	String friendUri ;
	
	@Value("${plan.uri}")
	String planUri ;
	
	@RequestMapping(value="/customers", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(@RequestBody CustomerDTO custDTO) {
		logger.info("creation request for customer" + custDTO) ;
		custService.createCustomer(custDTO) ;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public void login(@RequestBody LoginDTO loginDTO) {
		logger.info("login request for customer" + loginDTO) ;
		custService.login(loginDTO) ;
	}
	
	@RequestMapping(value="/customers/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) {
		logger.info("Profile Request for customer" + phoneNo) ;
		CustomerDTO custDTO = custService.getCustomerProfile(phoneNo) ;
		PlanDTO planDTO = new RestTemplate().getForObject(planUri + custDTO.getCurrentPlan().getPlanId(), PlanDTO.class) ;
		custDTO.setCurrentPlan(planDTO);
		
		@SuppressWarnings("unchecked")
		List<Long> friends = new RestTemplate().getForObject(friendUri+phoneNo+"/friends",List.class); 
		custDTO.setFriendAndFamily(friends) ;
		
		return custDTO ;
	}
	
	
}
