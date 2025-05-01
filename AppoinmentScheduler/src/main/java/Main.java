import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public enum PatientType {NORMAL, EMERGENCY}

    public enum InsuranceStatus {APPROVED, REJECTED}

    public enum AppointmentResult {APPROVED, CANCELED}

    public static class Patient {
        String name;
        PatientType type;
        InsuranceStatus insuranceStatus;

        public Patient(String name, PatientType type, InsuranceStatus insuranceStatus) {
            this.name = name;
            this.type = type;
            this.insuranceStatus = insuranceStatus;
        }
    }

    public static class AvailableSlot {
        LocalDateTime start;
        LocalDateTime end;
        Patient reservedBy = null;

        public AvailableSlot(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }

        public void reserve(Patient patient) {
            this.reservedBy = patient;
        }

        public boolean isReserved() {
            return reservedBy != null;
        }
    }

    public static class Doctor {
        String name;
        String department;
        List<AvailableSlot> availableSlots = new ArrayList<>();

        public Doctor(String name, String department) {
            this.name = name;
            this.department = department;
        }

        public void addAvailableSlot(LocalDateTime start, LocalDateTime end) {
            availableSlots.add(new AvailableSlot(start, end));
        }
    }

    public static class Clinic {
        List<Doctor> doctors = new ArrayList<>();

        public void addDoctor(Doctor doctor) {
            doctors.add(doctor);
        }

        public AppointmentResult bookAppointment(Patient patient, String department, LocalDate desiredDay) {
            return AppointmentResult.APPROVED;
        }
    }
}
