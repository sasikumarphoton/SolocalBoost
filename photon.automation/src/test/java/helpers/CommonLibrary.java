package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class CommonLibrary {
	public static WebDriver webDriver = null;
	public static WebElement element = null;
	public static AndroidDriver<MobileElement> nativeDriver = null;
	public static MobileElement mobElement = null;
	static WebDriverWait browserWithElementWait = null;
	public static Configuration config = null;
	public static String highlightedWebElementStyle;
	public static WebElement highlightedWebElement;
	public static String elementNameTxt;
	public static int implicitWaitInSeconds;
	public static int elementWaitInSeconds;
	public static String browser;
	public static String platform;
	public static String resourceType;
	public static String appType;
	public static String applicationURL;
	public static AppiumDriverLocalService appiumService;
	public static String appiumServiceUrl;
	static Process p;
	static String cmd = PathConfig.nodePath + " " + PathConfig.appiumJSPath;
	
	static{
		ConfigurationFactory factory = new ConfigurationFactory("config/config.xml");
		try {
			config = factory.getConfiguration();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		implicitWaitInSeconds = config.getInt("implicitWaitInSeconds");
		elementWaitInSeconds = config.getInt("elementWaitInSeconds");
		browser = config.getString("browser").toUpperCase();
		platform = config.getString("platform").toUpperCase();
		resourceType = config.getString("resourceType").toUpperCase();
		appType = config.getString("appType").toUpperCase();
		applicationURL = config.getString("applicationURL");
		System.setProperty("webdriver.chrome.driver", PathConfig.chromeDriverLocation);
		System.setProperty("webdriver.gecko.driver", PathConfig.geckoDriverLocation);
		System.setProperty("webdriver.ie.driver", PathConfig.ieDriverLocation);
		System.setProperty("phantomjs.binary.path", PathConfig.phanthomJSDriverLocation);
	}
	
	// Method for Browser Initiation
	public static void initiateBrowser() throws ConfigurationException, IOException, InterruptedException {
		switch(browser){
		case "FIREFOX":
			startFirefoxDriver();
			break;
		case "CHROME":
			startChromeDriver();
			break;
		case "IE": 
			startIEDriver();
			break;
		case "PHANTOMJS":
			startPhantomJsDriver();
			break;
		default:
			System.out.println("Please specify a browser in Application.properties");
		}
		if(config.getString("maximizeBrowser").equalsIgnoreCase("Yes")){
			webDriver.manage().window().maximize();
		}
	}
	
	private static void startChromeDriver(){
		webDriver = new ChromeDriver();
		webDriver.get(applicationURL);
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	private static void startFirefoxDriver(){
		DesiredCapabilities	capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", false);
		webDriver = new FirefoxDriver(capabilities);
		webDriver.get(applicationURL);
		webDriver.manage().timeouts().implicitlyWait(implicitWaitInSeconds, TimeUnit.SECONDS);
	}
	
	private static void startIEDriver(){
		if (resourceType.equalsIgnoreCase("Laptop")) {
			DesiredCapabilities	capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability("nativeEvents", false);
			webDriver = new InternetExplorerDriver(capabilities);
			webDriver.get(applicationURL);
			webDriver.manage().timeouts().implicitlyWait(implicitWaitInSeconds, TimeUnit.SECONDS);
		}
		else{
			DesiredCapabilities	capabilities = DesiredCapabilities.internetExplorer();
//	        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			webDriver = new InternetExplorerDriver(capabilities);
			webDriver.get(applicationURL);
			webDriver.manage().timeouts().implicitlyWait(implicitWaitInSeconds, TimeUnit.SECONDS);
		}
	}
	
	private static void startPhantomJsDriver(){
		DesiredCapabilities	capabilities = DesiredCapabilities.phantomjs();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PathConfig.phanthomJSDriverLocation);
		webDriver = new PhantomJSDriver(capabilities);
		webDriver.get(applicationURL);
		webDriver.manage().timeouts().implicitlyWait(implicitWaitInSeconds, TimeUnit.SECONDS);
	}
	
	public static void initiateBrowser_Android() throws IOException, InterruptedException {
		try {
			String adbPath = PathConfig.sdk_location + File.separator + "platform-tools";
			//Runtime.getRuntime().exec(UserConfig.projectLocation + File.separator + "Extensions/Killchromedriver.sh" + " start");
			Thread.sleep(1000 * 2);
			Thread.sleep(1000);
			Runtime.getRuntime().exec(adbPath + "/adb" + " start-server");
			Thread.sleep(1000);
			Runtime.getRuntime().exec(PathConfig.projectLocation + "/drivers/chromedriver");
			Thread.sleep(1000);//UserConfig
			System.out.println("initialising the browser");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("androidPackage", "com.android.chrome");
			options.setExperimentalOption("androidDeviceSerial", PathConfig.deviceId);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setPlatform(Platform.ANDROID);
			capabilities.setCapability("device", "android");
			capabilities.setCapability("app", "chrome");
			webDriver = new RemoteWebDriver(new URL(PathConfig.chromeDriver_location), capabilities);
			webDriver.manage().deleteAllCookies();
			webDriver.get(applicationURL);
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void initiateBrowser_iOS() throws IOException, InterruptedException {
		try {
			System.out.println("initialising the Ios browser");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.BROWSER_NAME,"safari");
			capabilities.setCapability("app", "safari");
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("platformVersion", PathConfig.platform_Version);
			capabilities.setCapability("deviceName", "iPhone");
			capabilities.setCapability("device", "iPhone");
			capabilities.setCapability("-U", PathConfig.deviceUDID); 
			capabilities.setCapability("app", PathConfig.safarilauncherLocation); 
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("autoAcceptAlerts", true);
			webDriver=new RemoteWebDriver(new URL(PathConfig.appiumServerURL_iOS), capabilities);
			webDriver.manage().deleteAllCookies();
			webDriver.get(applicationURL);
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 // This method Is responsible for starting appium server.
	 public static void appiumStart() throws IOException, InterruptedException {
		appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort().usingDriverExecutable(new File(PathConfig.nodePath)).withAppiumJS(new File(PathConfig.appiumJSPath)));
		// appiumService=AppiumDriverLocalService.buildDefaultService();;
		appiumService.start();
		appiumServiceUrl = appiumService.getUrl().toString();
		System.out.println("Appium Service Address : - "+ appiumServiceUrl);
	 }
	 
	 // This method Is responsible for stopping appium server.
	public static void appiumStop() throws IOException {
		System.out.println("Stop appium service");
		appiumService.stop();
	}
	
	public static void initiateNativeAPP_Android() throws IOException, InterruptedException {
		try {
			System.out.println("Initialising the Android Application");
			DesiredCapabilities capabilities = new DesiredCapabilities();
//			capabilities.setCapability("app", PathConfig.app_location);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", PathConfig.platformVersion);
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("deviceName", PathConfig.deviceId);
			capabilities.setCapability("appPackage", PathConfig.appPackage); 
			capabilities.setCapability("appActivity", PathConfig.appActivity); 
			nativeDriver=new AndroidDriver<MobileElement>(new URL(appiumServiceUrl), capabilities);
//			nativeDriver.resetApp();
			nativeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
			nativeDriver.pressKeyCode(66);
		}
	}

	// Method for closing the browser
	public static void closeBrowser() throws InterruptedException, IOException{
		webDriver.quit();
	}

	// Method to close android native driver
	public static void closeNativeDriver() throws InterruptedException, IOException, Exception{
		if(resourceType.equalsIgnoreCase("Mobile") && appType.equalsIgnoreCase("androidNative")){
			System.out.println("closing");
			nativeDriver.quit();
		}
	}
	
	
    public String getCurrentPageTitle() {
        return webDriver.getTitle();
    }
    

	/*
	 *  Methods to get the element By Property
	 */
	public static WebElement getElementByProperty(String objectProperty, WebDriver webDriver) {
		String propertyType = null;
		WebDriverWait browserWithElementWait = null;
		if (browserWithElementWait == null) {
			browserWithElementWait = new WebDriverWait(webDriver, elementWaitInSeconds);
		}
		propertyType = StringUtils.substringAfter(objectProperty, "~");
		objectProperty = StringUtils.substringBefore(objectProperty, "~");
		switch(propertyType.toUpperCase()){
			case "CSS":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(objectProperty)));
				break;
			case "XPATH":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(objectProperty)));
				break;
			case "ID":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.id(objectProperty)));
				break;
			case "CLASS":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.className(objectProperty)));
				break;
			case "NAME":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.name(objectProperty)));
				break;
			case "LINKTEXT":
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(objectProperty)));
				break;
			default: //default locator is xpath 
				element = browserWithElementWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(objectProperty)));
				break;
		}
		if(element == null){
			Assert.fail("Element not present");
		}
