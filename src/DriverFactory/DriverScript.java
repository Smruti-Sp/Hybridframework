package DriverFactory;

import org.openqa.selenium.WebDriver;

import CommonFunLibrary.CommonFunction;
import Utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath="D:\\OJTProjectTesting\\Hybrid Framework\\Testinput\\HybridInput.xlsx";
String outputpath="D:\\OJTProjectTesting\\Hybrid Framework\\Testoutput";
public void startTest()throws Throwable
{
	//access excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterating all row in MasterTestCases sheet
	for(int i=1; i<=xl.rowCount("MasterTestCases");i++)
	{
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
		{
			//store module name into TCModule
			String TCModule=xl.getCellData("MasterTestCases", i, 1);
			//iterate all rows in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read all cell from TCModule
				String Description=xl.getCellData(TCModule, j, 0);
				String Function_Name=xl.getCellData(TCModule, j, 1);
				String Locator_Type=xl.getCellData(TCModule, j, 2);
				String Locator_Value=xl.getCellData(TCModule, j, 3);
				String Test_Data=xl.getCellData(TCModule, j, 4);
				try {
					if(Function_Name.equalsIgnoreCase("startBrowser"))
					{
						driver=CommonFunction.startBrowser();
					}
					else if(Function_Name.equalsIgnoreCase("openApplication"))
					{
						CommonFunction.openApplication(driver);
					}
					else if(Function_Name.equalsIgnoreCase("waitForElement"))
					{
						CommonFunction.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("typeAction"))
					{
						CommonFunction.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Function_Name.equalsIgnoreCase("clickAction"))
					{
						CommonFunction.clickAction(driver, Locator_Type, Locator_Value);
					}
					else if(Function_Name.equalsIgnoreCase("closeBrowser"))
					{
						CommonFunction.closeBrowser(driver);
					}
					//write as pass into status column TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					//write as pass into MaterTestCases sheet
					xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
				}catch(Exception e)
				{
					//write as Fail into status column TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					//write as Fail into MaterTestCases sheet
					xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
				}
			}
		}
		else
		{
			//write as blocked into status cell in mastertestcases sheet
			xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
		}
	
		
	}
}
}








	
	


