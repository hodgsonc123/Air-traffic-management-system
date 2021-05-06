import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Generated by Together
// Edited by Nganga Njiraini

/**
 * An interface to SAAMS:
 * Maintenance Inspector Screen:
 * Inputs events from the Maintenance Inspector, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:node:::id4tg7xcko4qme4cko4swuu.node146
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:view:::id4tg7xcko4qme4cko4swuu
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 */
public class MaintenanceInspector extends JFrame implements Observer, ActionListener {
    /**
     * The Maintenance Inspector Screen interface has access to the AircraftManagementDatabase.
     *
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label accesses/observes
     * @directed
     */
    private AircraftManagementDatabase aircraftManagementDatabase;

    /**
     * The GateConsole interface has access to the GateInfoDatabase.*
     *
     * @supplierCardinality 1
     * @clientCardinality 0..*
     * @label accesses/observes
     * @directed
     */

    private ArrayList<Integer> maintMCodes;
    private ArrayList<Integer> faultMCodes;

    private JTabbedPane tabPane;

    private JList<String> maintainList;

    private JTextArea aircraftDetailsTa;

    private JButton reportFaultBtn;
    private JTextArea faultInputTa;
    private JButton completeMaintenanceBtn;

    private JList<String> faultyList;
    private JTextArea faultDescriptionTa;
    private JButton finishRepairBtn;

    /*
     * Constructor
     */
    public MaintenanceInspector(AircraftManagementDatabase amd) {
        maintMCodes = new ArrayList<Integer>();
        faultMCodes = new ArrayList<Integer>();
        amd.addObserver(this);
        this.aircraftManagementDatabase = amd;
        initGUI();
    }

