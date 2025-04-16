package com.ums.web;

import com.ums.bean.*;
import com.ums.repository.ProductRepository;
import com.ums.repository.UserHistoryRepository;
import com.ums.repository.UserRepository;
import com.ums.service.RoutingService;
import com.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component("userController")
@Scope("request")
public class UserController {
	@Autowired
	private User user;

	@Autowired
	private RoutingService routingService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CSRFToken csrfToken;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UIDto uiDto;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private Product product;

	public void loadSignup() {
		routingService.routeTo("signup.jsf");
	}

	public void loadSignin() {
		routingService.routeTo("signin.jsf");
	}

	public void signup() {
		System.out.println(user);
		if(userRepository.findByUsername(user.getUsername()).isPresent()){
			routingService.addMessage("User already exists");
		}else {
			user.setUsername(user.getUsername().toLowerCase());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User user1 = userRepository.save(user);
			if (user1 != null) {
				routingService.addMessage("Signed up successfully");
			} else {
				routingService.addMessage("Error signing up");
			}
		}
	}

	public void signout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request =
				(HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpServletResponse response =
				(HttpServletResponse) facesContext.getExternalContext().getResponse();

		SecurityContextLogoutHandler securityContextHolder = new SecurityContextLogoutHandler();
		securityContextHolder.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		// Invalidate JSF session as well
		facesContext.getExternalContext().invalidateSession();
		routingService.addMessage("Logged out successfully");
		routingService.routeTo("signin.xhtml");
	}

	public User user(){
		return user;
	}

	public UIDto getUiDto(){
		loadUserDetails();
		return this.uiDto;
	}

	public void loadUserDetails() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(userOpt.isPresent()){
			System.out.println("User found: "+userOpt.get().getUsername());
			List<UserHistory> userHistory = userService.processUserSignIn(userOpt.get());
			user.setUsername(userOpt.get().getUsername());
			user.setRole(userOpt.get().getRole());
			user.setNoOfTimesLoggedIn(userOpt.get().getNoOfTimesLoggedIn());
			user.setUserHistories(userHistory);
			user.setEmailId(userOpt.get().getEmailId());
			user.setPhNo(userOpt.get().getPhNo());
			System.out.println(user.getUserHistories());
			uiDto.setMessage("Welcome: "+userOpt.get().getUsername().toUpperCase()+" "+userOpt.get().getRole());
			uiDto.setUser(user);
			//routingService.addMessage("Welcome: "+userOpt.get().getUsername().toUpperCase());
			//routingService.routeTo("homepage.xhtml");
		}
	}

	public void saveUser(){
		//UIDto temp = getUiDto();
		System.out.println("temp: "+user);
		User user1 = userRepository.findByUsername(user.getUsername()).get();
		user1.setEmailId(user.getEmailId());
		user1.setPhNo(user.getPhNo());
		userRepository.save(user1);
		routingService.routeTo("profile.xhtml");
	}

	public List<Product> products(){
		return productRepository.findAll();
	}

	public void addProduct(){
		productRepository.save(product);
		routingService.routeTo("products.xhtml");
	}


}
