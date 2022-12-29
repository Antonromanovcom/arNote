package com.antonromanov.arnote.old.utils;


// @Aspect
// @Component
public class AopService {

	// @Autowired
	//UsersRepo usersRepo;

	/*@Before("execution(* com.antonromanov.arnote.sbdfvjbsdf.controller.MainRestController.*(..)) && args(principal,..)")
	public void beforeAdvice(JoinPoint joinPoint, Principal principal) {

		if (principal != null) {
			if (usersRepo.findByLogin(principal.getName()).isPresent()) {
				ArNoteUser localUser = usersRepo.findByLogin(principal.getName()).get();
				localUser.setLastOperation(defineUserActionByMethodSignature(joinPoint.getSignature()));
				localUser.setLastOperationTime(LocalDateTime.now());
				usersRepo.save(localUser);
			}
		}
	}*/
}
