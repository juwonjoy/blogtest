package com.cos.blogtest.web;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogtest.domain.user.User;
import com.cos.blogtest.domain.user.UserRepository;
import com.cos.blogtest.web.dto.JoinReqDto;
import com.cos.blogtest.web.dto.LoginReqDto;


@Controller
public class UserController {

	private UserRepository userRepository;
	private HttpSession session;
	
	// DI
	public UserController(UserRepository userRepository, HttpSession session) {
		this.userRepository = userRepository;
		this.session = session;
	}
	
	@GetMapping("/test/query/join")
	public void testQueryJoin() {
		userRepository.join("cos", "1234", "cos@nate.com");
	}
	
	@GetMapping("/test/join")
	public void testJoin() {
		User user = new User();
		user.setUsername("juwon");
		user.setPassword("1234");
		user.setEmail("juwon@naver.com");
		
		userRepository.save(user);
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	

	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@PostMapping("/login")
	public String login(LoginReqDto dto) {
		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());
		User userEntity =  userRepository.mLogin(dto.getUsername(), dto.getPassword());
		
		if(userEntity == null) {
			return "redirect:/loginForm";
		}else {
			
			session.setAttribute("principal", userEntity);
			return "redirect:/home";
		}
	}
	
	@PostMapping("/join")
	public String join(JoinReqDto dto) { 
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm";
	}
	
}


