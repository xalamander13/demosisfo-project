package com.coder.demosisfo.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coder.demosisfo.model.ERole;
import com.coder.demosisfo.model.Role;
import com.coder.demosisfo.model.User;
import com.coder.demosisfo.payload.JwtResponse;
import com.coder.demosisfo.payload.request.LoginRequest;
import com.coder.demosisfo.payload.request.RegisterRequest;
import com.coder.demosisfo.repository.RoleRepository;
import com.coder.demosisfo.repository.UserRepository;
import com.coder.demosisfo.security.jwt.JwtUtils;
import com.coder.demosisfo.service.AuthService;

@Service("authService")
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	StudentServiceImpl studentServiceImpl;
	
	@Autowired
	JwtUtils jwtUtils;

	@Override
	public String registerUser(RegisterRequest registerRequest) {
		/* Create new user account */
		User user = new User(registerRequest.getFullname(),
							 registerRequest.getUsername(),
							 passwordEncoder.encode(registerRequest.getPassword()));
		
		Set<String> strRoles = registerRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
			roles.add(adminRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "dosen":
					Role dosenRole = roleRepository.findByName(ERole.ROLE_DOSEN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(dosenRole);
					break;
				
				case "staff":
					Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(staffRole);
					break;
					
				case "mahasiswa":
					Role mahasiswaRole = roleRepository.findByName(ERole.ROLE_MAHASISWA)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(mahasiswaRole);
					break;	

				default:
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(adminRole);
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);

		return "User registered successfully!";
	}

	@Override
	public JwtResponse jwtResponse(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		return new JwtResponse(jwt);
	}

}
