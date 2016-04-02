package org.ernest.applications.bt.db.manager.credentials.ms.controllers;

import org.ernest.applications.bt.db.manager.credentials.ct.ActivateOutput;
import org.ernest.applications.bt.db.manager.credentials.ct.CreateCredentialsInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateOutput;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.ActivateUserException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateTokenException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateUserException;
import org.ernest.applications.bt.db.manager.credentials.ms.services.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CredentialsController {

	@Autowired
	ValidateService validateService;
	
	@RequestMapping("/validate")
	public ValidateOutput create(@RequestBody ValidateInput input) throws CreateUserException {
		return validateService.validate(input);
	}
	
	@RequestMapping("/create")
	public String create(@RequestBody CreateCredentialsInput input) throws CreateUserException, CreateTokenException {
		String userId = validateService.createCredentials(input);
		return validateService.createToken(userId, input.getEmail());
	}
	
	@RequestMapping(path = "/activate/{token}", method = RequestMethod.GET)
	public ActivateOutput activate(@PathVariable("token") String token) throws ActivateUserException {
		return validateService.activate(token);
	}
	
	@RequestMapping(path = "/recover", method = RequestMethod.POST)
	public String recover(@RequestBody String email) throws CreateUserException, CreateTokenException {
		return validateService.recover(email);
	}
	
	@RequestMapping(path = "/retrieveid", method = RequestMethod.POST)
	public String retrieveId(@RequestBody String email) throws CreateUserException, CreateTokenException, ActivateUserException {
		return validateService.retrieve(email);
	}
}