    /*
     * Sets up the main window and initialises tabs
     */
    private void initGUI() {
        tabPane = new JTabbedPane();
        getContentPane().add(tabPane);
        initTab1();
        initTab2();

        setTitle("Maintenance Inspector");
        setVisible(true);
        setSize(550, 350);
        setLocation(630, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /*
     * Sets up the first tab for maintenance list and fault report
     */
    private void initTab1() {
    	//Create panel to hold the maintain list and info box for each flight
        JPanel maintainPanel = new JPanel();
        maintainPanel.setLayout(new BoxLayout(maintainPanel, BoxLayout.PAGE_AXIS));
        maintainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaintain = new JLabel("Ready for Maintenance");
        maintainList = new JList<String>(new DefaultListModel<String>());
        JScrollPane mScroll = new JScrollPane(maintainList);
        maintainList.setVisibleRowCount(aircraftManagementDatabase.maxMRs);
        //Updates the description text area when a flight is selected
        maintainList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!(maintainList.getSelectedIndex() == -1)) {
                    int i = maintainList.getSelectedIndex();
                    aircraftDetailsTa.setText(aircraftManagementDatabase.getRecordDetails(maintMCodes.get(i)));
                }
            }
        });
        JLabel lblInfo = new JLabel("Aircraft Info");

        aircraftDetailsTa = new JTextArea();

        aircraftDetailsTa = new JTextArea();

        completeMaintenanceBtn = new JButton("Complete Maintenance");
        completeMaintenanceBtn.addActionListener(this);
        
        //Adds elements to panel
        maintainPanel.add(lblMaintain);
        maintainPanel.add(mScroll);
        maintainPanel.add(lblInfo);

        maintainPanel.add(aircraftDetailsTa);
        maintainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        maintainPanel.add(aircraftDetailsTa);

        maintainPanel.add(completeMaintenanceBtn);

        //Create panel to hold the report fault button and accept user input for the fault description
        JPanel faultPanel = new JPanel();
        faultPanel.setLayout(new BoxLayout(faultPanel, BoxLayout.PAGE_AXIS));

        faultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        reportFaultBtn = new JButton("Report Fault");
        reportFaultBtn.addActionListener(this);
        
        //Adds elements to panel
        faultInputTa = new JTextArea(5, 10);
        faultInputTa.setLineWrap(true);
        faultInputTa.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(faultInputTa);

        faultPanel.add(reportFaultBtn);
        faultPanel.add(jsp);

        //Creates new panel to display both "maintainPanel" and "faultPanel" in the correct format
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(maintainPanel);
        panel.add(faultPanel);
        tabPane.addTab("Maintenance", panel);
    }

    /*
     * Sets up the second tab for displaying fault reports
     */
    private void initTab2() {
    	//Creates a panel to hold the faulty flights list and info on each selected flight
        JPanel faultDesPanel = new JPanel();
        faultDesPanel.setLayout(new BoxLayout(faultDesPanel, BoxLayout.PAGE_AXIS));
       
        JLabel lblfList = new JLabel("Aircraft requiring repair");
        faultyList = new JList<String>(new DefaultListModel<String>());
        faultyList.setFixedCellWidth(400);
      //Updates the description text area when a flight is selected
        faultyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!(faultyList.getSelectedIndex() == -1)) {
                    int mCodeIndex = faultyList.getSelectedIndex();
                    faultDescriptionTa.setText(aircraftManagementDatabase.getRecordDetails(faultMCodes.get(mCodeIndex)));
                }
            }
        });
        JScrollPane fScroll = new JScrollPane(faultyList);

        faultDescriptionTa = new JTextArea();
        faultDescriptionTa.setLineWrap(true);
        faultDescriptionTa.setWrapStyleWord(true);
        
        finishRepairBtn = new JButton("Complete Repair for selected aircraft");
        finishRepairBtn.addActionListener(this);
        faultDesPanel.add(lblfList);
        faultDesPanel.add(fScroll);
        faultDesPanel.add(faultDescriptionTa);
        faultDesPanel.add(finishRepairBtn);

        tabPane.addTab("Fault Repairs", faultDesPanel);
    }

    /*
     * Once maintenance is complete, move selected flight onto the next status 
     */
    @Override
    public void actionPerformed(ActionEvent evn) {
	    int index = maintainList.getSelectedIndex();
		if(evn.getSource() == completeMaintenanceBtn){
		
		    if(maintainList.getSelectedIndex()== -1){
		        JOptionPane.showMessageDialog(this,"Please select an aircraft");
		    }
		    else{
		        int mCode = maintMCodes.get(index);
		        int status = aircraftManagementDatabase.getStatus(maintMCodes.get(index));
			        
		        if(status == ManagementRecord.READY_CLEAN_AND_MAINT){
		            aircraftManagementDatabase.setStatus(mCode,ManagementRecord.OK_AWAIT_CLEAN);
		        } else if(status == ManagementRecord.CLEAN_AWAIT_MAINT){
		            aircraftManagementDatabase.setStatus(mCode, ManagementRecord.READY_REFUEL);
		        }
		    }
		}

        if (evn.getSource() == reportFaultBtn) {
            if (maintainList.getSelectedIndex() == -1) 
                JOptionPane.showMessageDialog(this,"Please select an aircraft");
            else{
                String description = faultInputTa.getText();
                if(description.equals(""))
                    JOptionPane.showMessageDialog(this, "Please provide a description of the fault");
                else{
                    int mCode = maintMCodes.get(index);
                    aircraftManagementDatabase.faultsFound(mCode, description);
                    JOptionPane.showMessageDialog(this, "Fault Acknowledged and Reported");
                    faultInputTa.setText("");
                }
            }
        }

        if (evn.getSource() == finishRepairBtn) {
        	int fIndex = faultyList.getSelectedIndex();
            if (fIndex == -1) {
                JOptionPane.showMessageDialog(this, "No Aircraft Selected");
            } else {
                int mCode = faultMCodes.get(fIndex);
                int status = aircraftManagementDatabase.getStatus(mCode);
                if(status == ManagementRecord.AWAIT_REPAIR){
                    aircraftManagementDatabase.setStatus(mCode,ManagementRecord.READY_CLEAN_AND_MAINT);
                    aircraftManagementDatabase.repair(mCode);
                } else {
                	JOptionPane.showMessageDialog(this, "Aircraft must be cleaned before repairs can take place.");
                }
            }
        }
    }

    /*
     * Called when notified of change by the AircraftManagementDatabase
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        faultyList.setSelectedIndex(-1);
        maintainList.setSelectedIndex(-1);
        faultInputTa.setText("");
        faultDescriptionTa.setText("");
        updateMaintenanceList();
        updateFaultyList();
    }

    /*
     * Retrieve all ManagementRecords with maintenance statuses and update JList
     */
    private void updateMaintenanceList() {
        maintMCodes.clear();
        maintMCodes.addAll(Arrays.asList(aircraftManagementDatabase.getWithStatus(ManagementRecord.READY_CLEAN_AND_MAINT)));
        maintMCodes.addAll(Arrays.asList(aircraftManagementDatabase.getWithStatus(ManagementRecord.CLEAN_AWAIT_MAINT)));

        String[] flightCodes = new String[maintMCodes.size()];
        for (int i = 0; i < maintMCodes.size(); i++) {
            flightCodes[i] = aircraftManagementDatabase.getFlightCode(maintMCodes.get(i)) + " :" + aircraftManagementDatabase.getStatusString(maintMCodes.get(i));
        }

        maintainList.setListData(flightCodes);
        maintainList.updateUI();
    }
    
    /*
     * Retrieve all ManagementRecords with maintenance statuses and update JList
     */
    private void updateFaultyList() {
        faultMCodes.clear();
        faultMCodes.addAll(Arrays.asList(aircraftManagementDatabase.getWithStatus(ManagementRecord.FAULTY_AWAIT_CLEAN)));
        faultMCodes.addAll(Arrays.asList(aircraftManagementDatabase.getWithStatus(ManagementRecord.AWAIT_REPAIR)));

        String[] flightCodes = new String[faultMCodes.size()];
        for (int i = 0; i < faultMCodes.size(); i++) {
            flightCodes[i] = aircraftManagementDatabase.getFlightCode(faultMCodes.get(i)) + " :" + aircraftManagementDatabase.getStatusString(faultMCodes.get(i));
        }

        faultyList.setListData(flightCodes);
        faultyList.updateUI();
    }

}
