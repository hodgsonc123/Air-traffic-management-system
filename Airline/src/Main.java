

/**
 * The Main class.
 *
 * The principal component is the usual main method required by Java application to launch the application.
 *
 * Instantiates the databases.
 * Instantiates and shows all the system interfaces as Frames.
 * @stereotype control
 */
public class Main {


/**
 * Launch SAAMS.
 */

public static void main(String[] args) {
    // Instantiate databases
    // Instantiate and show all interfaces as Frames
	AircraftManagementDatabase amd = new AircraftManagementDatabase();
	GateInfoDatabase gid = new GateInfoDatabase();
	
	RadarTransceiver t = new RadarTransceiver(amd);
	

	
    CleaningSupervisor cs = new CleaningSupervisor(amd);

    RefuellingSupervisor rs = new RefuellingSupervisor(amd);
	MaintenanceInspector mi = new MaintenanceInspector(amd);

	GateConsole g1 = new GateConsole(gid,amd,0);
	GateConsole g2 = new GateConsole(gid,amd,1);
	GateConsole g3 = new GateConsole(gid,amd,2);
	
	LATC latc = new LATC(amd);
	
    GOC goc = new GOC(amd, gid);
	
	PublicInfo pinfo = new PublicInfo(amd);
  }

}