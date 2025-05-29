package com.etshn.stock;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.etshn.stock.entity.Cachier;
import com.etshn.stock.entity.Role;
import com.etshn.stock.entity.Type;
import com.etshn.stock.repository.CachierRepository;
import com.etshn.stock.repository.RoleRepository;
import com.etshn.stock.repository.TypeRepository;

@SpringBootApplication
public class EtsHnStockApplication implements CommandLineRunner{
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Autowired
	private CachierRepository cachierRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EtsHnStockApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Override
	public void run(String... args) throws Exception {
		List<Role> roles = roleRepository.findAll();
		List<Type> types = typeRepository.findAll();
		List<Cachier> cachiers = cachierRepository.findAll();
		
		if(roles.size() == 0) {
			Role user = new Role("ROLE_USER");
			Role admin = new Role("ROLE_ADMIN");
			roleRepository.saveAll(List.of(user, admin));
		}
		
		if(types.size() == 0) {
			Type entree = new Type((long) 1,"ENTREE");
			Type sortie = new Type((long) 2,"SORTIE");
			typeRepository.saveAll(List.of(entree, sortie));
		}
		
		if(cachiers.size() == 0) {
			Cachier cachier = new Cachier((long) 1, BigDecimal.valueOf(0));
			cachierRepository.save(cachier);
		}
		
	}

}
