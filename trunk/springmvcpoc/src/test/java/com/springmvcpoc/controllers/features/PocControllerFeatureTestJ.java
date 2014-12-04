package com.springmvcpoc.controllers.features;
 
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
 
public class PocControllerFeatureTestJ {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
 
  @Before
  public void setUp() throws Exception {
    driver = new HtmlUnitDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
 
  @Test
  public void testWb() throws Exception {
    driver.get(baseUrl + "/springmvcpoc/");
    driver.findElement(By.id("nameTxt")).clear();
    driver.findElement(By.id("nameTxt")).sendKeys("Debdeep");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }
 
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
 
 
}