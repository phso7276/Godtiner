package com.cos.jwtex01.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtex01.config.jwt.JwtProperties;
import com.cos.jwtex01.config.oauth.provider.GoogleUser;
import com.cos.jwtex01.config.oauth.provider.OAuthUserInfo;
import com.cos.jwtex01.entity.User;
import com.cos.jwtex01.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/oauth/jwt/google")
	public String jwtCreate(@RequestBody Map<String, Object> data) {
		System.out.println("jwtCreate 실행됨");
		System.out.println(data.get("profileObj"));
		OAuthUserInfo googleUser = 
				new GoogleUser((Map<String, Object>)data.get("profileObj"));
		
/*		User userEntity =
				userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());*/
		User userEntity =
				userRepository.findByEmail(googleUser.getEmail());
		
		if(userEntity == null) {
			User userRequest = User.builder()
					.name(googleUser.getName())
					.password(bCryptPasswordEncoder.encode("google11"))
					.email(googleUser.getEmail())
					.provider(googleUser.getProvider())
					.providerId(googleUser.getProviderId())
					.roles("ROLE_USER")
					.build();
			
			userEntity = userRepository.save(userRequest);
		}
		
		String jwtToken = JWT.create()
				.withSubject(userEntity.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getEmail())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return jwtToken;
	}

	@PostMapping("/oauth/jwt/notSocial")
	public String jwtCreateNotSocial(@RequestBody User user) {
		System.out.println("jwtCreate 실행됨");
		System.out.println(user.getEmail());
		/*OAuthUserInfo notSocialUser =
				new User();*/

		/*User userEntity =
				userRepository.findByUsername(user.getProvider()+"_"+user.getProviderId());*/
		User userEntity =
				userRepository.findByEmail(user.getEmail());
/*
		if(userEntity == null) {
			User userRequest = User.builder()
					.username(user.getProvider()+"_"+user.getProviderId())
					.password(bCryptPasswordEncoder.encode("겟인데어"))
					.email(user.getEmail())
					.provider(user.getProvider())
					.providerId(user.getProviderId())
					.roles("ROLE_USER")
					.build();

			userEntity = userRepository.save(userRequest);
		}
*/

		String jwtToken = JWT.create()
				.withSubject(userEntity.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", userEntity.getId())
				.withClaim("username", userEntity.getEmail())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));

		return jwtToken;
	}
	
}
