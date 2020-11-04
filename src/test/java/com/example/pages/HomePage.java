package com.example.pages;

 
import org.springframework.beans.factory.annotation.Value;

import com.example.locators.HomePageLocators;

 
public class HomePage extends BaseActions
{

    @Value("${BaseUrl}")
    protected String baseUrl;

    public void clickOnLogIn()
    {
        action.clickElement(HomePageLocators.SIGNIN_BUTTON);
    }

    public void openGooglePage()
    {
        action.navigateTo(baseUrl);
    }

    public void clickSignInButton()
    {
        action.clickElement(HomePageLocators.SIGNIN_BUTTON);
    }

}
