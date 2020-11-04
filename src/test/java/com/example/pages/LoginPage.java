package com.example.pages;

import com.example.locators.BasePageLocators;
import com.example.locators.LoginPageLocators;

public class LoginPage extends BaseActions
{
    public void clickOnLoginButton() {
        action.clearElement(BasePageLocators.LOGIN_BUTTON);
    }

    public boolean isCreateAccountLinkDisplayed() {
        return is.elementVisible(LoginPageLocators.HEADER);
    }
}
