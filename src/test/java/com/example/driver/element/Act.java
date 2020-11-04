package com.example.driver.element;

 
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;
import com.example.driver.javascript.Js;

import java.util.List;
 

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Act {

    private final static Logger LOGGER = LoggerFactory.getLogger(Act.class);
    
    @Autowired
    protected WebDriver webDriver;
    
    @Autowired
    protected Find find;
    
    @Autowired
    protected WaitFor waitFor;
    
    @Autowired
    protected Js js;

    /**
     * Click on an Element
     * @param locator By locator for a specific WebElement
     */
    public void clickElement(By locator) {
        try {
            waitFor.elementClickable(locator);
            find.element(locator).click();
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            waitFor.elementClickable(locator);
            find.element(locator).click();
        }
    }

    public void navigateTo(String url)
    {
        webDriver.get(url);
    }

    /**
     * Click on an Element
     * @param webElement By locator for a specific WebElement
     */
    void clickElement(WebElement webElement) {
        try {
            webElement.click();
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            webElement.click();
        }
    }

    /**
     * Clicks on the visible WebElement
     * throws RuntimeException is multiple webelements are found to be visible
     * @param locator
     */
    public void clickVisibleElement(By locator) {
        List<WebElement> elems = find.elements(locator);
        boolean found = false;
        WebElement target = null;
        for (WebElement elem : elems) {
            if (elem.isDisplayed()) {
                if (found)
                    throw new RuntimeException("Multiple Visible Elements Found");
                found = true;
                target = elem;
            }
        }
        clickElement(target);
    }

    /**
     * Click on an Element that is repeated in the page specified by index
     * @param locator By locator for a specific WebElement
     */
    public void clickElement(By locator, int index) {
        try {
            waitFor.elementClickable(locator);
            find.elements(locator).get(index).click();
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            waitFor.elementClickable(locator);
            find.elements(locator).get(index).click();
        }
    }

    /**
     * Performs a click using the actions builder
     * @param element
     */
    public void clickElementWithBuilder(WebElement element) {
        Actions builder = new Actions(webDriver);
        builder.moveToElement(element).click(element).perform();
    }

    /**
     * Double click on an element
     * @param locator By locator for WebElement to double click on
     */
    public void doubleClickElement(By locator) {
        Actions builder = new Actions(webDriver);
        builder.doubleClick(find.element(locator)).perform();
    }

    /**
     * Click the element that matches the text specified
     * @param locator By for element to click
     * @param text    String for the text to search for
     */
    public void clickElementWithText(By locator, String text) {
        waitFor.elementVisible(locator);
        List<WebElement> elements = find.elementsWithText(locator, text);
        if (elements.size() == 0)
            throw new RuntimeException("Locator(" + locator + ") with text '" + text + "' was not found!");
        else if (elements.size() > 1)
            throw new RuntimeException("Multiple locators(" + locator + ") with text '" + text + "' were found!");
        clickElement(elements.get(0));
    }

    /**
     * Clears an Element of it's value
     * @param locator By locator for a specific WebElement
     */

    public void clearElement(By locator) {
        waitFor.elementVisible(locator);
        find.element(locator).clear();
    }

    /**
     * Types in an element (appends if not clear)
     * @param locator By locator for a specific WebElement
     * @param value   Value to type into Element
     */
    public void typeInElement(By locator, String value) {
        try {
            find.element(locator).sendKeys(value);
        } catch (Exception e) {
            LOGGER.info("Caught " + e.getMessage() + ". Trying again.");
            waitFor.elementVisible(locator);
            find.element(locator).sendKeys(value);
        }
    }

    /**
     * Updates an Element with value (clears it first)
     * @param locator By locator for a specific WebElement
     * @param value   Value to type into Element
     */
    public void updateElement(By locator, String value) {
        clearElement(locator);
        typeInElement(locator, value);
    }

    /**
     * Select an option
     * @param locator     By locator for a specific select WebElement
     * @param visibleText Text visible in option
     */
    public void selectOption(By locator, String visibleText) {
        waitFor.elementVisible(locator);
        find.select(locator).selectByVisibleText(visibleText);
    }

    /**
     * Multi select elements
     * @param locator By locator for a specific select WebElement
     */
    public void multiSelect(By locator) {
        List<WebElement> allElements = find.elements(locator);
        for (WebElement element : allElements) {
            if (!element.isSelected()) {
                element.click();
                try {
                    waitFor.elementToBeSelected(locator);
                } catch (Exception e) {
                    LOGGER.info("Element not selected. Trying again.");
                    element.click();
                    waitFor.elementToBeSelected(locator);
                }
            }
        }
    }

    /**
     * Select multiple options in a multi-select list
     * @param locator    The list locator
     * @param selections Options to be selected
     */
    public void multiSelectListOptions(By locator, List<String> selections) {
        for (String selection : selections) {
            find.element(locator).sendKeys(Keys.CONTROL);
            find.select(locator).selectByVisibleText(selection);
        }
    }

    /**
     * Scrolls the parameter into view area
     */
    public void scrollElementIntoView(By locator) {
        waitFor.elementPresent(locator);
        scrollElementIntoView(find.element(locator));
    }

    /**
     * Scrolls the parameter into view area
     */
    void scrollElementIntoView(WebElement webElement) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
        waitFor.milliSeconds(500);
    }

    /**
     * Scrolls to the top of the current page
     */
    public void scrollToTopOfPage() {
        ((JavascriptExecutor) webDriver).executeScript("scroll(0,0);");
        waitFor.milliSeconds(500);
    }

    public void focusOnElement(By locator) {
        focusOnElement(find.element(locator));
    }

    void focusOnElement(WebElement webElement) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].focus();", webElement);
        waitFor.milliSeconds(500);
    }

    /**
     * Drag and drop an element from it's source to another element
     * @param sourceLocator Locator of the element to be dragged
     * @param targetLocator Locator of the element to drop on
     * @param horizOffset   X axis offset of the drop position - for no offset, use 0
     * @param vertOffset    Y axis offset of the drop position - for no offset, use 0
     */
    public void dragAndDropElement(By sourceLocator, By targetLocator, int horizOffset, int vertOffset) {
        WebElement sourceElement = find.element(sourceLocator);
        WebElement targetElement = find.element(targetLocator);
        dragAndDropElement(sourceElement, targetElement, horizOffset, vertOffset);
    }

    /**
     * Drag and drop an element from it's source to another element
     * @param sourceElement
     * @param targetElement
     * @param horizOffset   X axis offset of the drop position - for no offset, use 0
     * @param vertOffset    Y axis offset of the drop position - for no offset, use 0
     */
    void dragAndDropElement(WebElement sourceElement, WebElement targetElement, int horizOffset, int vertOffset) {
        Actions builder = new Actions(webDriver);
        Action drag = builder.clickAndHold(sourceElement).moveToElement(targetElement, horizOffset, vertOffset).build();
        Action drop = builder.release().build();
        drag.perform();
        drop.perform();
        LOGGER.debug("Done drag & drop");
    }

    /**
     * Drag and drop an element to a target element
     * @param sourceLocator
     * @param targetLocator
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = find.element(sourceLocator);
        WebElement target = find.element(targetLocator);
        new Actions(webDriver).dragAndDrop(source, target).build().perform();
    }

    /**
     * Mouse over an element
     * @param locator Locator of the element to hover over
     */
    public void moveToElement(By locator) {
        moveToElement(find.element(locator));
    }

    /**
     * Mouse over an element
     * @param element The element to hover over
     */
    void moveToElement(WebElement element) {
        Actions builder = new Actions(webDriver);
        builder.moveToElement(element).perform();
    }

    /**
     * Wrapper method for upload file - no need for locator
     * @param filePath
     */
    public void uploadFile(String filePath) {
        uploadFile(By.cssSelector("input[type=file]"), filePath);
    }

    /**
     * Uploads a file using the windows dialog
     * @param locator  Locator of the file input
     * @param filePath
     */
    void uploadFile(By locator, String filePath) {
        WebElement elem = find.element(locator);
        try {
            elem.sendKeys(filePath);
        } catch (Exception e) {
            String attrVal = js.getAttributeValues(locator.toString(), "class");
            js.removeAttribute(locator.toString(), "class");
//            if (BrowserUtil.isBrowserFirefox())
//                waitFor.milliSeconds(Times.THREE_SECOND_PAUSE);
            elem.sendKeys(filePath);
            js.setAttributeValue(locator.toString(), "class", attrVal);
        }
    }

}
