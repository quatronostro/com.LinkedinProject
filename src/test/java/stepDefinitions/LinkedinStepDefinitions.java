package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.LinkedinPage;
import sheet.SheetAndJava;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JSUtilities;
import utilities.ReusableMethods;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class LinkedinStepDefinitions {
    LinkedinPage linkedinPage = new LinkedinPage();
    public static HashSet<String> companyNamesSet;
    public static HashSet<String> apiResponseSet;
    public static List<String> companyNamesList = new ArrayList<>();
    public static List<String> apiResponseList = new ArrayList<>();
    public static HashSet<String> misMatchedSet;
    public static List<List<Object>> values;
    public static List<String> misMatchedCompanyList;

    @Given("Webdriver goes to linkedin")
    public void webdriverGoesToLinkedin() {
        Driver.getDriver().get(ConfigReader.getProperty("linkedinUrl"));
    }
    @Then("Enters valid account info and cliks Sign In button")
    public void enters_valid_account_info_and_cliks_sign_in_button() {
        try {
            Assert.assertTrue(linkedinPage.emailBox.isDisplayed());
        }catch (Exception e){
            Driver.getDriver().navigate().refresh();
        }
        linkedinPage.emailBox.sendKeys(ConfigReader.getProperty("linkedinEmail"));
        linkedinPage.passBox.sendKeys(ConfigReader.getProperty("linkedinPass"));
        linkedinPage.signInButton.click();
        ReusableMethods.sleep(2);
    }
    @Then("Clicks Jobs button on header")
    public void clicks_jobs_button_on_header() {
        linkedinPage.jobsButton.click();
        ReusableMethods.sleep(1);
    }
    @Then("Clicks My Jobs button then clicks Applied Jobs button")
    public void clicks_my_jobs_button_then_clicks_applied_jobs_button() {
        linkedinPage.myJobsButton.click();
        ReusableMethods.sleep(1);
        linkedinPage.appliedButton.click();
        ReusableMethods.sleep(1);
    }
    @And("Takes company names")
    public void takesCompanyNames() throws GeneralSecurityException, IOException {

        // for Linkedin
        while (linkedinPage.nextButton.isEnabled()){
            ReusableMethods.sleep(2);
            for (int i = 0; i < linkedinPage.companyNameList.size(); i++) {
                companyNamesList.add((linkedinPage.companyNameList.get(i).getText()));
            }
            ReusableMethods.sleep(2);
            JSUtilities.scrollToElement(Driver.getDriver(), linkedinPage.nextButton);
            try {
                linkedinPage.nextButton.click();
            } catch (Exception e) {
                break;
            }
            ReusableMethods.sleep(1);
        }

        // for API
        values = (List<List<Object>>) SheetAndJava.getValues();

        for (int i = 0; i < values.size(); i++) {
            apiResponseList.add(values.get(i).get(0).toString());
        }
    }
    @And("Verify that all company names are matching with sheet")
    public void verifyThatAllCompanyNamesAreMatchingWithSheet() {

        companyNamesSet = new HashSet<>(companyNamesList);
        apiResponseSet = new HashSet<>(apiResponseList);

        System.out.println("===Verify===");

        if (companyNamesSet.equals(apiResponseSet)) {
            System.out.println("Lists are the same");
        } else {
            // Find the mismatched elements by calculating the symmetric difference between the sets
            misMatchedSet = new HashSet<>(companyNamesSet);
            misMatchedSet.removeAll(apiResponseSet);
            misMatchedCompanyList = new ArrayList<>(misMatchedSet);
        }

        Iterator<String> iterator1 = companyNamesSet.iterator();
        Iterator<String> iterator2 = apiResponseSet.iterator();
        // print
        while (iterator1.hasNext()){
            System.out.println("Company Name from Linkedin = " + iterator1.next());
        }
        while (iterator2.hasNext()){
            System.out.println("Company Name from API = " + iterator2.next());
        }
        try {
            for (String eachCompany : misMatchedCompanyList) {
                System.out.println("Mismatched company name = " + eachCompany);
            }
        } catch (NullPointerException e) {
            System.out.println("Lists are the same, so there is no mismatched company name...");
        }

    }
    @And("Add mismatched data to sheet")
    public void addMismatchedDataToSheet() throws GeneralSecurityException, IOException {

        try{
            for (int i = 0; i < misMatchedCompanyList.size(); i++) {

                String choosenOne = misMatchedCompanyList.get(i);
                boolean elementFound = false;

                while (!elementFound && linkedinPage.previousButton.isEnabled()) {
                    // Check if the element exists on the current page
                    List<WebElement> elements = Driver.getDriver().findElements(By.xpath("//*[text()='" + choosenOne + "']"));
                    if (elements.size() > 0) {
                        WebElement choosenOneXpath = elements.get(0);
                        // If the element is found, set the flag to true
                        elementFound = true;
                        choosenOneXpath.click();
                    } else {
                        // If the element is not found, click the previous button and continue to the next iteration
                        linkedinPage.previousButton.click();
                    }
                }
                ReusableMethods.sleep(2);

                String jobTitleText = null;
                try {
                    jobTitleText = linkedinPage.jobTitle.getText();
                } catch (Exception e) {
                    jobTitleText = "N/A";
                }
                String companyNameText = null;
                try {
                    companyNameText = linkedinPage.companyName.getText();
                } catch (Exception e) {
                    companyNameText = "N/A";
                }
                String locationText = null;
                try {
                    locationText = linkedinPage.location.getText();
                } catch (Exception e) {
                    locationText = "N/A";
                }
                String workPlaceTypeText = null;
                try {
                    workPlaceTypeText = linkedinPage.workPlaceType.getText();
                } catch (Exception e) {
                    workPlaceTypeText = "N/A";
                }
                String jobInsightText = null;
                try {
                    jobInsightText = linkedinPage.jobInsight.getText();
                } catch (Exception e) {
                    jobInsightText = "N/A";
                }
                // Send Job application info to Google Sheets
                SheetAndJava.putValue(jobTitleText, companyNameText, locationText, workPlaceTypeText, jobInsightText);

                Driver.getDriver().navigate().back();
            }
        } catch (NullPointerException exception) {
            System.out.println("Everything is matching with the Google Sheets Document!");
        }

        Driver.closeDriver();
    }
}
