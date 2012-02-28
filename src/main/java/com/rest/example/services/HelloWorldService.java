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

import org.springframework.context.annotation.Scope;
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
	@Produces("application/json")
	public void setMessage(@FormParam("json-payload") String data) {
		System.out.println("String is '" + data + "'");
		System.out.println("Data is " + gson.fromJson(data, Object.class));
	}

}