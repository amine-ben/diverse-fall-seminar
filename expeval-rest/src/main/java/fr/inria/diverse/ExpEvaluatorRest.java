package fr.inria.diverse;

import static spark.Spark.port;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.udojava.evalex.Expression;

public class ExpEvaluatorRest{
	
	final static Map<String, String> dbStore = new HashMap<String, String>();
	final static Map<String, String> activeClientStore = new HashMap<String, String>();
	final static Set<String> tokensStore = new HashSet<String>();
	
	//final static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

	final static int PORT_NUMBER = 8080;
	
	public static void main(String[] args) {
		//init dbStore
		dbStore.put("admin", "admin");
		// Specify the port
		port(PORT_NUMBER);
		
		post("/login", (request,response)-> {
			response.type("application/json");
			User user = new Gson().fromJson(request.body(), User.class);
			
			if (dbStore.containsKey(user.getLogin())) {
				if (dbStore.get(user.getLogin()).equals(user.getPass())) {
					//has token 
					String token= "";
					if (!activeClientStore.containsKey(user.getLogin())) {
						token = generateAndRegisterToken(user);
					} else {
						token = activeClientStore.get(user.getLogin());
					}
					// return OK add token with the message
					System.out.println(String.format("{\"token\":\"%s\"}", token));
					return new Gson().toJson(String.format("{\"token\":\"%s\"}", token));	
				} else {
					// return KO wrong password
					return new Gson().toJson("{\"fault\":\"invalid password\"}");
				}
			} else {
				//KO wrong login
				return new Gson().toJson("{\"fault\":\"invalid login\"}");
			}
		});	
		
		post("/compute", (request,response)-> {
			response.type("application/json");
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject)jsonParser.parse(request.body());
			String token = jo.get("token").getAsString();
			if(tokensStore.contains(token)) {
				String exp = jo.get("expression").getAsString();
				try {
					//Object result = engine.eval(exp);
					Object result = new Expression(exp).eval();
					return new Gson().toJson(String.format("{\"result\":\"%s\"}", result));
				} catch (Exception e) {
					return new Gson().toJson(String.format("{\"fault\":\"invalid expression: %s\"}", exp));
				}					
			} else {
				 return new Gson().toJson("{\"fault\":\"invalid token. You should authenticate first\"}");
			}
			 
			});
	}

	private static String generateAndRegisterToken(User user) {
		String token= String.valueOf(user.hashCode());
		activeClientStore.put(user.getLogin(),token);
		tokensStore.add(token);
		return token;
	}
}