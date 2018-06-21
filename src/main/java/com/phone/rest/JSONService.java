package com.phone.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/phoneNumber")
public class JSONService {

	private static HashMap<Integer, String> screenMapping = new HashMap<Integer, String>();
	private List<String> phoneNumberList;
	static {
		screenMapping.put(0, "0");
		screenMapping.put(1, "1");
		screenMapping.put(2, "abc");
		screenMapping.put(3, "def");
		screenMapping.put(4, "ghi");
		screenMapping.put(5, "jkl");
		screenMapping.put(6, "mno");
		screenMapping.put(7, "pgrs");
		screenMapping.put(8, "tuv");
		screenMapping.put(9, "wxyz");

	}

	private int[] convertToArray(String inputStr) {
		int[] inputInt = new int[10];
		for (int i = 0; i < inputStr.length(); i++) {
			inputInt[i] = Integer.parseInt(inputStr.charAt(i) + "");
		}

		return inputInt;
	}

	@POST
	@Path("/gen")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPhoneNumber(@FormParam("number") String number) {
		phoneNumberList = new ArrayList<String>();
		System.out.println("Input Number:" + convertToArray(number));
		// int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1 };
		generateNumber(convertToArray(number), 10);
		System.out.println("phoneNumberList:" + phoneNumberList.size());
		return phoneNumberList;
	}

	@GET
	@Path("/gen/{num}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPhoneNumbers(@PathParam("num") String num) {
		phoneNumberList = new ArrayList<String>();
		generateNumber(convertToArray(num), 10);
		System.out.println("phoneNumberList:" + phoneNumberList.size());
		return phoneNumberList;

	}

	void generateNumber(int number[], int n) {
		String[] result = new String[11];
		autoGenerateNumber(number, 0, result, n);
	}

	void autoGenerateNumber(int number[], int seq, String output[], int n) {
		if (seq == n) {
			String temp = "";
			for (String str : output) {
				if (str != null)
					temp = temp + str;

			}
			phoneNumberList.add(temp);
			// System.out.println("first round" + temp);
			return;
		}

		for (int i = 0; i < screenMapping.get(number[seq]).length(); i++) {
			output[seq] = "" + screenMapping.get(number[seq]).charAt(i);
			autoGenerateNumber(number, seq + 1, output, n);
		}
	}

}