package com.okstatelibrary.spacesui.folio.models;

/**
 * Represents the root structure of a response or data container that includes
 * metadata such as the total number of records.
 * 
 * This class can be used to encapsulate simple JSON/XML responses or to serve
 * as a container for pagination metadata.
 */
public class Root {

	/**
	 * The total number of records available. Typically used in pagination or
	 * summary views.
	 */
	public int totalRecords;
}
