package com.infosys.infytel.customer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.LoadBalancerConfig;
import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.LoginDTO;
import com.infosys.infytel.customer.dto.PlanDTO;
import com.infosys.infytel.customer.service.CustomerService;

@RestController
@CrossOrigin
@LoadBalancerClient(name="MyloadBalancer" , configuration=LoadBalancerConfig.class)
public class CustomerController {
	
	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	CustomerService custService ;
	
//	@Value("${friend.uri}")
	String friendUri ;
	
//	@Value("${plan.uri}")
	String planUri ;
	
	@Autowired
	RestTemplate restTemplate ;
	
	@Autowired
	private DiscoveryClient client;
	
	@RequestMapping(value="/customers", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(@RequestBody CustomerDTO custDTO) {
		logger.info("creation request for customer" + custDTO) ;
		custService.createCustomer(custDTO) ;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@RequestBody LoginDTO loginDTO) {
		logger.info("login request for customer" + loginDTO) ;
		return custService.login(loginDTO) ;
	}
	
	/**
	 * @apiNote: This endpoint further calls two endpoints one of infytel-friend-family & infytel-plan 
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value="/customers/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) {
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
		List<ServiceInstance> friendInstance=client.getInstances("infytel-friend-family");
		if(friendInstance!=null && !friendInstance.isEmpty()) {
			friendUri = friendInstance.get(0).getUri().toString() ;
		}
//		List<Integer> friends = new RestTemplate().getForObject(friendUri+phoneNo+"/friends",List.class);
		List<Integer> friends = new RestTemplate().getForObject(friendUri+"/customers/"+phoneNo+"/friends",List.class);
		List<Long> friendsList= friends.stream().map(f-> {return Long.valueOf(f) ;}).collect(Collectors.toList()) ;
		custDTO.setFriendAndFamily(friendsList) ;
		return custDTO ;
	}	
	
	/**
	 * @apiNote : This endpoint is a static loadbalanced version of the endpoint /customers/{phoneNo}. In this
	 * the infytel-friend-family is expected two instances each running in port 8081 & 7300.
	 * @param phoneNo
	 * @return
	 */
	
	@RequestMapping(value="/customers/v2/{phoneNo}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfilev2(@PathVariable Long phoneNo) {
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
//		List<ServiceInstance> friendInstance=client.getInstances("infytel-friend-family");
//		if(friendInstance!=null && !friendInstance.isEmpty()) {
//			friendUri = friendInstance.get(0).getUri().toString() ;
//		}
//		List<Integer> friends = new RestTemplate().getForObject(friendUri+phoneNo+"/friends",List.class);
		List<Integer> friends = restTemplate.getForObject("http://MyloadBalancer"+"/customers/"+phoneNo+"/friends",List.class);
		List<Long> friendsList= friends.stream().map(f-> {return Long.valueOf(f) ;}).collect(Collectors.toList()) ;
		custDTO.setFriendAndFamily(friendsList) ;
		return custDTO ;
	}
}