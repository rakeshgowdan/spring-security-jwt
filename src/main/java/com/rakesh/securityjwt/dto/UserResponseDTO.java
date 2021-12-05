package com.rakesh.securityjwt.dto;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

	private String userName;
	private String Message;
	private String token;
	private Date date;
}
