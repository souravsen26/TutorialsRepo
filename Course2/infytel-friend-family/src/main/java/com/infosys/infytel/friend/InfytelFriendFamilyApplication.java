package com.infosys.infytel.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.infosys.infytel.friend.service.FriendFamilyService;

@SpringBootApplication
public class InfytelFriendFamilyApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(InfytelFriendFamilyApplication.class, args);
		
		FriendFamilyService service = app.getBean(FriendFamilyService.class) ;
		service.loadData();
		
	}

}
