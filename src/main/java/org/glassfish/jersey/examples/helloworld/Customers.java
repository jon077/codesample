/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.examples.helloworld;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import org.json.*;


/**
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 */
@Path("/Customers/{username}")
public class Customers {
   
    @GET
    @Produces("text/plain")
    /*public String getHello() {
        return CLICHED_MESSAGE;

	}*/
	  public String getUser(@PathParam("username") String userName) {
		Customer reqCust = CustomerStore.getCustomer(userName);
		if(reqCust !=null)
		{
			return reqCust.toJson();
		}
		else
		{
			return "No Such User";
		}
    }
	@PUT
	public Response putCustomer(String content,@PathParam("username") String userName)
	{
		String firstName = null;
		String lastName = null;
		String phoneNumber = null;
		String eMail = null;
		
		JSONObject jsonObj = new JSONObject(content);
		if(!jsonObj.isNull("firstName"))
		{
			firstName = jsonObj.getString("firstName");
		}
		if(!jsonObj.isNull("lastName"))
		{
			lastName = jsonObj.getString("lastName");
		}
		if(!jsonObj.isNull("phoneNumber"))
		{
		 phoneNumber = jsonObj.getString("phoneNumber");
		}
		if(!jsonObj.isNull("eMail"))
		{
			eMail = jsonObj.getString("eMail");
		}
		Customer reqCust = new Customer(firstName,lastName,phoneNumber,eMail,userName);
		CustomerStore.processCustomer(reqCust);
		return Response.ok("Recieved:"+jsonObj.get("firstName")).build();
	}

}
