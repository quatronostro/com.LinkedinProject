package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import pages.LinkedinPage;
import sheet.SheetAndJava;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JSUtilities;
import utilities.ReusableMethods;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.NoSuchElementException;

public class LinkedinStepDefinitions {
    LinkedinPage linkedinPage = new LinkedinPage();
    public static List<String> companyNamesList = new ArrayList<>();
    public static List<String> apiResponseList = new ArrayList<>();
    public static List<List<Object>> values;
    public static List<String> misMatchedCompanyList;
    Map<String, Integer> differenceMap = new HashMap<>();
    String workPlaceType;
    String location;

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

        Map<String, Integer> mapForCompanies = new HashMap<>();
        Map<String, Integer> mapForAPI = new HashMap<>();

        System.out.println("===Verify===");

        for (String element : companyNamesList) {
            // get the current frequency of the element in map1, or 0 if not present
            int frequency = mapForCompanies.getOrDefault(element, 0);

            // increment the frequency by 1 and put it back in map1
            mapForCompanies.put(element, frequency + 1);
        }

        for (String element : apiResponseList) {
            // get the current frequency of the element in map2, or 0 if not present
            int frequency = mapForAPI.getOrDefault(element, 0);

            // increment the frequency by 1 and put it back in map2
            mapForAPI.put(element, frequency + 1);
        }

        // compare the maps for equality
        if (mapForCompanies.equals(mapForAPI)) {
            System.out.println("Lists have the same elements with the same frequency");
        } else {
            System.out.println("Lists have different elements or different frequency");

            // create a new Map or a List to store the differences
             // or List<String> differenceList = new ArrayList<>();

            // loop through each map and find the elements that are not in both maps
            for (Map.Entry<String, Integer> entry : mapForCompanies.entrySet()) {
                // get the key and value from map1
                String key = entry.getKey();
                int value = entry.getValue();

                // check if map2 has the same key and value
                if (!mapForAPI.containsKey(key) || !mapForAPI.get(key).equals(value)) {
                    // if not, add the key and value to the differenceMap or the key to the differenceList
                    differenceMap.put(key, value); // or differenceList.add(key);
                }
            }

            for (Map.Entry<String, Integer> entry : mapForAPI.entrySet()) {
                // get the key and value from map2
                String key = entry.getKey();
                int value = entry.getValue();

                // check if map1 has the same key and value
                if (!mapForCompanies.containsKey(key) || !mapForCompanies.get(key).equals(value)) {
                    // if not, add the key and value to the differenceMap or the key to the differenceList
                    differenceMap.put(key, value); // or differenceList.add(key);
                }
            }

            // print the differenceMap, this shows how many company names are not matching and how many are they
            System.out.println(differenceMap);

        }

        misMatchedCompanyList = new ArrayList<>(differenceMap.keySet());

        // reverse is not necessary but you can use
        Collections.reverse(misMatchedCompanyList);
        // just for control you can print misMatchedCompanyList
        //System.out.println(misMatchedCompanyList);
    }

    @And("Add mismatched data to sheet")
    public void addMismatchedDataToSheet() throws GeneralSecurityException, IOException {

        try{
            for (int i = 0; i < misMatchedCompanyList.size(); i++) {

                String chosenOne = misMatchedCompanyList.get(i).trim();
                boolean elementFound = false;

                while (!elementFound && linkedinPage.previousButton.isEnabled()) {
                    // Check if the element exists on the current page
                    List<WebElement> elements = Driver.getDriver().findElements(By.xpath("//*[text()='" + chosenOne + "']"));
                    if (elements.size() > 0) {
                        WebElement choosenOneXpath = elements.get(0);
                        // If the element is found, set the flag to true
                        choosenOneXpath.click();
                        elementFound = true;
                    } else {
                        // If the element is not found, click the previous button and continue to the next iteration
                        try {
                            linkedinPage.previousButton.click();
                        } catch (ElementClickInterceptedException e) {
                            linkedinPage.nextButton.click();
                        }
                    }
                }

                ReusableMethods.sleep(2);

                String jobTitleText = "";
                try {
                    jobTitleText = linkedinPage.jobTitle.getText().trim();
                    //System.out.println(jobTitleText);
                } catch (NoSuchElementException e) {
                    jobTitleText = "N/A";
                }
                String companyNameText = "";
                try {
                    companyNameText = linkedinPage.companyName.getText().trim();
                    //System.out.println(companyNameText);
                } catch (NoSuchElementException e) {
                    companyNameText = misMatchedCompanyList.get(i);
                }
                ReusableMethods.sleep(2);

                try {
                    String text = JSUtilities.getLocationWorkPlaceTypeLinkedin(Driver.getDriver(),
                            linkedinPage.startElement, linkedinPage.endElement);

                    if (text.isEmpty()){
                        String text2 = JSUtilities.getLocationWorkPlaceTypeLinkedin(Driver.getDriver(),
                                linkedinPage.startElement2, linkedinPage.endElement2);

                        String[] parts2 = text2.split("\\(", 3);
                        location = parts2[0].replaceAll("[^a-zA-Z,]", "").trim();
                        workPlaceType = parts2[1].replace(")", "").trim();

                    } else {

                        String[] parts = text.split("\\(", 3);
                        location = parts[0].replaceAll("[^a-zA-Z,]", "").trim();
                        workPlaceType = parts[1].replace(")", "").trim();

                    }
                } catch (Exception e) {
                    location = "N/A";
                    workPlaceType = "N/A";
                }

                String jobInsightText = "";
                try {
                    jobInsightText = linkedinPage.jobInsight.getText().trim();
                    //System.out.println(jobInsightText);
                } catch (NoSuchElementException e) {
                    jobInsightText = "N/A";
                }

                // send Job application info to Google Sheets
                SheetAndJava.putValue(jobTitleText, companyNameText, location, workPlaceType, jobInsightText);

                Driver.getDriver().navigate().back();
            }
        } catch (NullPointerException exception) {
            System.out.println("Everything is matching with the Google Sheets Document!");
        }

        Driver.closeDriver();
    }
}
