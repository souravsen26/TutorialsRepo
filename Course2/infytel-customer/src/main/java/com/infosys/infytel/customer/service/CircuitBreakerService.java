package com.infosys.infytel.customer.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.PlanDTO;

@Service
public class CircuitBreakerService {

	Log logger = LogFactory.getLog(getClass());

	@Autowired
	RestTemplate restTemplate;

	public CompletableFuture<PlanDTO> getPlan(CustomerDTO custDTO) throws InterruptedException {

//		Thread.sleep(5000);
		return CompletableFuture.supplyAsync(() -> {
	        PlanDTO planDTO = restTemplate.getForObject(
	            "http://infytel-plan/plans/" + custDTO.getCurrentPlan().getPlanId(),
	            PlanDTO.class
	        );
	        custDTO.setCurrentPlan(planDTO);
	        logger.info(planDTO);
	        return planDTO;  // <-- must return here
	    });
	}

	public CompletableFuture<List<Long>> getInfytelFriendFamily(Long phoneNo) throws InterruptedException {

		
//		@SuppressWarnings("unchecked")
//		List<Integer> friends = Future.of(()->restTemplate
//				.getForObject("http://infytel-friend-family/customers/" + phoneNo + "/friends", List.class));
//		List<Long> friendList = friends.stream().map(f -> {
//			return Long.valueOf(f);
//		}).collect(Collectors.toList());
//
//		return friendList;
		
		return CompletableFuture.supplyAsync(()	->	{
			List<Integer> friend = restTemplate.getForObject(
					"http://infytel-friend-family/customers/"+phoneNo + "/friends",
					List.class) ;
			List<Long> friendList = friend.stream().map(f -> { return Long.valueOf(f); }).collect(Collectors.toList());
			return friendList ;
		}) ;	
	}
}
