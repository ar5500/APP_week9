import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ServiceCostModel {
    private static final int GENERAL_SERVICE = 1000;
    private static final int OIL_CHANGE = 800;
    private static final int BRAKE_SERVICE = 1200;
    private static final int BATTERY_CHECK = 500;

    public int calculateCost(boolean general, boolean oil, boolean brake, boolean battery) {
        int cost = 0;
        if (general) cost += GENERAL_SERVICE;
        if (oil) cost += OIL_CHANGE;
        if (brake) cost += BRAKE_SERVICE;
        if (battery) cost += BATTERY_CHECK;
        return cost;
    }
}

class ServiceCostView extends JFrame {
    JTextField regNumberField = new JTextField(15);
    JComboBox<String> vehicleTypeBox = new JComboBox<>(new String[]{"Two Wheeler", "Car"});
    JCheckBox generalServiceBox = new JCheckBox("General Service - ₹1000");
    JCheckBox oilChangeBox = new JCheckBox("Oil Change - ₹800");
    JCheckBox brakeServiceBox = new JCheckBox("Brake Service - ₹1200");
    JCheckBox batteryCheckBox = new JCheckBox("Battery Check - ₹500");
    JButton calculateButton = new JButton("Calculate Cost");
    JTextArea resultArea = new JTextArea(5, 25);

    public ServiceCostView() {
        setTitle("Vehicle Service Cost Estimator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Vehicle Registration Number:"));
        add(regNumberField);

        add(new JLabel("Vehicle Type:"));
        add(vehicleTypeBox);

        add(generalServiceBox);
        add(oilChangeBox);
        add(brakeServiceBox);
        add(batteryCheckBox);

        add(calculateButton);

        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    public void displayResult(String result) {
        resultArea.setText(result);
    }
}

class ServiceCostController {
    private ServiceCostView view;
    private ServiceCostModel model;

    public ServiceCostController(ServiceCostView view, ServiceCostModel model) {
        this.view = view;
        this.model = model;
        this.view.calculateButton.addActionListener(new CalculateListener());
    }

    class CalculateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String regNumber = view.regNumberField.getText();
            String vehicleType = (String) view.vehicleTypeBox.getSelectedItem();

            boolean general = view.generalServiceBox.isSelected();
            boolean oil = view.oilChangeBox.isSelected();
            boolean brake = view.brakeServiceBox.isSelected();
            boolean battery = view.batteryCheckBox.isSelected();

            int totalCost = model.calculateCost(general, oil, brake, battery);

            String result = "Vehicle Reg. No: " + regNumber +
                    "\nVehicle Type: " + vehicleType +
                    "\nTotal Service Cost: ₹" + totalCost;

            view.displayResult(result);
        }
    }
}

public class VehicleServiceCostEstimator {
    public static void main(String[] args) {
        ServiceCostView view = new ServiceCostView();
        ServiceCostModel model = new ServiceCostModel();
        new ServiceCostController(view, model);
    }
}
