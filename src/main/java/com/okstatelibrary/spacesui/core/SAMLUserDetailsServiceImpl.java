/*
 * Copyright 2020 Vincenzo De Notaris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.okstatelibrary.spacesui.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import com.okstatelibrary.spacesui.models.SAMLUser;
import com.okstatelibrary.spacesui.models.SAMLUserList;

@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {
	
	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);
	
	public Object loadUserBySAML(SAMLCredential credential)
			throws UsernameNotFoundException {
		
		// The method is supposed to identify local account of user referenced by
		// data in the SAML assertion and return UserDetails object describing the user.
		
		String userID = credential.getNameID().getValue();
	
		// Getting the Attributes name.
		//for (Attribute att : credential.getAttributes())
		//{
		//	LOG.info(att.getName() + "---" + credential.getAttributeAsString(att.getName()));
		//}
		
		String cwid = credential.getAttributeAsString("cwid");
		String mail = credential.getAttributeAsString("urn:oid:0.9.2342.19200300.100.1.3");
		String firstname = credential.getAttributeAsString("urn:oid:2.5.4.42");
		String lastName = credential.getAttributeAsString("urn:oid:2.5.4.4");

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);

		SAMLUser samlUser = new SAMLUser( userID, firstname,
				lastName,cwid,mail);
	
		SAMLUserList.getInstance().addToArray(samlUser);
		
		// In a real scenario, this implementation has to locate user in a arbitrary
		// dataStore based on information present in the SAMLCredential and
		// returns such a date in a form of application specific UserDetails object.
		return  new User (userID, "<abc123>", true, true, true, true, authorities);
	}
	
}
