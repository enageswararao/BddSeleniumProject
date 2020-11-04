package com.example;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.config.TestConfig;
import com.example.pages.HomePage;
import com.example.pages.LoginPage;

 

@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext
public class PageRepository
{
    @Autowired
    protected HomePage homePage;

    @Autowired
    protected LoginPage loginPage;


}
