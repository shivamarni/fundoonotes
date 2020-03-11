package com.bridgelabz.fundoonotes.utils;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
@Component
public class JWTGenerator {
	public static final String SECRET="1234567890";
	public  String jwtToken(long l)

	{
		String token=null;
		try
		{
			token=JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return token;
	}
	public Integer parseJWT(String jwt)
	{
		Integer userId=(Integer)0;
		if(jwt!=null)
		{
			userId=JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwt).getClaim("id").asInt();
		}
		return userId;
	}


}
