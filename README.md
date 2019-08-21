# Personal Finance Budget and Expense Tracking

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=varunsdave_personal_finance_budget&metric=bugs)](https://sonarcloud.io/dashboard?id=varunsdave_personal_finance_budget)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=varunsdave_personal_finance_budget&metric=code_smells)](https://sonarcloud.io/dashboard?id=varunsdave_personal_finance_budget)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=varunsdave_personal_finance_budget&metric=coverage)](https://sonarcloud.io/dashboard?id=varunsdave_personal_finance_budget)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=varunsdave_personal_finance_budget&metric=alert_status)](https://sonarcloud.io/dashboard?id=varunsdave_personal_finance_budget)


This is an app that can be deployed to your local machine with a connected data source 
to track your expense and income. The app will come with a front end view that will provide
simple reporting. 

The goal is to create an application that can be used by people who do not want their
transactions to be used by third parties. 

The backend serves as a standalone spring boot application that perseves basic information about
transactions. 

The front end facilitates upload and visualisations. 

Ideally, the front end and backend can be hosted separately. Just requires some config changes. 


## Requirements

* Install a mongodb
* Update application.properties with mongodb uri

## Usage
1. Provide Db connection settings as per readme
2. Start the spring boot application using IDE. Alternatively, you can build using gradle and run the server.  
3. Visit localhost:8080/swagger-ui.html for the endpoints supported

### start front end
Ensure you have npm installed. [https://www.npmjs.com/get-npm]
1. navigate to frontEndClient
2. run install && npm start
3. Navigate to localhost:4200
