package com.example.driver.element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

 

public class ConditionFactory {

    public static ExpectedCondition<WebElement> elementPresent(final By locator) {
        return ExpectedConditions.presenceOfElementLocated(locator);
    }

    public static ExpectedCondition<WebElement> elementVisible(final By locator) {
        return ExpectedConditions.visibilityOfElementLocated(locator);
    }

    public static ExpectedCondition<Boolean> elementInvisible(final By locator) {
        return ExpectedConditions.invisibilityOfElementLocated(locator);
    }

    public static ExpectedCondition<WebElement> elementClickable(final By locator) {
        return ExpectedConditions.elementToBeClickable(locator);
    }

    public static ExpectedCondition<Boolean> elementSelectionState(final By locator, boolean selected) {
        return ExpectedConditions.elementSelectionStateToBe(locator, selected);
    }

    public static ExpectedCondition<Alert> alertPresent() {
        return ExpectedConditions.alertIsPresent();
    }


    public static ExpectedCondition<Boolean> elementNotPresent(final By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(locator);
                    return false;
                } catch (NoSuchElementException noSuchElementException) {
                    return true;
                }
            }

        };
    }


    public static ExpectedCondition<Boolean> elementWithText(final By locator, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    for (WebElement webElement : driver.findElements(locator)) {
                        if (webElement.getText().equals(text)) {
                            if (!webElement.isDisplayed())
                                return null;
                            else
                                return true;
                        }
                    }
                    return false;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be visible in element found by %s", text, locator);
            }
        };
    }


    public static ExpectedCondition<Boolean> elementVisibleText(final By locator, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    if (!driver.findElement(locator).isDisplayed())
                        return null;
                    String elementText = driver.findElement(locator).getText();
                    return elementText.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be visible in element found by %s", text, locator);
            }
        };
    }


    public static ExpectedCondition<Boolean> elementVisibleValue(final By locator, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    if (!driver.findElement(locator).isDisplayed())
                        return null;
                    String elementText = driver.findElement(locator).getAttribute("value");
                    if (elementText != null) {
                        return elementText.contains(text);
                    } else {
                        return false;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be the value of the visible element located by %s", text, locator);
            }
        };
    }


    public static <T> ExpectedCondition<T> refreshed(final ExpectedCondition<T> condition) {
        return new ExpectedCondition<T>() {
            @Override
            public T apply(WebDriver driver) {
                try {
                    return condition.apply(driver);
                } catch (StaleElementReferenceException e) {
                    return null;
                } catch (UnhandledAlertException unhandledAlertException) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("%s", condition);
            }
        };
    }


    public static ExpectedCondition<Boolean> elementAttributeContains(final By locator, final String attribute, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementAttribute = driver.findElement(locator).getAttribute(attribute);
                    return elementAttribute.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                } catch (NullPointerException npe) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be found in attribute ('%s') in element found by %s", text, attribute, locator);
            }
        };
    }

}
