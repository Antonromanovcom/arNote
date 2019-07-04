package com.antonromanov.regex;

public class ParentLambdaAndGenericClass {

	protected <T,E>T $do(SomeProcess<T, E> process, E s){
		return process.aplly(s);
	}

	protected interface SomeProcess<T, E> {
		T aplly(E req);
	}

}
