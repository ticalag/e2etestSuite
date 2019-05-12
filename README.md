# Twitter Front-end & Back-end automation tests framework

### Please take into consideration that this project is a personal project which is still in progress and will have multiple features added with time. I'm working on this framework in my spare time. The purpose of this project is to show my knowledge in automation area, to explore and play with new features, ideas, and also improve my skills.

As mentioned above, this project is still a work in progress, there is plenty of room to improve thus I've added TODO's where I thought that I need to review when I have some free time.

Bare in mind that if you run the API tests too many times (over 15 times in 1 minute), it will throw a 403 status code, because there is a limit for the number of times an account can make requests.


To run this project you need to create a JUnit configuration and add the following WM options: 

-Dtest=CucumberRunner -Dcucumber.options="--tags @loginPage" where @loginPage represents a cucumber tag/testcase 

For example if you want to run all the tests, simply add the @smoke-tests
this will run both the front-end and back-end tests.

