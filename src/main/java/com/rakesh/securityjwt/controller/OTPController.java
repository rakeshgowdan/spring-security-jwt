package com.rakesh.securityjwt.controller;

import java.security.Principal;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.securityjwt.dto.CommonResponse;
import com.rakesh.securityjwt.dto.UserDTO;
import com.rakesh.securityjwt.service.EmailService;
import com.rakesh.securityjwt.service.OTPService;
import com.rakesh.securityjwt.service.UserServiceImple;

@RestController
@RequestMapping("/api/v1/otp-service")
public class OTPController {
	
	@Autowired
	private UserServiceImple userDetailServiceHandler;
	@Autowired
	private EmailService emailService;
	@Autowired
	private OTPService otpService;

	@GetMapping("/generate")
	public CommonResponse generateOtp(Principal authentication) throws MessagingException {
		
		//getUserDetails
		UserDetails details=null;
		if(authentication!=null) {
			details=userDetailServiceHandler.loadUserByUsername(authentication.getName());
		}
		
		//Otp code
	
		int otp = otpService.generateOTP(details.getUsername());
		//Generate The Template to send OTP 
		//EmailTemplate template = new EmailTemplate("SendOtp.html");
		//Map replacements = new HashMap();
		//replacements.put("user", username);
		//replacements.put("otpnum", String.valueOf(otp));
		//String message = template.getTemplate(replacements);

		UserDTO user=(UserDTO) details;
		System.err.println(user.getMailId());
		emailService.sendOtpMessage(user.getMailId(), "OTP Verification Email", String.valueOf(otp));
		
		
		return new CommonResponse(200, false, "Otp sent successfully", new Date(), details, null);
	}
	
	@GetMapping("/validate")
	public CommonResponse validateOtp(@RequestParam("otpnum") int otpnum) {
		
		final String SUCCESS = "Otp is valid";
		final String FAIL = "Otp is NOT valid";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
	
		if(otpnum >= 0){
			
		  int serverOtp = otpService.getOtp(username);
		
			if(serverOtp > 0){
			  if(otpnum == serverOtp){
				  otpService.clearOTP(username);
		
				  return new CommonResponse(200, false, SUCCESS, new Date(), null, null);
				} 
				else {
					 return new CommonResponse(401, false, FAIL, new Date(), null, null);
				   }
			   }else {
				   return new CommonResponse(401, false, FAIL, new Date(), null, null);
			   }
			 }else {
				 return new CommonResponse(401, false, FAIL, new Date(), null, null);
		 }
	  
	}
}
