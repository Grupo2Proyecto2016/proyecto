package com.springmvc.enums;

public enum DayOfWeek 
{
	Sunday(1),
	Monday(2),
	Tuesday(3),
	Wednesday(4),
	Thursday(5),
	Friday(6),
	Saturday(7);
	
	
	private int value; 
	
	private DayOfWeek(int value)
	{ 
		this.value = value; 
	}
	
	public int getValue()
	{
		return value;
	}
}
