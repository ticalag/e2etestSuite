# Twitter Front-end & Back-end automation tests framework

Bear in mind that if you run the API tests too many times (over 15 times in 1 minute), it will throw a 403 status code,
because there is a limit for the number of times an account can make requests.

To run this project you need to create a TestNG configuration and add the following WM options:

**-DBrowser="chrome" -Dcucumber.options="--tags @loginPage"**
Here **@loginPage** represents a cucumber tag/testcase, if you add the tags option in the configuration, it will override
the tag from the CucumberRunner.

You can also run the tests using the **mvn clean test** command 

If you want to run a specific scenario using command line here is an example how to do so:

**mvn clean test -Dcucumber.options="--tags @loginPage"** this will run the loginPage scenario only


The Ui tests can be run in the following browsers: **chrome, firefox, edge and opera.**

If you want to run all the tests, simply add the **@regression** this will run both the front-end and back-end tests.

