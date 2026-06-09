package com.infosys.infytel.friend.service;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public void loadData() {
		
		List<long[]> rawDataList = Arrays.asList(
		        new long[]{1L, 9898989899L},
		        new long[]{2L, 9876543211L},
		        new long[]{3L, 9876543211L},
		        new long[]{14L, 9876543211L},
		        new long[]{15L, 9876543211L},
		        new long[]{6L, 9876543211L},
		        new long[]{7L, 9876543211L},
		        new long[]{8L, 9876543211L},
		        new long[]{26L, 9876543211L},
		        new long[]{10L, 9876543211L},
		        new long[]{11L, 9876543211L},
		        new long[]{12L, 9876543211L},
		        new long[]{13L, 9876543211L},
		        new long[]{4L, 9876543211L},
		        new long[]{5L, 9876543211L},
		        new long[]{16L, 9876543211L},
		        new long[]{17L, 9876543211L},
		        new long[]{18L, 9876543211L},
		        new long[]{19L, 9876543211L},
		        new long[]{20L, 9876543211L},
		        new long[]{21L, 9898989898L},
		        new long[]{22L, 9898989898L},
		        new long[]{23L, 9898989898L},
		        new long[]{24L, 9898989898L},
		        new long[]{25L, 9898989898L},
		        new long[]{27L, 9876445569L},
		        new long[]{28L, 9876445569L},
		        new long[]{29L, 9876445569L},
		        new long[]{30L, 9876445569L},
		        new long[]{31L, 9876445569L},
		        new long[]{32L, 9876445569L},
		        new long[]{33L, 9876445866L},
		        new long[]{34L, 9876445866L},
		        new long[]{35L, 9876445866L},
		        new long[]{36L, 9898989899L},
		        new long[]{37L, 9898989899L},
		        new long[]{38L, 9898989899L},
		        new long[]{39L, 9876445469L},
		        new long[]{40L, 9876445469L}
		    );

		List<FriendFamily> pojos = rawDataList.stream()
	            .map(data -> new FriendFamily(data[1], data[0]))
	            .collect(Collectors.toList());
		
		friendRepo.saveAll(pojos) ;
	}
	
	
	
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
