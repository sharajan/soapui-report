package com.deloitte.soaputils.utils;

public class Hello {
	
	public static String hello() {
		return "Framework Connected";
	}

	public static void main(String[] args) {
		int rows = ExcelReader.getTotalExcelRowsWithoutHeader("C:\\srajan\\CalculatorData.xlsx", "Sheet1");
		System.out.println(rows);
	}

}
