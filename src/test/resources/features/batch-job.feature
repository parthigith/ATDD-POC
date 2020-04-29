Feature: Process vendor files

  Scenario: Job must be started when required files are exist in source location
    Given that required vendor files are exist at the source location
    When batch job gets invoked
    Then job must be started

  Scenario: Files must be backed up and downloaded to the clean folder for processing
    Given that required vendor files are exist at the source location
    When batch job gets invoked
    Then all files must be copied to the backup location
    And all files must get downloaded to the clean folder

  Scenario: Image files are uploaded to server
    Given that required vendor files are exist at the source location
    When batch job gets invoked
    And api is invoked to get the response
    Then api status code must be 200
    And verify api response

  Scenario: Metadata must be updated in database
    Given that required vendor files are exist at the source location
    When batch job gets invoked
    Then verify metadata were updated in database