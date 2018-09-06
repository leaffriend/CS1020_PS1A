// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;


// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  BinaryHeap patientHeap;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    patientHeap = new BinaryHeap();

  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    Patient pat = new Patient(patientName, emergencyLvl);
    patientHeap.insert(pat);

  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
    patientHeap.increasePriority(patientName, incEmergencyLvl);

  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    if (patientHeap.peekMax().getName().equals(patientName)){
      patientHeap.extractMax();
    }
    else {
      patientHeap.maxLvl(patientName);
      patientHeap.extractMax();
    }


  }

  String Query() {
    String ans = "The emergency suite is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency suite is empty"
    //
    // write your answer here
    //System.out.println(patientHeap.toString());
    if (patientHeap.isEmpty()) {
      return ans;
    }
    else {
      return patientHeap.peekMax().getName();
    }
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
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}

class Patient {
  String patientName = "";
  int patientPriority = 0;
  int patientEntry = 0;
  static int entryCounter = 0;

  public Patient(String patientName, int patientPriority, int patientEntry) {
    this.patientName = patientName;
    this.patientPriority = patientPriority;
    this.patientEntry = patientEntry;
  }

  public Patient(String patientName, int patientPriority) {
    this(patientName,patientPriority,++entryCounter);
  }

  public Patient() {
    this("", -1);
  }

  void updatePatientName(String newName) {
    this.patientName = newName;
  }

  void updatePatientPriority(int newPriority) {
    this.patientPriority = newPriority;
  }

  void updatePatientEntry(int newEntry) {
    this.patientEntry = newEntry;
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

  public int compareTo(Patient other) {
    if ((this.getPriority() > other.getPriority()) || (this.getPriority() == other.getPriority() && this.getEntry() < other.getEntry()))
      return 1;
    return -1;
  }

}

class BinaryHeap {
  ArrayList<Patient> heapPatient;
  HashMap<String,Patient> mapPatient;
  HashMap<String,Integer> mapPatientIndex;
  int heapSize;

  public BinaryHeap() {
    this.heapPatient = new ArrayList<>();
    this.mapPatient = new HashMap<>();
    this.mapPatientIndex = new HashMap<>();
    this.heapSize = 0;

    this.heapPatient.add(new Patient());
  }

  void insert(Patient pat) {
    heapPatient.add(pat);
    mapPatient.put(pat.getName(),pat);
    heapSize++;
    mapPatientIndex.put(pat.getName(),heapSize);
    shiftUp(heapSize);
  }

  Patient extractMax() {
    Patient max = heapPatient.get(1);
    Patient last = heapPatient.get(heapSize);

    heapPatient.set(1, last);
    heapPatient.remove(heapSize--);

    mapPatient.remove(max.getName());
    mapPatientIndex.remove(max.getName());
    mapPatientIndex.replace(last.getName(), 1);
    shiftDown(1);
    return max;
  }

  Patient peekMax() {
    return heapPatient.get(1);
  }

  boolean isEmpty() {
    return heapSize == 0;
  }

  void maxLvl(String patientName) {
    Patient pat = mapPatient.get(patientName);
    int pos = mapPatientIndex.get(patientName);
    pat.updatePatientPriority(100);
    shiftUp(pos);
  }

  void increasePriority(String patientName, int increase) {
    Patient pat = mapPatient.get(patientName);
    int pos = mapPatientIndex.get(patientName);
    pat.updatePatientPriority(pat.getPriority() + increase);
    shiftUp(pos);
  }

  void shiftUp(int i) {
    Patient parent = heapPatient.get(parent(i));
    Patient child = heapPatient.get(i);

    while (i > 1 && parent.compareTo(child) == -1) {
      swap(parent(i), i);
      i = parent(i);
      parent = heapPatient.get(parent(i));
    }
  }

  void shiftDown(int i) {
    while(i <= heapSize) {
      int maxI = i;
      Patient max = heapPatient.get(i);

      if (left(i) != -1 && max.compareTo(heapPatient.get(left(i))) == -1) {
        maxI = left(i);
        max = heapPatient.get(left(i));
      }
      if (right(i) != -1 && max.compareTo(heapPatient.get(right(i))) == -1) {
        maxI = right(i);
        max = heapPatient.get(right(i));
      }
      if (maxI != i) {
        swap(maxI, i);
        i = maxI;
      }
      else break;
    }
  }

  void swap(int a, int b) {
    Patient patA = heapPatient.get(a);
    Patient patB = heapPatient.get(b);
    heapPatient.set(a, patB);
    heapPatient.set(b, patA);
    mapPatientIndex.replace(patA.getName(), b);
    mapPatientIndex.replace(patB.getName(), a);
  }

  int left(int i) {
    return 2*i > heapSize ? -1 : 2*i;
  }

  int right(int i) {
    return 2*i + 1 > heapSize ? -1 : 2*i + 1;
  }

  int parent(int i) {
    return i == 1 ? 1 : i/2;
  }

  public String toString() {
    return heapPatient.toString();
  }
}
