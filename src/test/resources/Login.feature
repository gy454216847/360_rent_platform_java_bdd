Feature: Normal Login

  @NormalLogin
  Scenario Outline: Login with right username and password
    Given I am on the page
    When I input right "<username>" and right "<password>"
    And I click login button
    Then I can login the platform
    Examples:
      | username | password |
      | admin    | password |


  @WrongLogin
  Scenario Outline: Login with right username and password
    Given I am on the page
    When I input wrong "<username>" and wrong "<password>"
    And I click login button
    Then I can not login the platform
    Examples:
      | username | password |
      | admin    | 123456   |


  @Logout
  Scenario Outline: Logout
    Given I am on the page
    When I input right "<username>" and right "<password>"
    And I click login button
    And I click logout button
    Then I can logout the platform
    Examples:
      | username | password |
      | admin    | password |




