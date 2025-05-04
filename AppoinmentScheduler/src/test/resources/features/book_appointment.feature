Feature: Booking Appointments

  Scenario: Doctor not available on the requested date
    Given a clinic with a doctor in "Cardiology" available on "2023-10-10"
    And a patient named "John Doe" of type "NORMAL" with "APPROVED" insurance
    When the patient tries to book an appointment with a "Cardiology" doctor on "2023-10-11"
    Then the appointment should be "CANCELED"

  Scenario: Emergency patient booking
    Given a clinic with a doctor in "Neurology" available on "2023-10-12"
    And an emergency patient named "Jane Smith" with "REJECTED" insurance
    When the patient tries to book an appointment with a "Neurology" doctor on "2023-10-12"
    Then the appointment should be "APPROVED"

  Scenario: Regular patient with approved insurance booking
    Given a clinic with a doctor in "Pediatrics" available on "2023-10-13"
    And a regular patient named "Peter Jones" with "APPROVED" insurance
    When the patient tries to book an appointment with a "Pediatrics" doctor on "2023-10-13"
    Then the appointment should be "APPROVED"

  Scenario: Regular patient with rejected insurance booking
    Given a clinic with a doctor in "Dermatology" available on "2023-10-14"
    And a regular patient named "Alice Brown" with "REJECTED" insurance
    When the patient tries to book an appointment with a "Dermatology" doctor on "2023-10-14"
    Then the appointment should be "CANCELED"