
package com.okstatelibrary.spacesui.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

/**
 * @author Damith
 *
 */
@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

	//
	/**
	 * 
	 */
	HttpServletRequest request;

	/**
	 * Load the user based on SAML
	 */
	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {

		// The method is supposed to identify local account of user referenced by
		// data in the SAML assertion and return UserDetails object describing the user.

		String userID = credential.getNameID().getValue();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		HttpSession session = request.getSession();

		// Getting the Attribute values and print
		// for (Attribute att : credential.getAttributes()) {
		//
		// System.out.println(att.getName() + " " + att.getFriendlyName());
		//
		// System.out.println(credential.getAttributeAsString(att.getName()));
		// }

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
