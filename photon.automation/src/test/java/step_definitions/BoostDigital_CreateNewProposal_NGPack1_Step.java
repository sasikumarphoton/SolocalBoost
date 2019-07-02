package step_definitions;

import constant.BoostDigital_CreateNewProposal_SocialClic_Constants;
import constant.BoostDigital_CreateNwProposal_NGPack1_Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.CommonLibrary;

public class BoostDigital_CreateNewProposal_NGPack1_Step extends CommonLibrary {
	
	

	@Then("^enter name of proposal as NGPack(\\d+)test$")
	public void enter_name_of_proposal_as_NGPack_test(int arg1) throws Throwable {
		EnterText(BoostDigital_CreateNwProposal_NGPack1_Constants.NGPACKPROPOSALNAME,
				BoostDigital_CreateNwProposal_NGPack1_Constants.NGPACKPROPOSALNAMEVALUE);
	} 
	    
	
	@Then("^click on Solutions Presence Referencement prioritqire Nouveau in product list$")
	public void click_on_Solutions_Presence_Referencement_prioritqire_Nouveau_in_product_list() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.NGPACK1PROPOSAL);
	 
	}

	@Then("^click on Choisir cette offre$")
	public void click_on_Choisir_cette_offre() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.CHOISERCETTEOFFREBUTTON);
	    
	}

	@Then("^click on OK in the annonceur$")
	public void click_on_OK_in_the_annonceur() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.ANNOUNCEURBUTTON);
	  
	}

	@Then("^click on Configurer$")
	public void click_on_Configurer() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.CONFIGUREBUTTON);
	
	}

	@Then("^click on Valider et passertwo$")
	public void click_on_Valider_et_passertwo() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERETPASSERBUTTON2);
		
	}
	
	@Then("^click on Valider et passerthree$")
	public void click_on_Valider_et_passerthree() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERETPASSERBUTTON3);
		
	}
	
	@Then("^click on Valider et passerfour$")
	public void click_on_Valider_et_passerfour() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERETPASSERBUTTON4);
		
	}
	
	@Then("^click on Valider et passerfive$")
	public void click_on_Valider_et_passerfive() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERETPASSERBUTTON5);
		
	}
	
		
	
	@Then("^click on Valider la configuration$")
	public void click_on_Valider_la_configuration() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERLACONFIGBUTTON);
	 
	}

	@Then("^click on OK in the les etablissments$")
	public void click_on_OK_in_the_les_etablissments() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.OKBUTTON);
	  
	}

	@Then("^click on Valider la configuration et passer au choix du plan de reglement$")
	public void click_on_Valider_la_configuration_et_passer_au_choix_du_plan_de_reglement() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALIDERLACONFIGETPASSERBUTTON2);
	   
	}

	@Then("^click on Valider le plan de reglement$")
	public void click_on_Valider_le_plan_de_reglement() throws Throwable {
		isElementPresentVerifyClick(BoostDigital_CreateNwProposal_NGPack1_Constants.VALDERLEPLANDEREGLEMENTBUTTON);
	   
	}

	@Then("^check the created proposal is shown in the propositions commerciales screen$")
	public void check_the_created_proposal_is_shown_in_the_propositions_commerciales_screen() throws Throwable {
		assertText(BoostDigital_CreateNwProposal_NGPack1_Constants.PROPOSALNAME, "NGPackTest1" );
			
	}

	@Then("^close the browser$")
	public void close_the_browser() throws Throwable {
		closeBrowser();
	   
	}
	
}
