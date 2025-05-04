import org.junit.Before;
import org.junit.Test;

import Main.Main;
import Main.Main.AppointmentResult;
import Main.Main.Clinic;
import Main.Main.Doctor;
import Main.Main.Patient;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestBookAppointment {

    private Main.Clinic clinic;

    @Before
    public void setUp() {
        clinic = new Main.Clinic();
    }

    // Rule 1: Doctor not available → Appointment canceled
    @Test
    public void rule1_doctorNotAvailable_onRequestedDate_appointmentCanceled() {
        // Given: A doctor without any slots on the desired day
        Main.Doctor doctor = new Main.Doctor("Dr. Smith", "General");
        clinic.addDoctor(doctor);

        Main.Patient patient = new Main.Patient("Ali", Main.PatientType.NORMAL, Main.InsuranceStatus.APPROVED);
        LocalDate desiredDay = LocalDate.of(2023, 10, 11); // No slot added for this date

        // When: Booking appointment
        Main.AppointmentResult result = clinic.bookAppointment(patient, "General", desiredDay);

        // Then: Should be canceled
        assertEquals(Main.AppointmentResult.CANCELED, result);
    }

    // Rule 2: Emergency patient → Confirmed regardless of insurance
    @Test
    public void rule2_emergencyPatient_appointmentApprovedRegardlessOfInsurance() {
        // Given: Doctor with available slot
        Main.Doctor doctor = new Main.Doctor("Dr. Smith", "ER");
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 8, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 10, 9, 0);
        doctor.addAvailableSlot(start, end);
        clinic.addDoctor(doctor);

        // Emergency patient with rejected insurance
        Main.Patient emergencyPatient = new Main.Patient("John", Main.PatientType.EMERGENCY, Main.InsuranceStatus.REJECTED);
        LocalDate desiredDay = LocalDate.of(2023, 10, 10);

        // When: Book appointment
        Main.AppointmentResult result = clinic.bookAppointment(emergencyPatient, "ER", desiredDay);

        // Then: Should be approved
        assertEquals(Main.AppointmentResult.APPROVED, result);
    }

    // Rule 3: Regular patient with approved insurance → Confirmed
    @Test
    public void rule3_regularPatientWithApprovedInsurance_appointmentApproved() {
        // Given: Doctor with available slot
        Main.Doctor doctor = new Main.Doctor("Dr. Brown", "Dentist");
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 10, 11, 0);
        doctor.addAvailableSlot(start, end);
        clinic.addDoctor(doctor);

        // Regular patient with approved insurance
        Main.Patient regularPatient = new Main.Patient("Sara", Main.PatientType.NORMAL, Main.InsuranceStatus.APPROVED);
        LocalDate desiredDay = LocalDate.of(2023, 10, 10);

        // When: Book appointment
        Main.AppointmentResult result = clinic.bookAppointment(regularPatient, "Dentist", desiredDay);

        // Then: Should be approved
        assertEquals(Main.AppointmentResult.APPROVED, result);
    }

    // Rule 4: Rejected insurance → Appointment canceled
    @Test
    public void rule4_regularPatientWithRejectedInsurance_appointmentCanceled() {
        // Given: Doctor with available slot
        Main.Doctor doctor = new Main.Doctor("Dr. Green", "Neurology");
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 11, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 10, 12, 0);
        doctor.addAvailableSlot(start, end);
        clinic.addDoctor(doctor);

        // Regular patient with rejected insurance
        Main.Patient rejectedPatient = new Main.Patient("Mike", Main.PatientType.NORMAL, Main.InsuranceStatus.REJECTED);
        LocalDate desiredDay = LocalDate.of(2023, 10, 10);

        // When: Book appointment
        Main.AppointmentResult result = clinic.bookAppointment(rejectedPatient, "Neurology", desiredDay);

        // Then: Should be canceled
        assertEquals(Main.AppointmentResult.CANCELED, result);
    }
}