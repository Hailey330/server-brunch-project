package com.project.brunch.domain.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String password;
	private String snsId; // @앞 아이디 파싱한거
	private String nickName;
	private String email;
	private String bio; 
	private String profileImage;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private String provider;
	private String providerId;

}