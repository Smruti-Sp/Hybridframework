package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueforKey(String key)throws Throwable
	{
		Properties configureProperties=new Properties();
		configureProperties.load(new FileInputStream("D:\\OJTProjectTesting\\Hybrid Framework\\Propertyfile\\Environment.properrties"));
		return configureProperties.getProperty(key);
		
	}

}
