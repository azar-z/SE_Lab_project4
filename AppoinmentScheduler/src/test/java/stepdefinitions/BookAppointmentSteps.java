package stepdefinitions;

import Main.*;
import Main.Main.AppointmentResult;
import Main.Main.Clinic;
import Main.Main.Doctor;
import Main.Main.InsuranceStatus;
import Main.Main.Patient;
import Main.Main.PatientType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class BookAppointmentSteps {

    private Main.Clinic clinic;
    private Doctor doctor;
    private Patient patient;
    private AppointmentResult result;
    private LocalDate desiredDate;

    @Given("a clinic with a doctor in {string} available on {string}")
    public void a_clinic_with_a_doctor_in_available_on(String department, String dateString) {
        clinic = new Clinic();
        Doctor doctor = new Doctor("Test Doctor", department);
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        doctor.addAvailableSlot(
            LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 8, 0),
            LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 12, 0)
        );
        clinic.addDoctor(doctor);
    }

    @Given("an emergency patient named {string} with {string} insurance")
    public void an_emergency_patient_named_with_insurance(String name, String insurance) {
        patient = new Patient(name, PatientType.EMERGENCY, InsuranceStatus.valueOf(insurance));
    }

    @Given("a regular patient named {string} with {string} insurance")
    public void a_regular_patient_named_with_insurance(String name, String insurance) {
        patient = new Patient(name, PatientType.NORMAL, InsuranceStatus.valueOf(insurance));
    }

    @Given("a patient named {string} of type {string} with {string} insurance")
    public void a_patient_named_of_type_with_insurance(String name, String type, String insurance) {
        patient = new Patient(name, PatientType.valueOf(type), InsuranceStatus.valueOf(insurance));
    }

    @When("the patient tries to book an appointment with a {string} doctor on {string}")
    public void the_patient_tries_to_book_an_appointment_with_a_doctor_on(String department, String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        result = clinic.bookAppointment(patient, department, date);
    }

    @Then("the appointment should be {string}")
    public void the_appointment_should_be(String expectedResult) {
        assertEquals(AppointmentResult.valueOf(expectedResult), result);
    }

    public BookAppointmentSteps() {
        System.out.println("BookAppointmentSteps constructor called"); // Add this for debugging
    }
}