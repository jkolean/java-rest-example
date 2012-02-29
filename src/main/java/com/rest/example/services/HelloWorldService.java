/*
 * This file is part of Java REST example
 *
 * Copyright (C) 2010 Manuel Rego Casasnovas <mrego@igalia.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rest.example.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rest.example.dtos.MessageDTO;

@Path("/helloworld")
@Component
@Scope("request")
public class HelloWorldService {
	Gson gson = new Gson();

	@GET
	@Produces("application/json")
	public String getMessage() {
		return gson.toJson(new MessageDTO("Hello World"));
	}

	@POST
	@Produces("application/text")
	public String setMessage(byte[] byteData) {
		String bytesRx = "Bytes received "+new String(Hex.encode(byteData));
		System.out.println(bytesRx);
		byte[] clearBytes = decrypt(byteData);
		String clearTextMsg = "Unable to decrypt";
		if (clearBytes != null) {
			clearTextMsg = "Clear text: "+new String(clearBytes);
		}
		return bytesRx+"\r\n"+clearTextMsg;
	}
	
	public byte[] decrypt(byte[] byteData) {
		CryptoMan cryptoMan = CryptoMan.getInstance();
		byte[] result =  null;
		
		//WDE algorithm
		result = cryptoMan.decryptDES_CBC_WDE(byteData);
		if (validateXML(result)) {
			return result;
		}
		
		//add other algorithms here
		
		return null;
	}

	public boolean validateXML(byte[] byteData) {
		return false;
	}
	

}