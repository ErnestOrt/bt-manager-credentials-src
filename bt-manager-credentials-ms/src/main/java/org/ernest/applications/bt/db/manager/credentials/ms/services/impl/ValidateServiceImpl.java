package org.ernest.applications.bt.db.manager.credentials.ms.services.impl;

import java.util.UUID;

import org.ernest.applications.bt.db.manager.credentials.ct.ActivateOutput;
import org.ernest.applications.bt.db.manager.credentials.ct.CreateCredentialsInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateInput;
import org.ernest.applications.bt.db.manager.credentials.ct.ValidateOutput;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.ActivateUserException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateTokenException;
import org.ernest.applications.bt.db.manager.credentials.ct.exceptions.CreateUserException;
import org.ernest.applications.bt.db.manager.credentials.ms.entites.Credentials;
import org.ernest.applications.bt.db.manager.credentials.ms.entites.Token;
import org.ernest.applications.bt.db.manager.credentials.ms.services.ValidateService;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidateServiceImpl implements ValidateService{
	
	@Value("${db.credentials.name}")
	private String dbCredentialsName;
	
	@Value("${db.tokens.name}")
	private String dbTokensName;
	
	@Value("${db.host}")
	private String dbHost;
	
	@Override
	public ValidateOutput validate(ValidateInput input) {
		ValidateOutput output = new ValidateOutput();
		output.setValidated(false);
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbProperties(dbCredentialsName));
			Credentials credentials = dbClient.find(Credentials.class, input.getName());
			dbClient.shutdown();
			
			if(credentials.getPass().equals(input.getPass()) && credentials.isActivated()){
				output.setUserId(credentials.getUserId());
				output.setValidated(true);
			}
			return output;
			
		}catch(Exception e){
			return output;
		}
	}
	
	@Override
	public String createCredentials(CreateCredentialsInput input) throws CreateUserException {
		Credentials credentials = new Credentials();
	    credentials.set_id(input.getEmail());
	    credentials.setPass(input.getPass());
	    credentials.setName(input.getName());
	    credentials.setUserId(UUID.randomUUID().toString());
	    credentials.setActivated(false);

		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbProperties(dbCredentialsName));
			dbClient.save(credentials);
			dbClient.shutdown();
					
		}catch(Exception e){
			e.printStackTrace();
			throw new CreateUserException(e.getMessage());
		}
		return credentials.getUserId();
	}
	
	@Override
	public String createToken(String userId, String email) throws CreateTokenException {
		Token token = new Token();
		token.set_id(userId);
		token.setUserCredentialsId(email);
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbProperties(dbTokensName));
			dbClient.save(token);
			dbClient.shutdown();
			return userId;
		}catch(Exception e){
			e.printStackTrace();
			throw new CreateTokenException(e.getMessage());
		}
	}
	
	@Override
	public ActivateOutput activate(String tokenActivate) throws ActivateUserException {
		Token token = null;
		try{
			CouchDbClient dbClient = new CouchDbClient(buildCouchDbProperties(dbTokensName));
			token = dbClient.find(Token.class, tokenActivate);	
			dbClient.shutdown();
			
			dbClient = new CouchDbClient(buildCouchDbProperties(dbCredentialsName));
			Credentials credentials = dbClient.find(Credentials.class, token.getUserCredentialsId());
			credentials.setActivated(true);
			dbClient.update(credentials);
			dbClient.shutdown();
			return new ActivateOutput(credentials.get_id(), credentials.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActivateUserException(e.getMessage());
		}
	}
	
	@Override
	public String recover(String email) {
		CouchDbClient dbClient = new CouchDbClient(buildCouchDbProperties(dbCredentialsName));
		Credentials credentials = dbClient.find(Credentials.class, email);
		dbClient.shutdown();
		
		return credentials.getPass();
			
	}
	
	private CouchDbProperties buildCouchDbProperties(String dbName) {
		CouchDbProperties properties = new CouchDbProperties();
		properties.setDbName(dbName);
		properties.setHost(dbHost);
		properties.setPort(5984);
		properties.setCreateDbIfNotExist(true);
		properties.setProtocol("http");
		return properties;
	}


}