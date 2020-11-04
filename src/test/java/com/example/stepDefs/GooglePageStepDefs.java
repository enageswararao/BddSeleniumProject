package com.example.stepDefs;
 
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.PageRepository;
import com.example.driver.element.Act;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

 

public class GooglePageStepDefs extends PageRepository
{
    @Given("^I navigated to google page$")
    public void i_navigated_to_google_page()  {
        homePage.openGooglePage();
    }

    @When("^I click on SigIn button$")
    public void i_click_on_SigIn_button()  {
        homePage.clickOnLogIn();
    }

    @Then("^I should see create account link$")
    public void i_should_see_create_account_link()  {
        assertThat(loginPage.isCreateAccountLinkDisplayed(), is(equalTo(true)));
    }
}
