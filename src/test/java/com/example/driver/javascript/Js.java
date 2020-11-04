package com.example.driver.javascript;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;
import com.example.driver.element.Times;
import com.example.driver.element.WaitFor;


@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Js {

    @Autowired
    protected WebDriver webDriver;

    @Autowired
    protected WaitFor waitFor;
    
    private JavascriptExecutor javascriptExecutor() {
        return (JavascriptExecutor) webDriver;
    }


    public Object getEval(String query) {
        int count = 5;
        while (count > 0) {
            try {
                return javascriptExecutor().executeScript(query);
            } catch (WebDriverException e) {
                // e.printStackTrace();
                waitFor.milliSeconds(Times.SECOND_PAUSE);
                count--;
            }
        }
        if (query.length() > 1000)
            query = query.substring(0, 1000) + "...";
        throw new RuntimeException("Failed to run javascript query 5 times before failing : " + query);
    }


    public Object getEval(String query, Object object) {
        int count = 5;
        while (count > 0) {
            try {
                return javascriptExecutor().executeScript(query, object);
            } catch (WebDriverException e) {
                e.printStackTrace();
                waitFor.milliSeconds(Times.SECOND_PAUSE);
                count--;
            }
        }
        if (query.length() > 1000)
            query = query.substring(0, 1000) + "...";
        throw new RuntimeException("Failed to run javascript query 5 times on object: " + object.toString() + " before failing: " + query);
    }


    public void waitForEvalToMatch(String query, Object expected, int waitTimeInSeconds) {
        for (int i = 0; i < waitTimeInSeconds; i++) {
            if (getEval(query).equals(expected))
                return;
            waitFor.milliSeconds(Times.SECOND_PAUSE);
        }
        throw new RuntimeException("Failed to match object returned by javascript query:" + waitTimeInSeconds);
    }


    public void removeAttribute(String locator, String attribute) {
        getEval(String.format("document.querySelector('%s').removeAttribute('%s');", locator, attribute));
    }


    public String getAttributeValues(String locator, String attribute) {
        return getEval(String.format("return document.querySelector('%s').getAttribute('%s');", locator, attribute)).toString();
    }


    public void setAttributeValue(String locator, String attribute, String values) {
        getEval(String.format("document.querySelector('%s').setAttribute('%s','%s');", locator, attribute, values));
    }


    public void setValue(String locator, String value) {
        getEval(String.format("document.querySelector('%s').value = '%s'", locator, value));
    }


    public void enableInput(String locatorId) {
        waitFor.elementPresent(By.id(locatorId));
        getEval(String.format("document.getElementById('%s').disabled = false;", locatorId));
    }


    public void clickElement(String locator) {
        getEval(String.format("document.querySelector(\"%s\").click()", locator));
    }


    public boolean isElementVisible(String locator) {
        try {
            return (Boolean) getEval(String.format("if(document.querySelector('%s').offsetWidth === 0 && document.querySelector('%s').offsetHeight === 0) { return false; } else { return true; }", locator, locator));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<String> getListOfOptions(String locator) {
        List<String> listOfOptions = new ArrayList<>();
        for (int i = 0; i < getNumberOfOptions(locator); i++) {
            listOfOptions.add((String) getEval(String.format("var option = document.querySelector('%s'); return option[%s].text;", locator, i)));
        }

        return listOfOptions;
    }


    public String getElementTextByCss(String locator) {
        return (String) getEval(String.format("return document.querySelector('%s').innerText", locator));
    }


    public String getElementValue(String locator) {
        return (String) getEval(String.format("return document.querySelector(\"%s\").value", locator));
    }

    public void selectOption(String locator, String value) {
        for (int i = 0; i < getNumberOfOptions(locator); i++) {
            getEval(String.format("var select = document.querySelector('%s'); if(select[" + i + "].text == '%s') { select.selectedIndex = " + i + "; }", locator, value));
        }
    }


    int getNumberOfOptions(String locator) {
        long numberOfOptions = (Long) getEval(String.format("return document.querySelector('%s').length", locator));
        return (int) numberOfOptions;
    }


    public String getSelectedValue(String locator) {
        return (String) getEval(String.format("var e=document.querySelector('%s'); return e.options[e.selectedIndex].text;", locator));
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(Times.THREE_SECOND_PAUSE);
    }


    public void waitForPageToLoad(long delayExecution) {
        long timeInMilliSeconds = new Date().getTime();
        long timeout = timeInMilliSeconds + 60000;
        String state = null;
        try {
            if (delayExecution > 0l)
                Thread.sleep(delayExecution);

            while (timeInMilliSeconds < timeout) {
                state = (String) getEval("return document.readyState;");
                if (state.equals("complete"))
                    return;
                timeInMilliSeconds = new Date().getTime();
            }

            if (new Date().getTime() >= timeout)
                throw new RuntimeException("Timed out after 60 seconds waiting for page to finish loading. Last known state: " + state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}