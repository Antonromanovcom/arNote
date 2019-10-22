package com.antonromanov.arnote.utils;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.repositoty.UsersRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.Principal;
import java.time.LocalDateTime;
import static com.antonromanov.arnote.utils.Utils.defineUserActionByMethodSignature;

@Aspect
@Component
public class AopService {

	@Autowired
	UsersRepo usersRepo;


	@Before("execution(* com.antonromanov.arnote.controller.MainRestController.*(..)) && args(principal,..)")
	public void beforeAdvice(JoinPoint joinPoint, Principal principal) {

		if (principal != null) {
			if (usersRepo.findByLogin(principal.getName()).isPresent()) {
				LocalUser localUser = usersRepo.findByLogin(principal.getName()).get();
				localUser.setLastOperation(defineUserActionByMethodSignature(joinPoint.getSignature()));
				localUser.setLastOperationTime(LocalDateTime.now());
				usersRepo.save(localUser);
			}
		}
	}
}
