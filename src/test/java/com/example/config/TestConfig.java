package com.example.config;

 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.example.PageRepository;
import com.example.driver.element.Act;
import com.example.driver.element.Find;
import com.example.driver.element.Get;
import com.example.driver.element.Is;
import com.example.driver.element.WaitFor;
import com.example.driver.javascript.Js;
import com.example.pages.HomePage;
import com.example.pages.LoginPage;

 
import io.github.bonigarcia.wdm.WebDriverManager;
 

@Configuration
@ComponentScan(basePackages = "com.example")
@PropertySource("classpath:conf.properties")
public class TestConfig
{
    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${driver}")
    private String driver;

    @Autowired
    private WebDriver webDriver;

    @Bean
    public WebDriver getWebDriver()
    {
       switch (driver)
       {
           case "Chrome":
              
             
               WebDriverManager.chromedriver().setup();
               return new ChromeDriver();
               default:
                   throw new IllegalArgumentException("unknown driver " + driver);

           case "Firefox":
              
               WebDriverManager.firefoxdriver().setup();
               return new FirefoxDriver();
       }
    }

    @Bean
    public PageRepository getPageRepository() {
        return new PageRepository();
    }



    @Bean
    public Act getAct() {
        return new Act();
    }

    @Bean
    public Find getFind() {
        return new Find();
    }

    @Bean
    public Get getInstance() {
        return new Get();
    }

    @Bean
    public Is getTheIstanceOfIs() {
        return new Is();
    }

    @Bean
    public WaitFor getWaitFor() {
        return new WaitFor();
    }

    @Bean
    public Js getJs() {
        return new Js();
    }

    @Bean
    public HomePage getHomePage() {
        return new HomePage();
    }

    @Bean
    public LoginPage getLoginPage() {
        return new LoginPage();
    }

}
