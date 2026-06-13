package com.infosys.infytel.customer;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

//@Component
public class LoadBalancerConfig {

	@Bean
//	@Primary
	ServiceInstanceListSupplier serviceInstanceListSupplier() {
		return new DemoServiceInstanceListSupplier("infytel-friend-family") ;
	}
	
}
	
	class DemoServiceInstanceListSupplier implements ServiceInstanceListSupplier{
		
		private final String serviceId ;
		
		DemoServiceInstanceListSupplier(String serviceId){
			this.serviceId=serviceId;
		}
		
		@Override
		public Flux<List<ServiceInstance>> get(){
			return Flux.just(Arrays.asList(new DefaultServiceInstance(serviceId+ "1", serviceId,"localhost",
					8081,false)
//					,new DefaultServiceInstance(serviceId+ "1", serviceId,"localhost",7400,false)
					)) ;
		}

		@Override
		public String getServiceId() {
			return serviceId;
		}
			
}
