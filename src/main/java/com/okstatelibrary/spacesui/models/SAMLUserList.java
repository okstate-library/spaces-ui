package com.okstatelibrary.spacesui.models;

import java.util.ArrayList;

public class SAMLUserList {

	private static SAMLUserList mInstance;
	public ArrayList<SAMLUser> list = null;

	public static SAMLUserList getInstance() {
		if (mInstance == null)
			mInstance = new SAMLUserList();

		return mInstance;
	}

	private SAMLUserList() {
		list = new ArrayList<SAMLUser>();
	}

	public SAMLUser getSAMLUser(String sessionId) {

		for (SAMLUser samlUser : list) {

			if (samlUser.getUserId().equals(sessionId)) {

				System.out.println("session id ----------" + sessionId);

				return samlUser;
			}
		}

		return null;

	}

	// retrieve array from anywhere
	public ArrayList<SAMLUser> getArray() {

		return this.list;
	}

	// Remove element to array
	public void removeFromArray(SAMLUser value) {

		System.out.println("remove id ----------" + value.getFirstName());

		list.remove(value);
	}

	public void clean() {
		mInstance = null;
		list = null;
	}

	// Add element to array
	public void addToArray(SAMLUser value) {

		SAMLUser samlUser = findBySessionId(value.getUserId());

		if (samlUser == null) {
			list.add(value);
		}

	}

	public ArrayList<SAMLUser> getUserArray() {

		return list;
	}

//	public void clean() {
//
//		list = null;
//	}

	public SAMLUser findBySessionId(String sessionId) {
		return list.stream().filter(samlUser -> sessionId.equals(samlUser.getUserId())).findFirst().orElse(null);
	}

}
