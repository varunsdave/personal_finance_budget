# Personal Finance Budget and Expense Tracking

Server Badges: 
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

**Front end is only here to provide an example project to use the backend.**

## Requirements

* Install a mongodb or ensure a mongodb is available.
* Ensure java 12 is installed on your computer
* Node package manager. NPM [https://www.npmjs.com/get-npm]
* Update application.properties with mongodb uri

## Usage

Note, this application requires some setup. The simplest instructions for backend and front end are specified first. 

### Simple backend startup

1. Clone this repository and navigate to the directory.
2. Ensure mongodb server is running
3. Open a terminal or cmd and navigate to the the repository directory. 
4. Run one  the following command

If you are running on default mongodb port locally
```
java -jar budget-{version}.jar
```

If mongodb is running elsewhere. (Note, the authenticated credentials aren't supported atm)
```
java -jar budget-{version}.jar --spring.data.mongodb.host={yourHost} --spring.data.mongodb.port={yourPort}
```

    ** {version} - Version of the budget app. 
    ** (yourHost) - mongodb server host
    ** {yourPort} - mongodb server port

The server should now be started. You can visit [http://localhost:8080/swagger-ui.html] to view the available APIS

### start front end
1. navigate to frontEndClient
2. run install && npm start
3. Navigate to localhost:4200

If this has helped you, you can support this project here.

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2SJP2FPZN7VXA)
