package constant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;

import helpers.CommonLibrary;

public class BoostDigital_CreateNewProposal_SocialClic_Login extends CommonLibrary{

	public BoostDigital_CreateNewProposal_SocialClic_Login() throws ConfigurationException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static final String usernameId="user_login~ID";
	public static final String passwordId="user_pass~ID";
	public static final String LoginBtn="//button[@name='submit']~XPATH";

	public static void login(Map<String, List<String>> dataMap) throws IOException{
		String usrName=getXLSTestData(dataMap.get("InputFileName").get(0), dataMap.get("SheetName").get(0), dataMap.get("RowId").get(0), "UserName");
		String passWord=getXLSTestData(dataMap.get("InputFileName").get(0), dataMap.get("SheetName").get(0), dataMap.get("RowId").get(0), "Password");
		System.out.println(usrName);
		
		try {
		// for web
		if (isElementPresentVerification(usernameId)) {
			if (!clearAndEnterText(usernameId,usrName)) {
				throw new Exception("User Not able to Enter Username");
			}
		}else {
				throw new Exception("Element Not Verified");
			}
			
		if (isElementPresentVerification(passwordId)) {
			if (!clearAndEnterText(passwordId,passWord)) {
				throw new Exception("User Not able to Enter password");
			}}
		else {
				throw new Exception("Element Not Verified");
			}
			
		System.out.println("Password Entered");
		
		if (isElementPresentVerification(LoginBtn)) {
			if (!isElementPresentVerifyClick(LoginBtn)) {
				throw new Exception("User Not able to click login button");
			  }
			System.out.println("clicked on Login button");
			}
		else {
				throw new Exception("Element Not Verified");
			}
		Thread.sleep(200);
			
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
	
}