package com.jushi.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duc-d
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken {
	private String token;
}
