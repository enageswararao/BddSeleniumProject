package com.example.driver.element;

 
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;

import java.util.ArrayList;
import java.util.List;

 
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class Get {

  private final static Logger LOGGER = LoggerFactory.getLogger(Act.class);
  private static final Get INSTANCE = new Get();

  @Autowired
  private Find find;

  @Autowired
  private WaitFor waitFor;

  public List<String> elementsAttribute(By locator, String attribute) {
    List<String> listAttribute = new ArrayList<String>();
    List<WebElement> webElementList = find.elements(locator);
    for (WebElement webElement : webElementList) {
      listAttribute.add(webElement.getAttribute(attribute));
    }
    return listAttribute;
  }


  public String elementAttribute(By locator, String attribute) {
    waitFor.elementPresent(locator);
    return find.element(locator).getAttribute(attribute);
  }


  public String elementCssValue(By locator, String property) {
    waitFor.elementPresent(locator);
    return find.element(locator).getCssValue(property);
  }


  public int elementCount(By locator) {
    try {
      return find.elements(locator).size();
    } catch (NoSuchElementException e) {
      return 0;
    }
  }


  public String elementText(By locator) {
    return elementText(locator, Times.TWENTY_SECOND_WAIT);
  }


  public String elementText(By locator, int waitTime) {
    for (int i = 0; i < 10; i++) {
      try {
        waitFor.elementPresent(locator, waitTime);
        return find.element(locator).getText();
      } catch (StaleElementReferenceException e) {
        LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
        waitFor.milliSeconds(500);
      }
    }
    throw new RuntimeException("Could not find: " + locator.toString());
  }


  public String elementHtml(By locator) {
    for (int i = 0; i < 10; i++) {
      try {
        waitFor.elementPresent(locator);
        return find.element(locator).getAttribute("innerHTML");
      } catch (StaleElementReferenceException e) {
        LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
        waitFor.milliSeconds(500);
      }
    }
    throw new RuntimeException("Could not find: " + locator.toString());
  }


  public String elementValue(By locator) {
    waitFor.elementVisible(locator);
    return elementAttribute(locator, "value");
  }


  public String selectedValue(By locator) {
    waitFor.elementVisible(locator);
    return find.select(locator).getFirstSelectedOption().getText();
  }


  public List<String> optionsText(By locator) {
    for (int i = 0; i < 10; i++) {
      try {
        List<String> optionsText = new ArrayList<String>();
        List<WebElement> optionElementList = optionsElements(locator);
        for (WebElement webElement : optionElementList) {
          if (!webElement.getText().equals("--Please select--") && !webElement.getText()
              .equals("-- Please select --")) {
            optionsText.add(webElement.getText());
          }
        }
        return optionsText;
      } catch (StaleElementReferenceException e) {
        LOGGER.warn("Caught StaleElementReferenceException : " + i + " times");
        waitFor.milliSeconds(500);
      }
    }
    throw new RuntimeException("Could not find: " + locator.toString());
  }


  private List<WebElement> optionsElements(By locator) {
    waitFor.elementVisible(locator);
    return find.select(locator).getOptions();
  }


  public List<String> elementsText(By locator) {
    List<String> listText = new ArrayList<String>();
    List<WebElement> webElementList;
    try {
      webElementList = find.elements(locator);
      for (WebElement webElement : webElementList) {
        listText.add(webElement.getText());
      }
    } catch (StaleElementReferenceException e) {
      waitFor.milliSeconds(Times.TEN_SECOND_PAUSE);
      webElementList = find.elements(locator);
      for (WebElement webElement : webElementList) {
        listText.add(webElement.getText());
      }
    }
    return listText;
  }


  public List<String> elementsUniqueText(By locator) {
    List<String> listText = new ArrayList<String>();
    List<WebElement> webElementList = find.elements(locator);
    for (WebElement webElement : webElementList) {
      if (!listText.contains(webElement.getText())) {
        listText.add(webElement.getText());
      }
    }
    return listText;
  }


  public String focusedElementId() {
    return find.focusedElement().getAttribute("id");
  }


  public boolean areAllElementsSelected(By locator) {
    boolean allChecked = true;
    List<WebElement> elements = find.elements(locator);
    for (WebElement element : elements) {
      if (element.isEnabled()) {
        if (!element.isSelected()) {
          allChecked = false;
          break;
        }
      }
    }
    return allChecked;
  }

  public boolean areListValuesDisabled(By locator, List<String> expDisabledValues) {
    List<WebElement> options = optionsElements(locator);
    boolean allDisabled = true;

    for (WebElement element : options) {
      String optionText = element.getText();
      for (String value : expDisabledValues) {
        if (optionText.equals(value)) {
          if (element.isEnabled()) {
            allDisabled = false;
            break;
          }
        }
      }
    }

    return allDisabled;
  }

  public String focusedElementAttribute(String attribute) {
    return find.focusedElement().getAttribute(attribute);
  }

  public String attributeOfFocusedParentElement(String attribute) {
    return find.parentElement(find.focusedElement()).getAttribute(attribute);
  }

}
