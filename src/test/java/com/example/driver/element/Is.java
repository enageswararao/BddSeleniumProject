package com.example.driver.element;

 
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;
 

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Is {

    @Autowired
    protected Find find;

    @Autowired
    protected WaitFor waitFor;



    public boolean elementPresent(By locator) {
        try {
            find.element(locator);
            return true;
        } catch (NoSuchElementException noSuchElementException) {
            return false;
        }
    }



    public boolean elementPresent(By locator, int waitTime) {
        try {
            waitFor.elementPresent(locator, waitTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean elementNotPresent(By locator) {
        return !elementPresent(locator);
    }



    public boolean elementNotPresent(By locator, int waitTime) {
        try {
            waitFor.elementNotPresent(locator, waitTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean elementVisible(By locator) {
        if (elementPresent(locator))
            return find.element(locator).isDisplayed();
        else
            return false;
    }


    public boolean elementVisible(By locator, int waitTime) {
        try {
            waitFor.elementVisible(locator, waitTime);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException t) {
            return false;
        }
    }



    public boolean elementInvisible(By locator) {
        return !elementVisible(locator);
    }



    public boolean elementInvisible(By locator, int waitTime) {
        try {
            waitFor.elementInvisible(locator, waitTime);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }



    public boolean elementClickable(By locator, int waitTime) {
        try {
            waitFor.elementClickable(locator, waitTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean elementEnabled(By locator) {
        return find.element(locator).isEnabled();
    }



    public boolean elementEnabled(By locator, int waitTime) {
        try {
            waitFor.elementClickable(locator, waitTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean elementChecked(By locator) {
        try {
            return elementAttributeContaining(locator, "aria-checked", "true");
        } catch (Exception e) {
            return elementAttributeContaining(locator, "class", "checked");
        }
    }


    public boolean elementSelected(By locator) {
        return find.element(locator).isSelected();
    }



    public boolean elementActive(By locator) {
        return elementAttributeContaining(locator, "class", "active");
    }



    boolean elementAttributeContaining(By locator, String attribute, String text) {
        return (find.element(locator).getAttribute(attribute)).contains(text);
    }



    public boolean alertPresent() {
        try {
            waitFor.alert(Times.SECOND_WAIT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
