# Personal Finance Budget and Expense Tracking Backned

This is an app that can be deployed to your local machine with a connected data source 
to track your expense and income. The app will come with a front end view that will provide
simple reporting. 

The goal is to create an application that can be used by people who do not want their
transactions to be used by third parties. 

## Requirements

* Install a mongod
* Update application.properties with mongodb uri

## Usage
1. Provide Db connection settings as per readme
2. Start the spring boot application using IDE. 
3. Visit localhost:8080/swagger-ui.html for the endpoints supported