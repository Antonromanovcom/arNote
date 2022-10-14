package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalUserRq {

	private String login;

	private String email;

	private String fullName;

	private String pwd; //todo: везде проставить валидацию (спринг-валидатор). Во всех входных ДТО-шках
}
