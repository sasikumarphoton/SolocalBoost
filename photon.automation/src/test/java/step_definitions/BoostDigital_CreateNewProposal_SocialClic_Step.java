package step_definitions;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import constant.BoostDigital_CreateNewProposal_SocialClic_Constants;
import constant.BoostDigital_CreateNwProposal_NGPack1_Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.CommonLibrary;

public class BoostDigital_CreateNewProposal_SocialClic_Step extends CommonLibrary {

	@Given("^user launches the Boost Digital application$")
	public void user_launches_the_Boost_Digital_application() throws Throwable {
		initiateBrowser();
	}

	@Given("^user enters username and password$")
	public void user_enters_username_and_password() throws Throwable {
		EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.USERNAME,
				BoostDigital_CreateNewProposal_SocialClic_Constants.USERNAMEVALUE);
		EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.PASSWORD,
				BoostDigital_CreateNewProposal_SocialClic_Constants.PASSWORDVALUE);
		
	}

	@Given("^click on connect$")
	public void click_on_connect() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CONNECTBUTTON);
	}

	@Then("^EPJ entry screen is displayed$")
	public void epj_entry_screen_is_displayed() throws Throwable {
		isElementPresentVerification(BoostDigital_CreateNewProposal_SocialClic_Constants.EPJDASHBOARD);
	}

	@When("^user enters the EPJ number$")
	public void user_enters_the_EPJ_number() throws Throwable {
		EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.EPJNUMBER,
				BoostDigital_CreateNewProposal_SocialClic_Constants.EPJNUMBERVALUE);
	}

	@When("^click on continue$")
	public void click_on_continue() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CONTINUEBUTTON);
	}

	@Then("^user click on voir les propostions button in the dashboard screen$")
	public void user_click_on_voir_les_propostions_button_in_the_dashboard_screen() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.VOIRLESBUTTON);
	}

	@Then("^click on add icon plus button$")
	public void click_on_add_icon_plus_button() throws Throwable {
		scrollToBottom();
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.PLUSSBUTTON);
	}

	@Then("^select Creer une propostion vierge$")
	public void select_Creer_une_propostion_vierge() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CREERUNA);
	}

	@Then("^enter name of proposal as test(\\d+)$")
	public void enter_name_of_proposal_as_test(int arg1) throws Throwable {
		EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.PROPOSALNAME,
				BoostDigital_CreateNewProposal_SocialClic_Constants.PROPOSALNAMEVALUE);
	}

	@Then("^click on Valider$")
	public void click_on_Valider() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.VALIDARBUTTON);
	}

	@Then("^click on Social Clic in product list$")
	public void click_on_Social_Clic_in_product_list() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.SOCIALCLICK);
	}

	@Then("^click on configure product$")
	public void click_on_configure_product() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CONFIGUREPRODUCTBTN);
	}


	@Then("^enter votre activitie as garage$")
	public void enter_votre_activitie_as_garage() throws Throwable {
		//EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.VOLTREACTIVITIE);
				//BoostDigital_CreateNewProposal_SocialClic_Constants.VOLTREACTIVITIEVALUE);
	}

	@Then("^select the first one from the list$")
	public void select_the_first_one_from_the_list() throws Throwable {
		webDriver.findElement(By.xpath("//*[@id=\"bodycontainer\"]/div/div/section/div/div[2]/div[2]/div[2]/div/div[15]/form/div[1]/div[2]/div/div/div[2]/span/input[2]")).sendKeys("garages automobiles" + Keys.ARROW_DOWN + Keys.ENTER);
		
	}

	@Then("^select a votre pack$")
public void select_a_votre_pack	() throws Throwable {
	isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.VOLTREPACK);
	
	}


	@Then("^click on calender icon for photos$")
	public void click_on_calender_icon_for_photos() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CALENDERICON);
	}

	@Then("^select a date$")
	public void select_a_date() throws Throwable {
		while(!webDriver.findElement(By.className("ui-datepicker-month")).getText().contains("juillet"))
		{
			webDriver.findElement(By.className("ui-icon ui-icon-circle-triangle-e")).click();
		}
	
		int count = webDriver.findElements(By.cssSelector("[data-handler='selectDay']")).size();
		for(int i=0;i<count;i++) 
		{
			String text = webDriver.findElements(By.cssSelector("[data-handler='selectDay']")).get(i).getText();
			if(text.equalsIgnoreCase("11"))
			{
				webDriver.findElements(By.cssSelector("[data-handler='selectDay']")).get(i).click();
				break;
			}
		}
		
		}

	@Then("^click on photos Heure$")
	public void click_on_photos_Heure() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.PHOTOS);
	}

	@Then("^select a time$")
	public void select_a_time() throws Throwable {
		Select select = new Select(webDriver.findElement(By.id(("dropdownlist"))));
		select.selectByIndex(3);
	}

	

	@Then("^click on arrow icon$")
	public void click_on_arrow_icon() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.ARROWICON);
	}

	@Then("^enter mobile number$")
	public void enter_mobile_number() throws Throwable {
		EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.MOBILENUMBER,
				BoostDigital_CreateNewProposal_SocialClic_Constants.MOBILENUMBERVALUE);
	}

	@Then("^enter email address$")
	public void enter_email_address() throws Throwable {
EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.EMAILADDRESS, BoostDigital_CreateNewProposal_SocialClic_Constants.EMAILADDRESSVALUE);
	}

	@Then("^enter web address$")
	public void enter_web_address() throws Throwable {
EnterText(BoostDigital_CreateNewProposal_SocialClic_Constants.WEBADDRESS, BoostDigital_CreateNewProposal_SocialClic_Constants.WEBADDRESSVALUE);
	}
	
	@Then("^click on configure product completion$")
	public void click_on_configure_product_completion() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.CONFIGUREPRODUCT);
	}
	

	@Then("^click on ok in the Social Clic attention pop up$")
	public void click_on_ok_in_the_Social_Clic_attention_pop_up() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.ATTENTIONOKBUTTON);
	}

	@Then("^click on quitter in catalogue screen$")
	public void click_on_quitter_in_catalogue_screen() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNewProposal_SocialClic_Constants.QUITTERBUTTON);
	}
	
	
	@Then("^verify that the created proposal is shown in the propositions commerciales screen$")
	public void verify_that_the_created_proposal_is_shown_in_the_propositions_commerciales_screen() throws Throwable {
		assertText(BoostDigital_CreateNewProposal_SocialClic_Constants.PROPOSALNAMETWO, "TEST" );
			
	}
	
	
}