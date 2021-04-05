package DriverFactory;

import org.testng.annotations.Test;

public class AppTest {
	@Test
	public void Erp_Stock()
	{
	try {
		DriverScript ds=new DriverScript();
		ds.startTest();
		
		
		
	} catch (Throwable t) {
		// TODO: handle exception
		System.out.println(t.getMessage());
	}	
	}


}
