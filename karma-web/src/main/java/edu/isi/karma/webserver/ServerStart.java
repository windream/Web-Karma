/*******************************************************************************
 * Copyright 2012 University of Southern California
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This code was developed by the Information Integration Group as part 
 * of the Karma project at the Information Sciences Institute of the 
 * University of Southern California.  For more information, publications, 
 * and related projects, please see: http://www.isi.edu/integration
 ******************************************************************************/
package edu.isi.karma.webserver;

import edu.isi.karma.webserver.ServletContextParameterMap.ContextParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerStart extends HttpServlet {
	private static Logger logger = LoggerFactory.getLogger(ServerStart.class);
	private static final long serialVersionUID = 1L;

	// private static Logger logger = LoggerFactory.getLogger(ServerStart.class);

	public void init() throws ServletException {
		// Populate the ServletContextParameterMap data structure
		// Only the parameters that are specified in the
		// ServletContextParameterMap are valid. So, to use a context init
		// parameter, add it to the ServletContextParameterMap
		ServletContext ctx = getServletContext();
		Enumeration<?> params = ctx.getInitParameterNames();
		ArrayList<String> validParams = new ArrayList<String>();
		for (ContextParameter param : ContextParameter.values()) {
			validParams.add(param.name());
		}
		while (params.hasMoreElements()) {
			String param = params.nextElement().toString();
			if (validParams.contains(param)) {
				ContextParameter mapParam = ContextParameter.valueOf(param);
				String value = ctx.getInitParameter(param);
				ServletContextParameterMap.setParameterValue(mapParam, value);
			}
		}

		String contextPath = ctx.getRealPath(File.separator);
		logger.info("Got base path:" + contextPath);
		ServletContextParameterMap.setParameterValue(ContextParameter.WEBAPP_PATH, contextPath);
		
		logger.info("************");
		logger.info("Server start servlet initialized successfully..");
		logger.info("***********");

	}		
}
