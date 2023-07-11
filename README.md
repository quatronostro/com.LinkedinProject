# com.LinkedinProject
This project is an automation solution for tracking my job applications on LinkedIn using Java, Gradle, Cucumber and Google Sheets API.

## Project purpose
I am currently looking for a new job and I want to keep track of all the applications I send on LinkedIn. I used to enter the details of each application manually on a Google Sheets document, but I found this process tedious and time-consuming. Therefore, I decided to create an automation program that can fill in the Google Sheets document for me based on the information I provide on LinkedIn.

## Requirements
To run this project on your computer, you need to have the following:

- Java
- Gradle
- Cucumber
- A Google account
- A Google Sheets document with the following headers: Job Title, Company Name, Location, Date Applied, Status, Link

## Project workflow
1. Webdriver goes to the Linkedin account and logs in by entering the account details
2. Clicks on the Jobs button and then My jobs button and opens the page of the job posts applied for
3. One by one, it looks at the job advertisements on all the pages, takes the names of the companies and makes a listü
4. In the meantime, it queries the jobs previously entered in excel with the Google Sheets API with the get method, extracts a list from it and compares these two lists
5. If there are new postings, it goes back one by one to the job boards and tries to find them, but if there are no new postings, it prints all the company names it gets from Linkedin and API
6. When it finds job post, it clicks on the post, get the necessary information and put it into the sheets document with API.
7. Repeats this process for all remaining job posts

## Notes
*I have to mention that, this is just a demo project. You can encounter with a lot of different types of error when you wanna run this.*
- *First of all to run this project just use feature file. Because I haven't use Gradle before and I think there is some mistake about it and I can't run this project with Runners class. So if you have a solution please tell me :)*
- *You can encounter with different Linkedin account enterence page, if so close the driver and run again the feature.*
- *Some company names can cause problems, if so you can add one if else in step definition to say the program skip this company name.*
- *As I mentioned before this is a demo project, so don't trust that much this program and always take your list to different sheet physically.*
- *I always try to run with ChromeDriver, so if you try to run with other browsers I don't know what you might encounter.*
- *I have tried to write detailed steps on how you can run the project below. If you have any problems with these steps, feel free to contact me. If you can't find way to do those steps, I helped from this videos maybe it helps you to:*
   - https://www.youtube.com/watch?v=3wC-SCdJK2c&t=701s
   - https://www.youtube.com/watch?v=8yJrQk9ShPg
   - https://www.youtube.com/watch?v=P16uA1Hl4DI&t=84s

## How to run the project
1. You can download the zip file from the Code button or you can clone the project link to your IDE.
2. You must create your own Google Sheets document and copy the headers from the image below. The program will write the data according to these headers, so make sure they match exactly. You can make changes to the code later if you want to use different headers.
![Ekran görüntüsü 2023-07-10 232229](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/59d80088-2ec9-41b3-8036-de84376a8ba6)
3. You must also enable the Google Sheets API for your Google account and create a project with OAuth consent. Follow these steps:
   - Go to this link and log in with your Google account. Then create a new project and give it any name you want:
   - [https://console.cloud.google.com/projectcreate](https://console.cloud.google.com/projectcreate)
   
   ![create-project](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/24a96f36-afd4-4426-8c22-99ad27f7951f)
   
   - Click "APIs & Services" button on the left menu.
   
   ![api-services](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/bc6e1aa4-651f-4a27-a711-e6654fb9a957)

   - Click "ENABLE APIS AND SERVICES" button.
   
   ![enableAPIs](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/65f6a0fb-ba4c-4d2b-806b-b2d69a6faf43)
   
   - Search for Google Sheets API or scroll down until you find it and click it.
   
   ![google sheets api](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/1cc53e7e-4701-4437-a24e-4fcf08e7df74)
   
   - Click "Enable" button.
     
   ![enable-project](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/d5dd3b0e-2ce4-4204-93a2-c09783e00594)
   
   - Click "Oauth consent screen" button on the left menu.
   
   ![oauth-consent-screen](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/a6579e08-cbb7-42de-87bf-3343d741ebf7)
   
   - Click "External" then "Create".
   
   ![external-createe](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/8c91101c-ce71-4d03-963e-d7eff64bf452)
   
   - On the App Information page, enter "App name", "User support email" and "Developer contact information". You can use any name you want and your Gmail address for both email fields.
     
   ![app-info](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/61558de0-9d04-4a2a-b0a7-3adfef05843f)
   
   ![developer-info](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/394aa9b9-9e02-4d10-a38e-43f579155348)
   
   - In Scopes tab, click "ADD OR REMOVE SCOPES" button.

   ![scopes](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/79e4d4d7-4005-4556-954a-86bf6ac0b78e)

   - On the right screen enter in filter box "Google Sheets API" then click all APIs like on the picture. After that click "Update" button.
  
   ![update-scopes](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/ad14abff-718b-429d-b12d-3cf2190c6f93)

   - Then click "SAVE AND CONTINUE" button on the buttom
   - In the Test users tab, click "+ ADD USERS" button than add yourself(I mean your gmail address again)
   
   ![test-users](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/ffd299a8-0ddb-4d5a-beb2-1f1216a3418a)

   - Then click "SAVE AND CONTINUE" button on the buttom
   - DONE!
  
4. Now you have to create credentials for your project.
   -  On same page click the "Credentials" button on the left menu.
   
   ![credentials-button](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/6fa5ce0b-34a2-4b83-b768-67c6929503d9)

   - Now first click "+ CREATE CREDENTIALS` on the header than choose "Oauth client ID".
   
   ![oauth-credential](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/702b8d13-65cd-42a3-b1fd-7b7852005f84)

   - Application type is going to be a simple "Desktop app" and again you can give a name whatever you want. Then click "Create" button. 
  
   ![create-client-ID](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/f63be3eb-9803-40bd-9f2c-1942c83c7c3a)

   - DONE! Now you can see the credentials. As for the last step Download Json file on your desktop. You will see credential.json file on your desktop. (I didn't add this file on this repo because every credential file is unique like API key.)

5. Open this project codes on your computer than drag and drop the credential.json file on the desktop into the resources folder inside the project.
6. Open [configuration.properties](https://github.com/quatronostro/com.LinkedinProject/blob/master/configuration.properties) file and enter your informations.

   - You can find your Google Sheet ID in the link of your sheet. After the d/
  
   ![sheet-id](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/f6420013-7cf5-42e6-9f2e-d57f2bb54558)

   - Also you can find your sheet name at the buttom of the page
  
   ![sheet-name](https://github.com/quatronostro/com.LinkedinProject/assets/93223660/0183985a-5f5e-4c8a-9c16-62e0be2676d1)

   - To give a range you have to use A1 notation, you can find more information about [A1 notation in here](https://developers.google.com/sheets/api/guides/concepts#cell)
     - Example: Sheet1!A1:B2
    
   
    
**YOU ARE READY TO GO!!!**









  





















