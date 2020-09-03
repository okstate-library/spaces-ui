package com.okstatelibrary.spacesui.models;

import java.util.ArrayList;

public class SAMLUserList {
	
	 private static SAMLUserList mInstance;
     private ArrayList<SAMLUser> list = null;

     public static SAMLUserList getInstance() {
         if(mInstance == null)
             mInstance = new SAMLUserList();

         return mInstance;
     }

     private SAMLUserList() {
       list = new ArrayList<SAMLUser>();
     }
   
		public SAMLUser getSAMLUser() {

			return this.list.get(0);
		}
     
     // retrieve array from anywhere
     public ArrayList<SAMLUser> getArray() {
    	 
    	
      return this.list;
     }
     //Add element to array
     public void addToArray(SAMLUser value) {
      list.add(value);
     }

}
