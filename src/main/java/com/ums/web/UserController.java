package com.ums.web;

import com.ums.bean.User;
import com.ums.bean.UserHistory;
import com.ums.repository.UserHistoryRepository;
import com.ums.repository.UserRepository;
import com.ums.service.RoutingService;
import com.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.util.Date;
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


	public void submit(){

	}

	public void loadSignup() {
		routingService.routeTo("signup.xhtml");
	}

	public void loadSignin() {
		routingService.routeTo("signin.xhtml");
	}
	public void signin() {
		Optional<User> userOpt = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(userOpt.isPresent()){
			System.out.println("User found: "+userOpt.get().getUsername());
			List<UserHistory> userHistory = userService.processUserSignIn(userOpt.get());
			user.setNoOfTimesLoggedIn(userOpt.get().getNoOfTimesLoggedIn());
			user.setUserHistories(userHistory);
			System.out.println(user.getUserHistories());
			routingService.addMessage("Welcome: "+userOpt.get().getUsername().toUpperCase());
			routingService.routeTo("homepage.xhtml");
		}else {
			routingService.addMessage("Invalid username or password");
		}
	}

	public void signup() {
		System.out.println(user);
		if(userRepository.findByUsername(user.getUsername()).isPresent()){
			routingService.addMessage("User already exists");
		}else {
			user.setUsername(user.getUsername().toLowerCase());
			User user1 = userRepository.save(user);
			if (user1 != null) {
				routingService.addMessage("Signed up successfully");
			} else {
				routingService.addMessage("Error signing up");
			}
		}
	}

	public void signout(){
		routingService.addMessage("Logged out successfully");
		routingService.routeTo("signin.xhtml");
	}

	public User user(){
		return user;
	}



//	public void reset() {
//	    formBean.getSubmittedValues().clear();
//	    formBean.setField(null);
//
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Form reset."));
//	}

}
