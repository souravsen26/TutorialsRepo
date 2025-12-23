package com.infosys.infytel.plan.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.infytel.plan.dto.PlanDTO;
import com.infosys.infytel.plan.service.PlanService;

@RestController
@CrossOrigin
public class PlanController {

	Log logger = LogFactory.getLog(getClass()) ;
	
	@Autowired
	PlanService planService ;
	
	@GetMapping(value="/getAllPlans",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<PlanDTO> getAllPlans(){
		return planService.getAllPlans() ;
	}
	
	@GetMapping(value="/plans/{planId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public PlanDTO getSpecificPlans(@PathVariable Integer planId) {
		
		logger.info("Fetching plan with planId " + planId) ;
		return planService.getSpecificPlan(planId) ;
		
	}
	
}
