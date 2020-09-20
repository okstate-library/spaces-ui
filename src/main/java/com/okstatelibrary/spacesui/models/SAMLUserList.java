package com.okstatelibrary.spacesui.models;

import java.util.ArrayList;

public class SAMLUserList {

	private static SAMLUserList mInstance;
	private ArrayList<SAMLUser> list = null;

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

	// Add element to array
	public void addToArray(SAMLUser value) {

		SAMLUser samlUser = findBySessionId(value.getUserId());

		if (samlUser == null) {
			list.add(value);

			for (int i = 0; i < list.size(); i++) {
				list.get(i).print();
			}
		}

	}

	public SAMLUser findBySessionId(String sessionId) {
		return list.stream().filter(samlUser -> sessionId.equals(samlUser.getUserId())).findFirst().orElse(null);
	}

}
