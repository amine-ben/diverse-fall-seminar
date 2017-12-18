package fr.inria.diverse;

public interface ExpEvalService {

	public ExpEvalResponse authenticate (User user);
	public ExpEvalResponse evaluate (String expression);
	
}
