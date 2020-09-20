
package com.okstatelibrary.spacesui.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.okstatelibrary.spacesui.models.SAMLUser;
import com.okstatelibrary.spacesui.models.SAMLUserList;

@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);

	HttpServletRequest request;

	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {

		// The method is supposed to identify local account of user referenced by
		// data in the SAML assertion and return UserDetails object describing the user.

		String userID = credential.getNameID().getValue();

		LOG.info("userID ID: " + userID);

		// Getting the Attributes name.
		// for (Attribute att : credential.getAttributes())
		// {
		// LOG.info(att.getName() + "---" +
		// credential.getAttributeAsString(att.getName()));
		// }

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		HttpSession session = request.getSession();

		LOG.info("Session ID: ******************* " + session.getId());

		String cwid = credential.getAttributeAsString("cwid");
		String mail = credential.getAttributeAsString("urn:oid:0.9.2342.19200300.100.1.3");
		String firstname = credential.getAttributeAsString("urn:oid:2.5.4.42");
		String lastName = credential.getAttributeAsString("urn:oid:2.5.4.4");

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);

		SAMLUser samlUser = new SAMLUser(session.getId(), firstname, lastName, cwid, mail);

		SAMLUserList.getInstance().addToArray(samlUser);

		// In a real scenario, this implementation has to locate user in a arbitrary
		// dataStore based on information present in the SAMLCredential and
		// returns such a date in a form of application specific UserDetails object.
		return new User(userID, "<abc123>", true, true, true, true, authorities);
	}

}
