package com.example.challenge.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {

	@GetMapping("/set")
	public String setSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("username", "password");
		return "redirect:/";
	}
	
	@GetMapping("/get")
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("Username from session: " + username);
        return "redirect:/";
    }
	
}
