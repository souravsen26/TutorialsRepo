package com.infosys.infytel.customer.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.PlanDTO;
import com.infosys.infytel.customer.service.CircuitBreakerService;
import com.infosys.infytel.customer.service.CustomerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@RestController
public class CustomerController2 {

	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	CustomerService custService ;
	
//	@Value("${friend.uri}")
	String friendUri ;
	
//	@Value("${plan.uri}")
	String planUri ;
	
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	RestTemplate restTemplate ;
	
	@Autowired
	CircuitBreakerService circuitBreakerService;
	
	/**
	 * @apiNote: This is version 3 of the /customers/{phoneNo} endpoint This is to implement the load balancing
	 * concept dynamically. The RestTemplate is a load balanced one and the restTemplate interacts with consul to get the instances
	 * of the required services dynamically.
	 * @param phoneNo
	 * @return
	 */
	
	@RequestMapping(value="/customers/v3/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfilev3(@PathVariable Long phoneNo) {
		logger.info("V3 getCustomerProfilev3 Invoked") ;
		logger.info("phoneNo");
		logger.info("Profile Request for customer" + phoneNo) ;
		CustomerDTO custDTO = custService.getCustomerProfile(phoneNo) ;
//		PlanDTO planDTO = new RestTemplate().getForObject(planUri + custDTO.getCurrentPlan().getPlanId(), PlanDTO.class) ;
		List<ServiceInstance> planInstance=client.getInstances("infytel-plan");
		if(planInstance!=null && !planInstance.isEmpty()) {
			planUri = planInstance.get(0).getUri().toString() ;
		}
		PlanDTO planDTO = new RestTemplate().getForObject(planUri+"/plans/" + custDTO.getCurrentPlan().getPlanId(), PlanDTO.class) ;
		custDTO.setCurrentPlan(planDTO);
		logger.info(planDTO);
		
		@SuppressWarnings("unchecked")
//		List<Long> friends = new RestTemplate().getForObject(friendUri+phoneNo+"/friends",List.class);
		/*List<ServiceInstance> friendInstance=client.getInstances("infytel-friend-family");
		if(friendInstance!=null && !friendInstance.isEmpty()) {
			friendUri = friendInstance.get(0).getUri().toString() ;
		}
		List<Integer> friends = new RestTemplate().getForObject(friendUri+"/customers/"+phoneNo+"/friends",List.class);*/
		List<Integer> friends = restTemplate.getForObject("http://infytel-friend-family/customers/"+phoneNo+"/friends", List.class) ;
		logger.info(" Retrieved :::::: "+ friends) ;
		List<Long> friendsList= friends.stream().map(f-> {return Long.valueOf(f) ;}).collect(Collectors.toList()) ;
		logger.info("friendsList :" + friendsList) ;
		custDTO.setFriendAndFamily(friendsList) ;
		logger.info("About to return DTO: " + custDTO);
		return custDTO ;
	}
	
	/**
	 * @apiNote: This is version 4 of the /customers/{phoneNo} endpoint This is to implement the 
	 * Resilience4j.
	 * of the required services dynamically.
	 * @param phoneNo
	 * @return
	 */

	@RequestMapping(value="/customers/v4/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name="customerService", fallbackMethod="getCustomerProfileFallback")
	public CustomerDTO getCustomerProfilev4(@PathVariable Long phoneNo) {
		logger.info("V3 getCustomerProfilev3 Invoked") ;
		logger.info("phoneNo");
		logger.info("Profile Request for customer" + phoneNo) ;
		CustomerDTO custDTO = custService.getCustomerProfile(phoneNo) ;
//		PlanDTO planDTO = new RestTemplate().getForObject(planUri + custDTO.getCurrentPlan().getPlanId(), PlanDTO.class) ;
		List<ServiceInstance> planInstance=client.getInstances("infytel-plan");
		if(planInstance!=null && !planInstance.isEmpty()) {
			planUri = planInstance.get(0).getUri().toString() ;
		}
		PlanDTO planDTO = new RestTemplate().getForObject(planUri+"/plans/" + custDTO.getCurrentPlan().getPlanId(), PlanDTO.class) ;
		custDTO.setCurrentPlan(planDTO);
		logger.info(planDTO);
		
		@SuppressWarnings("unchecked")
//		List<Long> friends = new RestTemplate().getForObject(friendUri+phoneNo+"/friends",List.class);
		/*List<ServiceInstance> friendInstance=client.getInstances("infytel-friend-family");
		if(friendInstance!=null && !friendInstance.isEmpty()) {
			friendUri = friendInstance.get(0).getUri().toString() ;
		}
		List<Integer> friends = new RestTemplate().getForObject(friendUri+"/customers/"+phoneNo+"/friends",List.class);*/
		List<Integer> friends = restTemplate.getForObject("http://infytel-friend-family/customers/"+phoneNo+"/friends", List.class) ;
		logger.info(" Retrieved :::::: "+ friends) ;
		List<Long> friendsList= friends.stream().map(f-> {return Long.valueOf(f) ;}).collect(Collectors.toList()) ;
		logger.info("friendsList :" + friendsList) ;
		custDTO.setFriendAndFamily(friendsList) ;
		logger.info("About to return DTO: " + custDTO);
		return custDTO ;
	}
	
	
	
	/**
	 * @apiNote: This is version 5 of the /customers/{phoneNo} endpoint This is to implement the 
	 * async version.
	 * of the required services dynamically.
	 * @param phoneNo
	 * @return
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */

	@RequestMapping(value="/customers/v5/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name="customerService", fallbackMethod="getCustomerProfileFallback")
	public CustomerDTO getCustomerProfilev5(@PathVariable Long phoneNo) throws InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		logger.info("V5 getCustomerProfilev5 Invoked") ;
		logger.info("phoneNo");
		logger.info("Profile Request for customer" + phoneNo) ;
		CustomerDTO custDTO = custService.getCustomerProfile(phoneNo) ;
		CompletableFuture<PlanDTO> planDTO = circuitBreakerService.getPlan(custDTO) ;
		CompletableFuture<List<Long>> friends = circuitBreakerService.getInfytelFriendFamily(phoneNo);
		PlanDTO planDto = planDTO.get() ;
		List<Long> friendList = friends.get() ;
		logger.info(" Retrieved :::::: "+ friendList) ;
		logger.info("About to return DTO: " + custDTO);
		long endTime = System.currentTimeMillis() ;
		logger.info("Time taken by request is :::::::" + Math.abs((Math.subtractExact(endTime, startTime))/1000)) ;
		return custDTO ;
	}
	
	public CustomerDTO getCustomerProfileFallback(Long pnoneNo, Throwable throwable) {
		logger.info("=========== FALLBACK ==============");
		return new CustomerDTO();
	}
}
