package com.infosys.infytel.friend.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.infosys.infytel.friend.dto.FriendFamilyDTO;
import com.infosys.infytel.friend.service.FriendFamilyService;

public class FriendFamilyController {

	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	FriendFamilyService friendService ;
	
	// Create Friend Family
	@PostMapping(value="/customers/{phoneNo}/friends", consumes= MediaType.APPLICATION_JSON_VALUE)
	public void saveFriend(@PathVariable Long phoneNo, @RequestBody FriendFamilyDTO friendDTO) {
		logger.info("Creation request for customer"+friendDTO) ;
		friendService.saveFriend(phoneNo,friendDTO) ;
	}
	
	@GetMapping(value="/customers/{phoneNo}/friends",consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Long> getSpecificFriends(@PathVariable Long phoneNo){
		logger.info("Friend and Family numbers for customer"+phoneNo) ;
		return friendService.getSpecificFriends(phoneNo);
		
	}
	
	
}
