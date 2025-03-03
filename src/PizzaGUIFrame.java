// PizzaGUIFrame.java
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame implements ActionListener {

  private JRadioButton thinCrust, regularCrust, deepDishCrust;
  private JComboBox<String> sizeComboBox;
  private JCheckBox pepperoni, mushrooms, olives, onions, sausage, extraCheese;
  private JTextArea orderTextArea;
  private JButton orderButton, clearButton, quitButton;

  public PizzaGUIFrame() {
    setTitle("Pizza Order Form");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Crust Panel
    JPanel crustPanel = new JPanel();
    crustPanel.setLayout(new FlowLayout());
    crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
    thinCrust = new JRadioButton("Thin");
    regularCrust = new JRadioButton("Regular");
    deepDishCrust = new JRadioButton("Deep-dish");
    ButtonGroup crustGroup = new ButtonGroup();
    crustGroup.add(thinCrust);
    crustGroup.add(regularCrust);
    crustGroup.add(deepDishCrust);
    crustPanel.add(thinCrust);
    crustPanel.add(regularCrust);
    crustPanel.add(deepDishCrust);

    // Size Panel
    JPanel sizePanel = new JPanel();
    sizePanel.setLayout(new FlowLayout());
    sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
    String[] sizes = {"Small", "Medium", "Large", "Super"};
    sizeComboBox = new JComboBox<>(sizes);
    sizePanel.add(sizeComboBox);

    // Toppings Panel
    JPanel toppingsPanel = new JPanel();
    toppingsPanel.setLayout(new GridLayout(0, 2)); // 2 columns
    toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
    pepperoni = new JCheckBox("Pepperoni");
    mushrooms = new JCheckBox("Mushrooms");
    olives = new JCheckBox("Olives");
    onions = new JCheckBox("Onions");
    sausage = new JCheckBox("Sausage");
    extraCheese = new JCheckBox("Extra Cheese");
    toppingsPanel.add(pepperoni);
    toppingsPanel.add(mushrooms);
    toppingsPanel.add(olives);
    toppingsPanel.add(onions);
    toppingsPanel.add(sausage);
    toppingsPanel.add(extraCheese);

    // Order Text Area Panel
    JPanel orderPanel = new JPanel();
    orderPanel.setLayout(new BorderLayout());
    orderPanel.setBorder(BorderFactory.createTitledBorder("Order Receipt"));
    orderTextArea = new JTextArea(10, 30);
    orderTextArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(orderTextArea);
    orderPanel.add(scrollPane, BorderLayout.CENTER);

    // Button Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    orderButton = new JButton("Order");
    clearButton = new JButton("Clear");
    quitButton = new JButton("Quit");
    orderButton.addActionListener(this);
    clearButton.addActionListener(this);
    quitButton.addActionListener(this);
    buttonPanel.add(orderButton);
    buttonPanel.add(clearButton);
    buttonPanel.add(quitButton);

    // Layout the panels in the frame
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(0, 2)); // 2 columns for crust, size, toppings
    inputPanel.add(crustPanel);
    inputPanel.add(sizePanel);
    inputPanel.add(toppingsPanel);


    add(inputPanel, BorderLayout.NORTH);
    add(orderPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(null); // Center the frame
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == quitButton) {
      int choice = JOptionPane.showConfirmDialog(this,
              "Are you sure you want to quit?",
              "Quit Confirmation", JOptionPane.YES_NO_OPTION);
      if (choice == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    } else if (e.getSource() == clearButton) {
      clearForm();
    } else if (e.getSource() == orderButton) {
      displayOrder();
    }
  }

  private void clearForm() {
    thinCrust.setSelected(false);
    regularCrust.setSelected(false);
    deepDishCrust.setSelected(false);
    sizeComboBox.setSelectedIndex(0);
    pepperoni.setSelected(false);
    mushrooms.setSelected(false);
    olives.setSelected(false);
    onions.setSelected(false);
    sausage.setSelected(false);
    extraCheese.setSelected(false);
    orderTextArea.setText("");
  }

  private void displayOrder() {
    String crustType = "";
    if (thinCrust.isSelected()) {
      crustType = "Thin";
    } else if (regularCrust.isSelected()) {
      crustType = "Regular";
    } else if (deepDishCrust.isSelected()) {
      crustType = "Deep-dish";
    } else {
      JOptionPane.showMessageDialog(this, "Please select a crust type.", "Crust Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String size = (String) sizeComboBox.getSelectedItem();
    if (size == null) {
      JOptionPane.showMessageDialog(this, "Please select a size.", "Size Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String toppings = "";
    double toppingsCost = 0;
    if (pepperoni.isSelected()) {
      toppings += "Pepperoni\n";
      toppingsCost++;
    }
    if (mushrooms.isSelected()) {
      toppings += "Mushrooms\n";
      toppingsCost++;
    }
    if (olives.isSelected()) {
      toppings += "Olives\n";
      toppingsCost++;
    }
    if (onions.isSelected()) {
      toppings += "Onions\n";
      toppingsCost++;
    }
    if (sausage.isSelected()) {
      toppings += "Sausage\n";
      toppingsCost++;
    }
    if (extraCheese.isSelected()) {
      toppings += "Extra Cheese\n";
      toppingsCost++;
    }

    if (toppings.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please select at least one topping.", "Toppings Error", JOptionPane.ERROR_MESSAGE);
      return;
    }


    double baseCost = 0;
    switch (size) {
      case "Small":
        baseCost = 8.00;
        break;
      case "Medium":
        baseCost = 12.00;
        break;
      case "Large":
        baseCost = 16.00;
        break;
      case "Super":
        baseCost = 20.00;
        break;
    }

    double subTotal = baseCost + toppingsCost;
    double tax = subTotal * 0.07;
    double total = subTotal + tax;

    StringBuilder receiptText = new StringBuilder();
    receiptText.append("=========================================\n");
    receiptText.append("Type of Crust & Size\tPrice\n");
    receiptText.append(crustType).append(", ").append(size).append("\t$").append(String.format("%.2f", baseCost)).append("\n");
    receiptText.append("-----------------------------------------\n");
    receiptText.append("Ingredient\t\tPrice\n");
    receiptText.append(toppings);
    receiptText.append("-----------------------------------------\n");
    receiptText.append("Sub-total:\t\t$").append(String.format("%.2f", subTotal)).append("\n");
    receiptText.append("Tax (7%):\t\t$").append(String.format("%.2f", tax)).append("\n");
    receiptText.append("---------------------------------------------------------------------\n");
    receiptText.append("Total:\t\t\t$").append(String.format("%.2f", total)).append("\n");
    receiptText.append("=========================================\n");

    orderTextArea.setText(receiptText.toString());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new PizzaGUIFrame());
  }
}