package com.infosys.calldetails.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.calldetails.dto.CallDetailsDTO;
import com.infosys.calldetails.entity.CallDetails;
import com.infosys.calldetails.repository.CallDetailsRepository;

@Service
public class CallDetailsService {

	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	CallDetailsRepository callDetailsRepo;
	
	public List<CallDetailsDTO> getCustomerCallDetails(@PathVariable long phoneNo){
		
		logger.info("CallDetails request for cusyomer" + phoneNo);
		
		List<CallDetails> callDetails = callDetailsRepo.findByCalledBy(phoneNo) ;
		List<CallDetailsDTO> callsDTO = new ArrayList<>() ;
		
		for(CallDetails call : callDetails) {
			callsDTO.add(CallDetailsDTO.valueOf(call)) ;
		}
		
		return callsDTO ;
	}
	
	
	
	
}
