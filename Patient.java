import java.util.*;
import java.io.*;


class Patient {
  String patientName = "";
  int patientPriority = 0;

  public Patient(String patientName, int patientPriority) {
    this.patientName = patientName;
    this.patientPriority = patientPriority;
  }

  void UpdatePatientName(Patient patient, String newName) {
    patient.patientName = newName;
  }

  void UpdatePatientPriority(Patient patient, int newPriority) {
    patient.patientPriority = newPriority;
  }

}
