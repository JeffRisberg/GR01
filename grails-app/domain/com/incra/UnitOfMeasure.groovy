package com.incra

import java.util.Date;

/**
 * The <i>UnitOfMeasure</i> entity defines a way to measure an amount for an activity.  It 
 * drives several input screens.
 * 
 * @author Jeff Risberg
 * @since September 25, 2010
 */
class UnitOfMeasure {
	static final int UOM_Minutes = 1;
	static final int UOM_Counts = 2;
	static final int UOM_Calories = 3;
	
	String name
	Date dateCreated
	
	static constraints = {
		name(blank: false, unique:true)
		dateCreated(display: false)
	}
	
	String toString() {
		"${name}"
	}
}
