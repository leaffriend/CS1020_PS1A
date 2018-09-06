// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;


// write your matric number here: A0139860X
// write your name here: Yip Bing You
// write list of collaborators here:
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoomBackup {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  static int entryCounter = 0;

  class Patient {
    String patientName = "";
    int patientPriority = 0;
    int patientEntry = 0;


    public Patient(String patientName, int patientPriority) {
      this.patientName = patientName;
      this.patientPriority = patientPriority;
      entryCounter++;
      this.patientEntry = entryCounter;

    }

    public Patient(String patientName, int patientPriority, int patientEntry) {
      this.patientName = patientName;
      this.patientPriority = patientPriority;
      this.patientEntry = patientEntry;

    }

    void updatePatientName(String newName) {
      this.patientName = newName;
    }

    void updatePatientPriority(int newPriority) {
      this.patientPriority = newPriority;
    }

    String getName() {
      return patientName;
    }

    int getPriority() {
      return patientPriority;
    }

    int getEntry() {
      return patientEntry;
    }

    @Override
    public String toString() {
      return this.getName() + "/" + this.getPriority() + "/" + this.getEntry();
    }

    @Override
    public boolean equals(Object pat) {
      if (pat == null){
        return false;
      }
      final Patient other = (Patient) pat;
      if (this.getName() != other.getName()) {
        return false;
      }
      if (this.getPriority() != other.getPriority()) {
        return false;
      }
      if (this.getEntry() != other.getEntry()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      return getName().hashCode() + 31 * getPriority() + 37 * getEntry();
    }

  }

  class PatientComparator implements Comparator<Patient> {
    @Override
    public int compare(Patient x, Patient y) {
      // PriorityQueue is ordinal, negating the result to turn it cardinal
      if (x.getPriority() != y.getPriority())
        return y.getPriority() - x.getPriority();
      return x.getEntry() - y.getEntry();
    }
  }

  PatientComparator patientComparator;
  PriorityQueue patientQueue;
  Hashtable patientHashtable;

  public EmergencyRoomBackup() {
    // Write necessary code during construction
    //
    // write your answer here

    // Declare comparator for Patient class
    patientComparator = new PatientComparator();
    // Declare PriorityQueue (Binary Heap) for the Queue
    patientQueue = new PriorityQueue<Patient>(patientComparator);
    // Declare hashtable to put in patients
    // Key: patientName
    // Value: Patient object
    patientHashtable = new Hashtable();
  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    Patient newPatient = new Patient(patientName, emergencyLvl);
    patientQueue.add(newPatient);
    patientHashtable.put(patientName, newPatient);
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here

    Patient oldPatient = (Patient) patientHashtable.get(patientName);
    if (patientQueue.contains(oldPatient)) {
      patientQueue.remove(oldPatient);
      Patient updPatient = new Patient(patientName, oldPatient.getPriority() + incEmergencyLvl, oldPatient.getEntry());
      patientQueue.add(updPatient);
      patientHashtable.remove(patientName);
      patientHashtable.put(patientName, updPatient);
    }
  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    /*
    // Treat patient with highest priority
    Patient toTreatPat = (Patient) patientQueue.poll();
    patientHashtable.remove(toTreatPat.getName());
    */
    // Treat named patient
    Patient toTreatPat = (Patient) patientHashtable.get(patientName);
    if (patientQueue.contains(toTreatPat)) {
      patientQueue.remove(toTreatPat);
      patientHashtable.remove(toTreatPat);
    }
  }

  String Query() {
    String ans = "The emergency suite is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency suite is empty"
    //
    // write your answer here
    System.out.println(patientQueue.toString());
    Patient toTreatPat = (Patient) patientQueue.peek();
    if (toTreatPat != null) {
      ans = toTreatPat.getName();
    }
    return ans;
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoomBackup ps1 = new EmergencyRoomBackup();
    ps1.run();
  }
}
