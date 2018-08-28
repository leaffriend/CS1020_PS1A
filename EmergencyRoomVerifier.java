// Copy paste this Java Verifier and save it as "EmergencyRoomVerifier.java"
// Usage: "java EmergencyRoomVerifier < yourproposedtestcasefilename" and see if this verifier reports anything
import java.util.*;

class EmergencyRoomVerifier {
  private static void checkName(String name) {
    if (name.length() > 15)
      System.out.println("ERROR, name " + name + " is longer than 15 characters");
    for (int i = 0; i < name.length(); i++)
      if (!Character.isUpperCase(name.charAt(i))) {
        System.out.println("ERROR, name " + name + " contains non-uppercase character");
        break;
      }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    TreeMap < String, Integer > patient = new TreeMap < String, Integer >();
    String name;

    int numCMD = sc.nextInt();
    if (numCMD > 1000000) {
      System.out.println("ERROR, too many commands...");
      return;
    }

    while (numCMD-- > 0) {
      int cmd = sc.nextInt();
      switch (cmd) {
        case 0: // ArriveAtHospital
          name = sc.next();
          checkName(name);
          if (patient.containsKey(name)) {
            System.out.println("ERROR, duplicate patient name found..." + name);
            return;
          }
          int emergencyLvl = sc.nextInt();
          if (emergencyLvl < 30 || emergencyLvl > 100) {
            System.out.println("ERROR, emergency level is not in [30..100]..., it is = " + emergencyLvl);
            return;
          }
          patient.put(name, emergencyLvl);
          if (patient.size() > 200000) {
            System.out.println("ERROR, this test case contains too many patient..., it is = " + patient.size());
            return;
          }
          break;
        case 1: // UpdateEmergencyLvl
          name = sc.next();
          checkName(name);
          if (!patient.containsKey(name)) {
            System.out.println("ERROR, this patient " + name + " has not arrived at the hospital yet...");
            return;
          }
          int increaseEmergencyLvl = sc.nextInt();
          if (increaseEmergencyLvl < 0 || increaseEmergencyLvl > 70) {
            System.out.println("ERROR, increaseEmergencyLvl is not in [0..70]..., it is = " + increaseEmergencyLvl);
            return;
          }
          if (patient.get(name)+increaseEmergencyLvl > 100) {
            System.out.println("ERROR, add increaseEmergencyLvl = " + increaseEmergencyLvl + " will cause " + name + " to have emergency level greater than 100, that is impossible");
            return;
          }
          patient.put(name, patient.get(name)+increaseEmergencyLvl);
          break;
        case 2: // Treat
          name = sc.next();
          checkName(name);
          if (!patient.containsKey(name)) {
            System.out.println("ERROR, this patient " + name + " has not arrived at the hospital yet...");
            return;
          }
          break;
        case 3: // Query
          break;
      }
    }

    System.out.println("Test data is valid :)");
  }
}
