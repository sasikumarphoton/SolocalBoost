package helpers;



import java.io.IOException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends CommonLibrary {
	
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) throws Exception {
    	if(resourceType.equalsIgnoreCase("MOBILE") && appType.equalsIgnoreCase("androidNative")){
    		appiumStop();
    	}
    	
        if(scenario.isFailed()) {
        	System.out.println("Scenario Failed...Taking screenshot....");
        	//CommonLibrary.getscreenshotEmbed("embed", scenario);
        }
    }
    
    @Before
	public static void BeforeClass() throws IOException, InterruptedException{
		if(resourceType.equalsIgnoreCase("MOBILE") && appType.equalsIgnoreCase("androidNative")){
			// Start appium server.
			appiumStart();
		}
	}
}
