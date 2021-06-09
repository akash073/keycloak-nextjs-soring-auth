package com.dsi.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ResourceController {

	@GetMapping({"/index","/index/hello"})
	public String getIndex()
	{
		return "Index";
	}
	
	@GetMapping("/admin")
	public String getAdmin()
	{
		return "Hello admin";
	}

	@GetMapping("/user")
	public String getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<SimpleGrantedAuthority> grantedAuthorities = authentication.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList()); // (1)



		return "Hello user";
	}

}
