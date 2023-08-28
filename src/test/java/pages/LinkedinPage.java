package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class LinkedinPage {

    public LinkedinPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//*[@autocomplete='username']")
    public WebElement emailBox;

    @FindBy(xpath = "//*[@autocomplete='current-password']")
    public WebElement passBox;

    @FindBy(xpath = "//*[@data-id='sign-in-form__submit-btn']")
    public WebElement signInButton;

    @FindBy(xpath = "//*[@title='Jobs']")
    public WebElement jobsButton;

    @FindBy(xpath = "(//*[text()='My jobs'])[1]")
    public WebElement myJobsButton;

    @FindBy(xpath = "//*[text()='Applied'][1]")
    public WebElement appliedButton;

    @FindBy(xpath = "//*[@class='entity-result__primary-subtitle t-14 t-black t-normal']")
    public List<WebElement> companyNameList;

    @FindBy(xpath = "//*[text()='Next']")
    public WebElement nextButton;

    @FindBy(xpath = "//*[text()='Previous']")
    public WebElement previousButton;

    @FindBy(xpath = "//h1[@class]")
    public WebElement jobTitle;

    @FindBy(xpath = "(//a[@href])[9]")
    public WebElement companyName;

    @FindBy(xpath = "//span[@class='white-space-pre']")
    public WebElement startElement;

    @FindBy(xpath = "(//span[@class='white-space-pre'])[2]")
    public WebElement endElement;

    @FindBy(xpath = "(//span[@class='white-space-pre'])[2]")
    public WebElement startElement2;

    @FindBy(xpath = "(//span[@class='white-space-pre'])[3]")
    public WebElement endElement2;

    @FindBy(xpath = "//div[3]/ul/li[1]/span")
    public WebElement jobInsight;

}
