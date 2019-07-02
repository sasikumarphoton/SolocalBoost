package helpers;
public class PathConfig {
	
	public static String currentDir(){
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace('\\', '/');
		return currentDir;
	}
    public static final String projectLocation = currentDir();

    /*Driver Location */
    public static final String chromeDriverLocation = currentDir()+"/driver/chromedriver/win/chromedriver.exe";
    public static final String geckoDriverLocation = currentDir()+"/driver/geckodriver/win/geckodriver.exe";
    public static final String ieDriverLocation = currentDir()+"/driver/iedriver/IEDriverServer.exe";
    public static final String phanthomJSDriverLocation = currentDir()+"/driver/phantomjsdriver/win/phantomjs.exe";
    
    /*Appium Location */
    public static final String nodePath = "c:/PROGRA~2/Appium/node.exe";
    public static final String appiumJSPath = "c:/PROGRA~2/Appium/node_modules/appium/bin/appium.js";
    
    /*
     * iOS 
     */
    public static final String deviceUDID =  "a1be71915746553b6146507619c8225cb640d98b";
    public static final String platform_Version =  "8.0";
    public static final String deviceName =  "iPhone 6 Plus";
    public static final String safarilauncherLocation =  "/Applications/Appium/Contents/Resources/node_modules/appium/build/SafariLauncher/SafariLauncher.app";
    public static final String appiumServerURL_iOS =  "http://127.0.0.1:4723/wd/hub";

    /*
     * Android
     */
    public static final String sdk_location =  "";
    public static final String deviceId =  "Nexus_5_API_23:5554";
    public static final String platformVersion =  "6.0";
    public static final String appPackage =  "com.example.android.apis";
    public static final String appActivity =  "com.example.android.apis.ApiDemos";
    public static final String chromeDriver_location =  "http://localhost:9515";
    public static final String app_location = currentDir()+"/app/amazon.apk";
    
}