//		else if(!element.isDisplayed()){
//			Assert.fail("Element not present");
//		}
		return element;	
	}


	/*
	 * Common Methods for Element Verification
	 */

	public static boolean isElementPresentVerification(String objectProperty) throws Exception {
		boolean isElementPresent = false;
		implicitWaitInSeconds = config.getInt("implicitWaitInSeconds");
		try {
			element = getElementByProperty(objectProperty, webDriver);
			if (element != null) {
				isElementPresent = true;
			} else {
				throw new Exception("Object Couldn't be retrieved and verified");
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Element not present");
		}
		return isElementPresent;
	}
	
	
	/*
	 * Common Methods for Element Verification in Native App
	 */

	public static boolean isElementPresentVerificationNativeApp(String objectProperty) throws Exception {
		boolean isElementPresent = false;
		implicitWaitInSeconds = config.getInt("implicitWaitInSeconds");
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			if (element != null) {
				isElementPresent = true;
			} else {
				throw new Exception("Object Couldn't be retrieved and verified");
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Element not present");
		}
		return isElementPresent;
	}
	

	/*
	 *  Common Method for Click
	 */
	public static boolean isElementPresentVerifyClick(String objectProperty) {
		boolean isVerifiedAndClicked = false;
		implicitWaitInSeconds = config.getInt("implicitWaitInSeconds");
		if (resourceType.equalsIgnoreCase("Laptop")) {
			if(webDriver.toString().toUpperCase().contains("INTERNETEXPLORER")){
//				System.out.println("IE Click");
				try{
					element = getElementByProperty(objectProperty, webDriver);
					if (element != null) {
						Actions action = new Actions(webDriver);
						action.moveToElement(element).doubleClick().build().perform();
						isVerifiedAndClicked = true;
					} else {
						throw new Exception("Object Couldn't be retrieved and clicked");
					}
				}
				catch(Exception e){
					element = null;
				}
			}
			else{
				try {
					element = getElementByProperty(objectProperty, webDriver);
					if (element != null) {
						element.click();
						isVerifiedAndClicked = true;
					} else {
						throw new Exception("Object Couldn't be retrieved and clicked");
					}
				} catch (Exception e) {
						element = null;
					}
			}
		}
		else{
			try {
				element = getElementByProperty(objectProperty, webDriver);
				if (element != null) {
					element.click();
					Thread.sleep(1000);
					isVerifiedAndClicked = true;
				} else {
					throw new Exception("Object Couldn't be retrieved and clicked");
				}
			} catch (Exception e) {
					element = null;
					Assert.fail("Element not Clicked");
				}
		}
		return isVerifiedAndClicked;
	}
	
	
	/*
	 *  Common Method for Click
	 */
	public static boolean isElementPresentVerifyClick_nativeApp(String objectProperty) {
		boolean isVerifiedAndClicked = false;
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			if (element != null) {
				element.click();
				isVerifiedAndClicked = true;
			} else {
				throw new Exception("Object Couldn't be retrieved and clicked");
			}
		} catch (Exception e) {
			element = null;
			Assert.fail("Element not Clicked");
		}
		return isVerifiedAndClicked;
	}
	
	public static boolean isElementPresentVerifyDoubleClick(String objectProperty){
		boolean isVerifiedAndClicked = false;
		try {
			element = getElementByProperty(objectProperty, webDriver);
			if (element != null) {
				Actions action = new Actions(webDriver);
				action.doubleClick(element).perform();
				//action.doubleClick(element).build().perform();
				isVerifiedAndClicked = true;
			} else {
				throw new Exception("Object Couldn't be retrieved and clicked");
			}
		} 
		catch (Exception e) {
			element = null;
			Assert.fail("Element not Clicked");
		}
		return isVerifiedAndClicked;
	}

	
	/*
	 *  Methods for Clear and Enter Text
	 */
	public static boolean clearAndEnterText(String objectProperty, String Text) {
		boolean isTextEnteredResult = false;
		try {
			if ("-".equals(Text)) {
				// ignore this field
				isTextEnteredResult = true;
			} else {
				WebElement textBox = getElementByProperty(objectProperty, webDriver);
				textBox.clear();
				textBox.sendKeys(Text);
				isTextEnteredResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return isTextEnteredResult;
	}
	
	
	/*
	 *  Methods for Clear and Enter Text for NativeApp
	 */
	public static boolean clearAndEnterText_NativeApp(String objectProperty, String Text) {
		boolean isTextEnteredResult = false;
		try {
			if ("-".equals(Text)) {
				// ignore this field
				isTextEnteredResult = true;
			} else {
				WebElement textBox = getElementByProperty(objectProperty, nativeDriver);
//				textBox.click();
				textBox.clear();
				textBox.sendKeys(Text);
				isTextEnteredResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return isTextEnteredResult;
	}
	
	public static boolean clearText(String objectProperty) {
		try {
			WebElement textBox = getElementByProperty(objectProperty, webDriver);
			textBox.clear();
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return false;
	}
	
	
	public static boolean EnterText(String objectProperty, String Text){
		boolean isTextEnteredResult = false;
		try {
			if ("-".equals(Text)) {
				// ignore this field
				isTextEnteredResult = true;
			} else {
				WebElement textBox = getElementByProperty(objectProperty, webDriver);
				textBox.sendKeys(Text);
				isTextEnteredResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return isTextEnteredResult;
	}
	

	// Methods for Text validation

	public static void linkText_Validation(String objectProperty,String Text) {
		element = getElementByProperty(objectProperty, webDriver);
		String linkText = element.getText();
		if(linkText.equalsIgnoreCase(Text)) {
			System.out.println("Link Text expected and actual text are Same");
		}else {
			System.out.println("Link Text expected and actual text are not Same");
			System.out.println("Link Text - Actual : "+linkText);
			System.out.println("Link Text -Expected : "+Text);
		}
	}
	
	
	public static void linkText_ContainsValidation(String objectProperty,String Text) {
		element = getElementByProperty(objectProperty, webDriver);
		String linkText = element.getText();
		if(linkText.contains(Text)) {
			System.out.println("Link Text expected that contains actual text");
		}else {
			System.out.println("Link Text not contains actual text");
			System.out.println("Link Text - Actual : "+linkText);
			System.out.println("Link Text -Expected : "+Text);
		}	
	}
	
	
	public static boolean linkText_Valid(String objectProperty, String Text) {
		element = getElementByProperty(objectProperty, webDriver);
		String linkText = element.getText();
		if (linkText.equalsIgnoreCase(Text)) {
			return true;
		}
		return false;
	}
	
	/*
	 *  Methods for Highlight the Elements
	 */
	public static void highlightElement(WebElement element,WebDriver webDriver) {
		for (int i = 0; i < 1; i++) {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border: 3px solid black;");
		}
	}

	/*
	 *  random Link
	 */
	public static void randomClickLink(String objectProperty,WebDriver webDriver) {
		Random r = new java.util.Random();
	    List<WebElement> links = element.findElements(By.xpath(objectProperty));

	    WebElement randomElement = links.get(r.nextInt(links.size()));
	    randomElement.click();
	}
	
	

	/*
	 *  Methods for Browser Navigation
	 */
	public static void browserNavigation_Back() {
		try {
			webDriver.navigate().back();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

	
	/*
	 *  Methods for Page refresh
	 */
	public static void refresh_Page() {
		webDriver.navigate().refresh();
	}

	
	/*
	 *  Methods for Screenshot
	 */
	public static void getscreenshot(String screenShotName) throws Exception 
	{
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in project location with name "screenshot.png"
		FileUtils.copyFile(scrFile, new File(PathConfig.projectLocation+"/FailureScreenShot/"+screenShotName+".png"));
	}
	
	public static void getscreenshotEmbed(String screenShotName, Scenario scenario) throws Exception 
	{
//		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(scrFile, new File(PathConfig.projectLocation+"/FailureScreenShot/"+screenShotName+".png"));
		final byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
	    scenario.embed(screenshot, "image/png"); // ... and embed it in the report.
	    webDriver.quit();
	}


	/*
	 *  Methods for Scroll
	 */
	public static void scrollTo(String objectProperty) {
		element = getElementByProperty(objectProperty, webDriver);
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}
	
	
	/*
	 *  Methods for Getting data from Excel Sheet
	 */
	public static Map<String, List<String>> getHorizontalData(DataTable dataTable) {
		Map<String, List<String>> dataMap = null;
		try {
			dataMap = new HashMap<String, List<String>>();
			List<String> headingRow = dataTable.raw().get(0);
			int dataTableRowsCount = dataTable.getGherkinRows().size() - 1;
			ArrayList<String> totalRowCount = new ArrayList<String>();
			totalRowCount.add(Integer.toString(dataTableRowsCount));
			dataMap.put("totalRowCount", totalRowCount);
			for (int i = 0; i < headingRow.size(); i++) {
				List<String> dataList = new ArrayList<String>();
				dataMap.put(headingRow.get(i), dataList);
				System.out.println("heading row " + headingRow.get(i));
				System.out.println("data list : " + dataList);
				for (int j = 1; j <= dataTableRowsCount; j++) {
					List<String> dataRow = dataTable.raw().get(j);
					dataList.add(dataRow.get(i));
					System.out.println("data row " + dataRow.get(i));
				}
			}
		} catch (Exception e) {

		}
		return dataMap;
	}

	public static Map<String, List<String>> getVerticalData(DataTable dataTable) {
		Map<String, List<String>> dataMap = null;
		try {
			int dataTableRowsCount = dataTable.getGherkinRows().size();
			dataMap = new HashMap<String, List<String>>();
			for (int k = 0; k < dataTableRowsCount; k++) {
				List<String> dataRow = dataTable.raw().get(k);
				String key = dataRow.get(0);
				dataRow.remove(0);
				dataMap.put(key, dataRow);
			}
		} catch (Exception e) {

		}
		return dataMap;
	}
	
	
	//Highlight Element with Red Color
	protected static void highlightElement(String searchTxt) {
		
		if (highlightedWebElement != null) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) webDriver; 
				js.executeScript("arguments[0].style.border='" + highlightedWebElementStyle + "';",
					highlightedWebElement);
			} catch (final Exception e) {
				
			}
		}

		try {
			highlightedWebElement = element;
			JavascriptExecutor js = (JavascriptExecutor) webDriver;  
			highlightedWebElementStyle = (String) js.executeScript("return arguments[0].style.border;", element);
			js.executeScript("arguments[0].style.border='3px dotted red';", element);
		} catch (final Exception e) {
			}
	}
		
		
	protected static void removehighlightElement(String searchTxt) {
		
		if (highlightedWebElement != null) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) webDriver; 
				js.executeScript("arguments[0].style.border='" + highlightedWebElementStyle + "';",
					highlightedWebElement);
			} catch (final Exception e) {		
			}
		}
	}


	/*
	 *  Methods for get data from Excel Sheet
	 */
	public static String getXLSTestData (String FileName,String SheetName, String RowId,String column) throws IOException {

		String col1 = null;
		DataFormatter df = new DataFormatter();
		FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") +"/InputData"+ File.separator +FileName+".xls"));
		HSSFWorkbook book = new HSSFWorkbook(file);
		HSSFSheet sheet = book.getSheet(SheetName);

		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for (int rowIterator = 1; rowIterator<=rowCount;rowIterator++) {
			String row = sheet.getRow(rowIterator).getCell(0).getStringCellValue();
			if (row.equalsIgnoreCase(RowId)) {
				for (int colIterator = 1;colIterator<sheet.getRow(rowIterator).getLastCellNum();colIterator++) {
					String col = sheet.getRow(0).getCell(colIterator).getStringCellValue();
					if (col.equalsIgnoreCase(column)) {
						Cell cellvalue = sheet.getRow(rowIterator).getCell(colIterator);
						col1 = df.formatCellValue(cellvalue);
						break;
					}
				}
			}
		}
		book.close();
		return col1;
	}
	
	//Method to hit enter button
	public static boolean enterfunction(String objectProperty) {
		boolean isVerifiedAndClicked = false;
		browserWithElementWait = new WebDriverWait(webDriver,30);
		try {
			element = getElementByProperty(objectProperty, webDriver);
			if (element != null) {
				element.sendKeys(Keys.ENTER);
			} else {
				throw new Exception("Object Couldn't be retrieved and clicked");
			}
		} catch (Exception e) {
			element = null;
		}
		return isVerifiedAndClicked;
	}
	
	
	public static void waitUntilElementHides(int timeOutInSeconds,final String objectProperty){
		//Sets FluentWait Setup
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(webDriver)
		        .withTimeout(3, TimeUnit.SECONDS)
		        .pollingEvery(500, TimeUnit.MILLISECONDS)
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);

		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
			System.out.println("Waiting for loading to disappear");
			  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
			   public WebElement apply(WebDriver driver) {
			      return driver.findElement(By.xpath(objectProperty));
			  }
			});
		    } catch (Exception e) {
		   }

		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
		      wait.until(ExpectedConditions.invisibilityOfElementLocated(
		                 By.xpath(objectProperty))
		            );
		        }
		}
	
	
	/*
	 * Method for Mouse Over Action
	 */
	public static boolean isMouseoveraction(String objectProperty) {
		boolean isMouseoveract = false;
		Actions action = new Actions(webDriver);

		browserWithElementWait = new WebDriverWait(webDriver, 30);
		try {
			element = getElementByProperty(objectProperty, webDriver);
			if (element != null) {
				action.moveToElement(element).perform();
				Thread.sleep(1000);
				isMouseoveract = true;
			} else {
				throw new Exception("Object Couldn't be retrieved");
			}
		} catch (Exception e) {
			element = null;
		}
		return isMouseoveract;
	}
	
	// Five digit random number generator
	public static String randomNumGenerator(){
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000);
		String fivedigitnum = String.format("%05d", num); 
		return fivedigitnum;
	}
	
	//Random int generator
	public static int randomnum(){
		Random random = new Random();
		int index=random.nextInt((10 - 1) + 1) + 1;
		return index;
	}
	
	
	//Method to click a Dropdown randomly
	public static void randomDropdownClick(String objectProperty) throws InterruptedException{
		List<WebElement> randomclick = element.findElements(By.xpath(objectProperty));
		Random random = new Random();
		int index = 0;
//		for (int i = 1; i <= randomclick.size(); i++) {    
//		      index = random.nextInt(randomclick.size());
//		}
		index=random.nextInt(randomclick.size());
		if(index>0){
			if(index==randomclick.size()){
				index=index-1;
				randomclick.get(index).click();
				Thread.sleep(1000);
			}
			else{
				randomclick.get(index).click();
				Thread.sleep(1000);
			}
		}
		else{
			index=index+1;
			randomclick.get(index).click();
			Thread.sleep(1000);
		}

	}
	
	
	//Method to click a Dropdown by Value
	public static void dropdownSelectByvalue(String dropdownValue,String objectProperty){
		Select oSelect = new Select(getElementByProperty(objectProperty, webDriver));
		oSelect.selectByValue(dropdownValue);
	}
	
	
	//Method to click a Dropdown by Text 
	public static void dropdownClickByText(String dropDownText,String objectProperty) throws InterruptedException{
		List<WebElement> dropdownElements = element.findElements(By.xpath(objectProperty));
		for (WebElement li : dropdownElements) {
			String text = li.getText();
			if(text.equals(dropDownText)){
				li.click();
				break;
			}
		}
	}
	
	//Method to click a div list randomly
	public static void randomDivClick(String objectProperty) throws InterruptedException{
		List<WebElement> randomclick = element.findElements(By.xpath(objectProperty));
		Random random = new Random();
		int index = 0;
		if(randomclick.size()>0){
			index=random.nextInt((9 - 1) + 1) + 1;
		}
//		for (int i = 1; i <= 9; i++) {    
//		      index = random.nextInt(9);
//		}
		if(index>0){
			randomclick.get(index).click();
			Thread.sleep(1000);
		}
		else{
			System.out.println("Index value is 0");
		}
	}
	
	
	
	//Method to check the element is active
	public static boolean checkActive(String objectProperty){
		boolean isActive = false;
		try {
			element = getElementByProperty(objectProperty, webDriver);
			if (element != null) {
				String activeClass = element.getAttribute("class");
				if ("active".equals(activeClass)) {
					   isActive=true;
				}
				else{
					isActive=false;
				}
			} else {
				throw new Exception("Object Couldn't be retrieved and clicked");
			}
		} catch (Exception e) {
			element = null;
			Assert.fail("Element not present");
		}
		return isActive;
	}
	
	//Method to check the element is active
	public static boolean checkUnderline(String objectProperty){
		boolean underlined=false;
		try {
			List<WebElement> elementsList = element.findElements(By.xpath(objectProperty));
//			Random random = new Random();
			
			for(int i=0;i<elementsList.size();i++){
				String text = elementsList.get(i).getCssValue("text-decoration");
//				int randomValue = random.nextInt(elementsList.size());
				if(text.equalsIgnoreCase("underline")){
					elementsList.get(i).click();
					underlined=true;
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return underlined;
	}

	// Method for assertion(text,text)
	public static void assertText(String objectProperty, String Text) throws Exception {
		try {
			element = getElementByProperty(objectProperty, webDriver);
			String ActualText = element.getText();
			Assert.assertEquals(ActualText,Text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void assertTextNativeApp(String objectProperty, String Text) throws Exception {
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			String ActualText = element.getText();
			Assert.assertEquals(ActualText,Text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void assertAttributeValueNativeApp(String objectProperty, String attribute ,String Text) throws Exception {
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			String ActualText = element.getAttribute(attribute);
			Assert.assertEquals(ActualText,Text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method for get Text 
	public static String getText(String objectProperty) throws Exception {
		String text = null;
		try {
			element = getElementByProperty(objectProperty, webDriver);
			 text = element.getText();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public static String getTextNativeApp(String objectProperty) throws Exception {
		String text = null;
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			 text = element.getText();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public static String getAttributeValueNativeApp(String objectProperty,String attribute) throws Exception {
		String text = null;
		try {
			element = getElementByProperty(objectProperty, nativeDriver);
			 text = element.getAttribute(attribute);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	
	
	/*
	 * Method to write and Read data from TestData file
	 */
	public static void writeDataToFile(String key,String value){
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(PathConfig.projectLocation+"/InputData/testData.properties",true);
			// set the properties value
			prop.setProperty(key, value);
			// save properties
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String readDatafromFile(String key){
		Properties prop = new Properties();
		InputStream input = null;
		String value=null;
		try {
			input = new FileInputStream(PathConfig.projectLocation+"/InputData/testData.properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			value=prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static void deleteTestDataFile(){
		try{
			 File file = new File(PathConfig.projectLocation+"/InputData/testData.properties"); 
			 boolean success=file.delete();
			 if(!success){
			    // Notify user that the file 
			 }
		}
		catch(SecurityException ex){
			 // No sufficient rights to do this operation
		}
	}
	
	/*
	 *Method to Scroll to the bottom of the page
	 */
	public static void scrollToBottom() {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void pressPageDownKey() {
		Actions action = new Actions (webDriver);
		action.sendKeys(Keys.PAGE_DOWN).perform();
	}
	
	public static String waitForElementToBeVisible(int timeOutInSeconds, String objectProperty) throws TimeoutException{
	    try {
	        (new WebDriverWait(webDriver, timeOutInSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By
	                .xpath(objectProperty)));
	        return null;
	    } catch (TimeoutException e) {
	        return "Element Not Visible even after" + timeOutInSeconds;
	    }
	}
	
}
