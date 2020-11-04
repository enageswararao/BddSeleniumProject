package com.example.pages;

 
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;
import com.example.driver.element.Act;
import com.example.driver.element.Find;
import com.example.driver.element.Get;
import com.example.driver.element.Is;
import com.example.driver.element.WaitFor;
import com.example.driver.javascript.Js;

 

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class BaseActions {

  @Autowired
  protected WebDriver driver;

  @Autowired
  protected Act action;

  @Autowired
  protected Find find;

  @Autowired
  protected Get get;

  @Autowired
  protected Is is;

  @Autowired
  protected WaitFor waitFor;

  @Autowired
  protected Js js;
}
