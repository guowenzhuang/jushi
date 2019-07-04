package com.jushi.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVM {
	@NotEmpty
	@Size(min = 1, max = 50)
	private String username;

	@NotEmpty
	private String password;
}
