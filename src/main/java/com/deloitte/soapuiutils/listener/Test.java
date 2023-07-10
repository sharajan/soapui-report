package com.deloitte.soapuiutils.listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;


public class Test {

	public static void main(String[] args) throws URISyntaxException, IOException {

		int timeout = 1000;
		if (InetAddress.getByName("http://192.168.182.237").isReachable(timeout)) {
			System.out.println("is reachable");
		}

	}
}