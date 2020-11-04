$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/Login.feature");
formatter.feature({
  "line": 1,
  "name": "Gmail Login Functionality",
  "description": "",
  "id": "gmail-login-functionality",
  "keyword": "Feature"
});
formatter.before({
  "duration": 52287800,
  "status": "passed"
});
formatter.scenario({
  "line": 3,
  "name": "Login Successful",
  "description": "",
  "id": "gmail-login-functionality;login-successful",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "I navigated to google page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I click on SigIn button",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I should see create account link",
  "keyword": "Then "
});
formatter.match({
  "location": "GooglePageStepDefs.i_navigated_to_google_page()"
});
formatter.result({
  "duration": 32642454500,
  "status": "passed"
});
formatter.match({
  "location": "GooglePageStepDefs.i_click_on_SigIn_button()"
});
formatter.result({
  "duration": 18915145600,
  "status": "passed"
});
formatter.match({
  "location": "GooglePageStepDefs.i_should_see_create_account_link()"
});
formatter.result({
  "duration": 21733553100,
  "error_message": "java.lang.AssertionError: \nExpected: is \u003ctrue\u003e\n     but: was \u003cfalse\u003e\r\n\tat org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:20)\r\n\tat org.junit.Assert.assertThat(Assert.java:956)\r\n\tat org.junit.Assert.assertThat(Assert.java:923)\r\n\tat com.example.stepDefs.GooglePageStepDefs.i_should_see_create_account_link(GooglePageStepDefs.java:33)\r\n\tat ✽.Then I should see create account link(src/test/resources/features/Login.feature:7)\r\n",
  "status": "failed"
});
formatter.after({
  "duration": 78300,
  "status": "passed"
});
});