package com.etshn.stock.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.etshn.stock.payload.LoginDto;
import com.etshn.stock.payload.RegisterDto;
import com.etshn.stock.payload.UserDto;
import com.etshn.stock.repository.RoleRepository;
import com.etshn.stock.repository.UserRepository;
import com.etshn.stock.security.JwtTokenProvider;
import com.etshn.stock.service.AuthService;
import com.etshn.stock.entity.Role;
import com.etshn.stock.entity.User;
import com.etshn.stock.exception.BlogAPIException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper mapper;



    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			ModelMapper mapper) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.mapper = mapper;
	}

	@Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    
    private UserDto mapToDTO(User entity){
    	UserDto dto = mapper.map(entity, UserDto.class);
        return dto;
    }
    
    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

	@Override
	public UserDto findByUsername(String username) {
		User u = userRepository.findByUsername(username);
		return mapToDTO(u);
	}
	
    @Override
    public String register(RegisterDto registerDto) {

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
//        if(userRepository.existsByEmail(registerDto.getEmail())){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
//        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getName());
        user.setActive(registerDto.getActive());

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }
}
