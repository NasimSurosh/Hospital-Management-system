package hospitalSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Patient {
  private int patientID;
  private static int nextPatient = 1;
  private String patientName;
  private int patientAge;
  private String patientgender;
  private String patientContact;

  public Patient(String patientName, int patientAge, String patientgender, String patientContact) {

    this.patientID = nextPatient;
    this.patientName = patientName;
    this.patientAge = patientAge;
    this.patientgender = patientgender;
    this.patientContact = patientContact;
  }

  public int getPatientID() {
    return patientID;
  }

  public static int getNextPatient() {
    return nextPatient;
  }

  public String getPatientName() {
    return patientName;
  }

  public int getPatientAge() {
    return patientAge;
  }

  public String getPatientgender() {
    return patientgender;
  }

  public String getPatientContact() {
    return patientContact;
  }

}

class Doctor {
  private int doctorID;
  private static int nextDoctor = 1;
  private String doctorName;
  private List<Appointment> appointments;

  public Doctor(String doctorName) {
    this.doctorName = doctorName;
    this.doctorID = nextDoctor;
    this.appointments = new ArrayList<>();
  }

  public int getDoctorID() {
    return doctorID;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public List<Appointment> getAppoinments() {
    return appointments;
  }

  public void addDoctorAppointments(Appointment appointment) {
    appointments.add(appointment);
  }

}

class Appointment {
  private Patient patient;
  private Doctor doctor;
  private Date dateTime;

  public Appointment(Doctor doctor, Patient patient, Date dateTime) {

    this.patient = patient;
    this.doctor = doctor;
    this.dateTime = dateTime;
  }

  public Patient getPatient() {
    return patient;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public Date getDateTime() {
    return dateTime;
  }

}

class Hospital {
  private List<Patient> patients;
  private List<Doctor> doctors;
  private List<Appointment> appointments;

  public Hospital() {
    this.patients = new ArrayList<>();
    this.doctors = new ArrayList<>();
    this.appointments = new ArrayList<>();
  }

  public void patientRegestration(String name, int age, String contact, String gender) {
    Patient addPatient = new Patient(name, age, gender, contact);
    patients.add(addPatient);
  }

  public void displayRegestredPatient() {
    for (Patient patient : patients) {
      System.out.println("Patient ID: " + patient.getPatientID() + " patient name: " + patient.getPatientName()
          + " patient age: " + patient.getPatientAge() + " patient gender: " + patient.getPatientgender()
          + " patient contact: " + patient.getPatientContact());
    }
  }

  public void doctorsInHospital() {
    for (Doctor doctor : doctors) {
      System.out.println("Doctor ID: " + doctor.getDoctorID() + " docotor name: " + doctor.getDoctorName());
    }
  }

  public Doctor availableDoctor(int doctorID) {
    for (Doctor doctor : doctors) {
      if (doctor.getDoctorID() == doctorID) {
        return doctor;
      }
    }
    return null;
  }

  public Patient availablePatient(int patientID) {
    for (Patient patient : patients) {
      if (patient.getPatientID() == patientID) {
        return patient;
      }
    }
    return null;
  }

  public void schedualedAppointments(int doctorID, int patientID, Date time) {
    Doctor doctor = availableDoctor(doctorID);
    Patient patient = availablePatient(patientID);

    if (patient != null && doctor != null) {
      Appointment appointment = new Appointment(doctor, patient, time);
      appointments.add(appointment);
      doctor.addDoctorAppointments(appointment);
      System.out.println("Appointment is schedualed.");
    } else {
      System.out.println("Appointment dosent accepted.");
    }
  }

  public void display() {
    for (Appointment appointment : appointments) {
      System.out.println("Doctor: " + appointment.getDoctor().getDoctorName() + " patient: "
          + appointment.getPatient().getPatientName() + " time: " + appointment.getDateTime());
    }
  }
}

public class HospitalManagementSystem {

  public static void main(String[] args) {

    Hospital hospital = new Hospital();
    Scanner scan = new Scanner(System.in);

    while (true) {
      System.out.println("1. To regester patient");
      System.out.println("2. Scheduale ppointments");
      System.out.println("3. Patients");
      System.out.println("4. Doctors");
      System.out.println("5. Appointments");
      System.out.println("6. To exit");
      System.out.println("Enter your choice");
      int choice = scan.nextInt();

      switch (choice) {
      case 1:
        System.out.println("Enter patient name: ");
        String name = scan.nextLine();
        System.out.println("Enter patient age: ");
        int age = scan.nextInt();
        System.out.println("Enter patient gender: ");
        String gender = scan.nextLine();
        System.out.println("Enter patient contact: ");
        String contact = scan.nextLine();
        hospital.patientRegestration(name, age, contact, gender);
        System.out.println("Patient registered.");
        break;
      case 2:
        System.out.println("Enter patient ID:");
        int patientID = scan.nextInt();
        System.out.println("Enter doctor ID:");
        int doctorID = scan.nextInt();
        System.out.println("Enter appointment time (yyyy-MM-dd HH:mm)");
        String StringTime = scan.next() + " " + scan.next();
        try {
          Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(StringTime);
          hospital.schedualedAppointments(doctorID, patientID, time);
        } catch (ParseException e) {
          System.out.println("Invalid date time format");
        }
        break;
      case 3:
        hospital.displayRegestredPatient();
        break;
      case 4:
        hospital.doctorsInHospital();
        break;
      case 5:
        hospital.display();
        break;
      case 6:
        System.out.println("Exiting from system!");
        System.exit(0);
      default:
        System.out.println("Invalid choice.");

      }
    }

  }

}
