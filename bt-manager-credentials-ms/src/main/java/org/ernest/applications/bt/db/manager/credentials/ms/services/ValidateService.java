package org.ernest.applications.bt.db.manager.credentials.ms.services;

import org.ernest.applications.bt.db.manager.credentials.ct.CreateCredentialsInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateOutput;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.ActivateUserException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateTokenException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateUserException;

public interface ValidateService {

	ValidateOutput validate(ValidateInput input);

	String createCredentials(CreateCredentialsInput input) throws CreateUserException;

	String createToken(String userId, String email) throws CreateTokenException;

	String activate(String token) throws ActivateUserException;

	String recover(String email);	
}