package com.infosys.infytel.friend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.infytel.friend.dto.FriendFamilyDTO;
import com.infosys.infytel.friend.entity.FriendFamily;
import com.infosys.infytel.friend.repository.FriendFamilyRepository;

@Service
public class FriendFamilyService {

	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	FriendFamilyRepository friendRepo ;
	
	// Create Friend Family
	
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) {
		logger.info("Creation request for customer "+ phoneNo + " with data "+ friendDTO);
		friendDTO.setPhoneNo(phoneNo);
		FriendFamily friendFamily = friendDTO.createFriend();
		friendRepo.save(friendFamily) ;		
	}
	
	// Get friend and family phone number list of a given customer
	
	public List<Long> getSpecificFriends(Long phoneNo){
		logger.info("Friend and family details forCustomer" + phoneNo);
		List<FriendFamily> friends= friendRepo.getByPhoneNo(phoneNo) ;
		List<Long> friendList =friends.stream().map(friendFamily->{
			return friendFamily.getFriendAndFamily() ;
		}).collect(Collectors.toList()) ;
		logger.info("The friend List is for customer"+phoneNo+ " is "+friendList);
		return friendList ;
	}
	
	
}
