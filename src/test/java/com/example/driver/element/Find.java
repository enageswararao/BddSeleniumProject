package com.example.driver.element;
 
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.xpath;

 

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Find {
    
    @Autowired
    protected WebDriver webDriver;


    public WebElement element(By locator) {
        for (int i = 0; i < 5; i++) {
            try {
                return webDriver.findElement(locator);
            } catch (UnhandledAlertException unhandledAlertException) {
                Times.waitForMilliSeconds(Times.SECOND_PAUSE);
            }
        }
        throw new RuntimeException("Common.findElement -> Tried 5 times to overcome unhandled alert exception before failing test");
    }


    Select select(By locator) {
        return new Select(element(locator));
    }



    public List<WebElement> elements(By locator) {
        for (int i = 0; i < 5; i++) {
            try {
                return webDriver.findElements(locator);
            } catch (UnhandledAlertException unhandledAlertException) {
                Times.waitForMilliSeconds(Times.SECOND_PAUSE);
            }
        }
        throw new RuntimeException("Common.findElements -> Tried 5 times to overcome unhandled alert exception before failing test");
    }



    public List<WebElement> elementsWithText(By locator, String text) {
        List<WebElement> webElements = elements(locator);
        List<WebElement> matched = new ArrayList<>();
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(text))
                matched.add(webElement);
        }
        return matched;
    }


    public WebElement focusedElement() {
        return webDriver.switchTo().activeElement();
    }



    public WebElement parentElement(WebElement child) {
        return child.findElement(xpath(".."));
    }
}
