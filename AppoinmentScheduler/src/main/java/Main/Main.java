package Main; 

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        public static void main(String[] args) {
            Clinic clinic = new Clinic();
            Doctor doctor = new Doctor("دکتر رضوی", "قلب");
            doctor.addAvailableSlot(LocalDateTime.of(2023, 10, 10, 8, 0), LocalDateTime.of(2023, 10, 10, 12, 0));
            clinic.addDoctor(doctor);
            Patient patient = new Patient("علی محمدی", PatientType.NORMAL, InsuranceStatus.APPROVED);
            LocalDate date = LocalDate.of(2023, 10, 10);
            AppointmentResult result = clinic.bookAppointment(patient, "قلب", date);
            System.out.println(result.name());
        }

        public void addDoctor(Doctor doctor) {
            doctors.add(doctor);
        }

        public AppointmentResult bookAppointment(Patient patient, String department, LocalDate desiredDay) {
            for (Doctor doctor : doctors) {
                if (!doctor.department.equals(department)) continue;

                Optional<AvailableSlot> availableSlot = doctor.availableSlots.stream()
                        .filter(slot -> !slot.isReserved()
                                && slot.start.toLocalDate().equals(desiredDay))
                        .findFirst();

                if (availableSlot.isPresent()) {
                    AvailableSlot slot = availableSlot.get();

                    if (patient.type == PatientType.EMERGENCY) {
                        slot.reserve(patient);
                        System.out.println("نوبت اورژانسی برای " + patient.name + " با " + doctor.name + " در ساعت " + slot.start.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")) + " ثبت شد.");
                        return AppointmentResult.APPROVED;
                    }

                    if (patient.type == PatientType.NORMAL && patient.insuranceStatus == InsuranceStatus.APPROVED) {
                        slot.reserve(patient);
                        System.out.println("نوبت برای " + patient.name + " با " + doctor.name + " در ساعت " + slot.start.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")) + " ثبت شد.");
                        return AppointmentResult.APPROVED;
                    }
                    return AppointmentResult.CANCELED;
                }
            }
            return AppointmentResult.CANCELED;
        }
    }
}
