Feature: Open Linkedin and collect information from applied jobs if there is a new job information add this Google Sheets

  Scenario: Linkedin and Google Sheets comparing company names and if there is mismatched company name take this info and put Google Sheets
    Given Webdriver goes to linkedin
    Then Enters valid account info and cliks Sign In button
    And Clicks Jobs button on header
    And Clicks My Jobs button then clicks Applied Jobs button
    And Takes company names
    And Verify that all company names are matching with sheet
    And Add mismatched data to sheet