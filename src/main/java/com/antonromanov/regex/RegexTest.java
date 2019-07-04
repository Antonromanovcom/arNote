package com.antonromanov.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest extends ParentLambdaAndGenericClass{
	public static void main(String[] args) {

		String text = "ПУБЛИЧНОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО &#34;СБЕРБАНК РОССИИ&#34;";

        /*Pattern pattern = Pattern.compile("А.+?а");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(text.substring(matcher.start(), matcher.end()));
        }*/


		String str = text.replaceAll("\\&\\#34;", "\"");
		System.out.println(str);


      /* RegexTest regexTest = new RegexTest();
	    regexTest.act("01");
	    regexTest.act2(1);*/
	}

	private String act (String str){
		return $do(s -> {
			String r = str + " 145";
			System.out.println(r);
			return r;
		}, str);
	}

	private Integer act2 (Integer num){
		return $do(s -> {
			Integer r = num + 145;
			System.out.println(r);
			return r;
		}, num);
	}
}
