README.md
Todo Challenge - Daisy and Myrto

We've been working to automate our ToDoMVC Test Plan, using Selenium and Java to set up the series of tests identified during our planning.

Reminder of initial scope:
We set out to test the most popular frameworks identified - React, Angular, Vue.js, Ember.js, Backbone.js,running tests in the two most common browsers: Chrome and Safari.

We committed to testing all of the functional requirements below:

1. New todo items can be added
2. Todo items can always be modified
3. Todo items can always be individually deleted
4. A todo item can be marked completed or incomplete
5. All todo items can be marked as completed, whether they have been completed or not
6. If all todo items are marked as completed, they can all be marked as incomplete in one go
7. The list can be filtered on todo items' completion states
8. Complete todo items can be cleared from the list, when >0 completed todo items are listed
9. Status bar always displays a count of remaining todo items left to do
10. Todo items can be reordered

Out of scope:
We identified the following as being out of scope:

1. We're not testing in older web browser versions. We expect this to be sufficient as the audience is primarily developers, who are likely to have the latest browser version.
2. We're not testing on mobile browsers for the same reasons as above.
3. The application is designed for developers to compare javascript frameworks, and not to be a fully functioning to do list tool. Therefore testing will focus on core functionality, rather than exploring every edge case (for example, emojis and accented/foreign language characters will not be tested).

Future tests:
Multiple frameworks: Our initial scope included testing the five most popular frameworks: React, Angular, Vue.js, Ember.js, Backbone.js. We focused on React for our initial set up, and were looking to expand to the other frameworks once the test structure was finalised, however we have not yet implemented this. With more time we would look to add in the additional frameworks without duplicating the code, ideally by using parameterised tests. However we also expect there to be framework-specific bugs, so we would want to ensure that any failing tests are easy to trace back to the framework and browser being tested. As a result we expect this to require quite a bit more planning to carry out.

Multiple browsers (Safari): We initially planned to test in Chrome and Safari, however we came across some issues with browser configuration of Safari that required further work. In order to increase browser coverage we adjusted our scope to test Chrome and Firefox, and would look to overcome the issue with Safari with further investigation, eventually looking to test all three browsers as these are the most common.
Test coverage tracking: With more time we would like to add some test coverage reporting into our code, to understand how extensive our automation is and locate any significant blind spots.

Validating local data storage: We discovered a bug in the way different frameworks stored the data - they varied quite widely and few contained all the fields outlined in the initial test plan (title, priority, completed, id). As a result, some frameworks remembered list items between uses, others partly remembered, and some retained no data locally at all. We would like to test this further through additional automated testing, to ensure that all frameworks store the correct data, in the correct format outlined in the test plan. Testing data stored locally via Selenium is a new topic which would require some exploratory reading before we create the tests.

Data-driven testing: We implemented data-driven testing for our shouldCheckValues method, and with further time would like to use it for the values in our shouldFailNeedsFix method as well. As we expand our test cases, it's likely that further opportunities for this approach would come up.

List reordering: Despite being identified as in scope, we noticed a bug early on that meant list items could not be selected, or dragged to reorder. Since the code needs to be edited (or even written) in order for this feature to work, we've not added a test for it yet. We would look to add a test once the functionality was created. We also still need to confirm whether this feature works on any of the other frameworks.
