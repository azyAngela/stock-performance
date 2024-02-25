package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.border.Border;

/**
 * This is the new view class for GUI displaying texts and buttons.
 */
public class JFrameView extends JFrame {
  String address2 = "src/resource/UserData/";
  String[] finalPortList;
  private JLabel display;
  private JLabel loadMsg;
  //private JLabel load;
  private JTextField weightPercentInputNew;
  private JTextField commissionInput;
  private JLabel errorCreateDCA;
  private JTextArea stockCompDisplay;
  private JButton loadFile;
  private JButton loginBtn;
  private JButton createByDCA;
  private JTextArea totalValueDisplay;
  private JTextArea costDisplay;
  private JLabel errorMessageCost;
  private JLabel errorDate;
  private JLabel errorMessageRegular;

  private JTextField nameInput;
  private JTextField startDateInput;
  private JTextField endDateInput;
  private JTextField frequencyInput;
  private JTextField weightOnlyCommissionInput;
  private JTextField weightOnlyDateInput;
  private JTextField regularCommissionInput;
  private JTextField addDeleteDateInput;
  private JTextField loadFileInput;
  private JTextField stockName;
  private JTextField commissionFeeInput;
  private JTextField portNameInputNew;
  private JTextField amountInvestOnlyInputNew;
  private JTextField costDateInput;
  private JTextField amountInvestOnlyInput;
  private JTextField weightPercentInput;

  private JButton createNew;
  private JButton viewExisting;
  private JButton addFromExisting;
  private JButton deleteFromExisting;
  private JButton loadFileBtn;
  private JButton viewCostBasis;
  private JButton addAnother;
  private JButton confirmCreate;
  private JButton confirm;
  private JButton confirmAddingByWeightOnly;
  private JButton confirmAddingByDCA;
  private JButton viewComp;
  private JButton confirmStockByWeightOnly;
  private JButton createDollarCost;
  private JButton confirmExisting;
  private JButton confirmAddMethod;
  private JButton percentAddNew;
  private JButton confirmRegular;
  private JButton percentAdd;
  private JButton confirmDateCostBasis;
  JComboBox<String> combobox;
  JComboBox<String> comboboxPortlist;
  JComboBox<String> comboboxPortlist2;
  JComboBox<String> combobox3;
  JComboBox<String> comboboxPortlist3;
  JComboBox<String> comboboxPortlist4;

  //  private JLabel name;
  private JLabel nameStatus;

  //  private JLabel portName;
  //  private JLabel stockComposition;
  //  private JLabel dateTotalValue;
  //  private JLabel totalValue;
  //  private JLabel addMethod;
  private JTextField portNameInput;
  private JTextField stockInput2;
  private JTextField sharesInput2;
  private JTextField startDateInputNew;
  private JTextField endDateInputNew;
  private JTextField frequencyInputNew;

  //private JLabel stock;
  public JTextField stockInput;

  //private JLabel shares;
  private JLabel errorMessageWeight;

  //private JLabel existingPort;
  private JTextField sharesInput;
  private JTextField dateTotalValueInput;

  private JPanel panel3;
  private JPanel panel4;
  private JPanel panel5;
  private JPanel panel6;
  private JPanel panel7;

  private JPanel panel8;
  private JLabel addSuccess;

  /**
   * This is the view that uses JFrame to prepare for the instructions on GUI.
   *
   * @param caption caption parameter
   */
  public JFrameView(String caption) {
    super(caption);
    setSize(1000, 1000);
    setLocation(10, 10);
    setTitle("Portfolio management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    //setting the bounds for the JFrame
    setBounds(500, 500, 1000, 1000);
    Border br = BorderFactory.createLineBorder(Color.black);
    Container c = getContentPane();
    //Creating a JPanel for the JFrame
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    panel3 = new JPanel();
    panel4 = new JPanel();
    panel5 = new JPanel();
    panel6 = new JPanel();
    panel7 = new JPanel();
    panel8 = new JPanel();
    //setting the panel layout as null
    //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setLayout(null);
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    panel3.setLayout(null);
    panel4.setLayout(null);
    panel5.setLayout(null);
    panel6.setLayout(null);
    panel7.setLayout(null);
    panel8.setLayout(null);
    //adding a label element to the panel
    //JLabel label=new JLabel("Panel 1");
    //JLabel label2=new JLabel("Panel 2");
    //JLabel label3=new JLabel("Panel 3");
    //JLabel label4=new JLabel("Panel 4");
    //label.setBounds(120,50,200,50);
    //label2.setBounds(120,50,200,50);
    //label3.setBounds(120,50,200,50);
    //label4.setBounds(120,50,200,50);

    //panel 1里面加的内容
    JLabel comboboxDisplay = new JLabel("Select portfolio type: ");
    //comboboxDisplay.setAlignmentX(LEFT_ALIGNMENT);
    panel.add(comboboxDisplay);
    comboboxDisplay.setBounds(15, 15, 200, 25);

    String[] options = {"Flexible portfolio"};
    combobox = new JComboBox<String>();
    //the event listener when an option is selected
    combobox.setActionCommand("Portfolio type selection");
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    panel.add(combobox);
    combobox.setBounds(8, 40, 200, 25);

    JLabel name = new JLabel("Please input your name: ");
    panel.add(name);
    name.setBounds(15, 65, 200, 25);

    nameInput = new JTextField(10);
    //nameInput.setMaximumSize(nameInput.getPreferredSize());
    panel.add(nameInput);
    nameInput.setBounds(10, 90, 200, 25);

    nameStatus = new JLabel("");
    panel.add(nameStatus);
    nameStatus.setBounds(15, 140, 200, 25);

    loginBtn = new JButton("Log in");
    loginBtn.setBounds(10, 115, 200, 30);
    loginBtn.setActionCommand("Log in");
    panel.add(loginBtn);

    //panel 2里面加的内容
    createNew = new JButton("Create new");
    createNew.setBounds(330, 20, 200, 30);
    createNew.setActionCommand("create new");

    viewExisting = new JButton("View existing");
    viewExisting.setBounds(330, 50, 200, 30);
    viewExisting.setActionCommand("view existing");

    loadFile = new JButton("Load file");
    loadFile.setBounds(330, 80, 200, 30);
    loadFile.setActionCommand("load file");

    JLabel load = new JLabel("Enter file name: ");
    panel8.add(load);
    load.setBounds(15, 15, 200, 30);

    loadFileInput = new JTextField(10);
    panel8.add(loadFileInput);
    loadFileInput.setBounds(15, 40, 200, 25);

    loadFileBtn = new JButton("Load");
    loadFileBtn.setBounds(15, 65, 100, 30);
    loadFileBtn.setActionCommand("load action btn");
    panel8.add(loadFileBtn);

    loadMsg = new JLabel("");
    panel8.add(loadMsg);
    loadMsg.setBounds(15, 100, 200, 30);

    addFromExisting = new JButton("Add/delete from existing");
    addFromExisting.setBounds(550, 20, 200, 30);
    addFromExisting.setActionCommand("Add/delete from existing");

    viewCostBasis = new JButton("View cost basis");
    viewCostBasis.setBounds(550, 80, 200, 30);
    viewCostBasis.setActionCommand("View cost basis");

    createDollarCost = new JButton("Create new (dollar-cost averaging strategy)");
    createDollarCost.setBounds(550, 80, 250, 30);
    createDollarCost.setActionCommand("dollar cost strategy");

    panel2.add(createNew);
    panel2.add(viewExisting);
    panel2.add(loadFile);
    panel2.add(addFromExisting);
    //panel2.add(deleteFromExisting);
    panel2.add(viewCostBasis);
    panel2.add(createDollarCost);

    //panel 3里面加的内容
    JLabel portName = new JLabel("Portfolio name: ");
    //portName.setForeground(new Color(0,0,0));
    panel3.add(portName);
    portName.setBounds(15, 15, 200, 25);

    portNameInput = new JTextField(10);
    panel3.add(portNameInput);
    portNameInput.setBounds(10, 40, 200, 25);

    JLabel stock = new JLabel("Stock: ");
    panel3.add(stock);
    stock.setBounds(15, 65, 200, 25);

    stockInput = new JTextField(10);
    panel3.add(stockInput);
    stockInput.setBounds(10, 90, 200, 25);

    JLabel shares = new JLabel("Shares: ");
    //shares.setForeground(new Color(0,0,0));
    panel3.add(shares);
    shares.setBounds(15, 115, 200, 25);

    sharesInput = new JTextField(10);
    panel3.add(sharesInput);
    sharesInput.setBounds(10, 140, 200, 25);

    JLabel commissionFee = new JLabel("Commission fee (ex: 0.1): ");
    //shares.setForeground(new Color(0,0,0));
    panel3.add(commissionFee);
    commissionFee.setBounds(15, 165, 200, 25);

    commissionFeeInput = new JTextField(10);
    panel3.add(commissionFeeInput);
    commissionFeeInput.setBounds(10, 190, 200, 25);

    addSuccess = new JLabel("");
    //shares.setForeground(new Color(0,0,0));
    panel3.add(addSuccess);
    addSuccess.setBounds(15, 225, 200, 25);

    addAnother = new JButton("Add more stock");
    addAnother.setBounds(230, 140, 150, 25);
    addAnother.setActionCommand("Add more stock");
    panel3.add(addAnother);

    confirmCreate = new JButton("Submit");
    confirmCreate.setBounds(200, 15, 90, 25);
    confirmCreate.setActionCommand("submit");
    panel3.add(confirmCreate);

    // panel 4里面的内容
    JLabel existingPort = new JLabel("Existing portfolio: ");
    panel4.add(existingPort);
    existingPort.setBounds(20, 3, 200, 50);

    address2 = address2 + getName() + "/" + "portfolioData";
    //String[] portList = (String[]) SearchFile.search(address2).toArray();
    String[] portList = {""};
    finalPortList = portList;
    //     List<String> list = SearchFile.search(address2);
    //    String[] portList;
    //    if (list != null) {
    //       portList = (String[]) list.toArray();
    //    } else {
    //      portList = new String[]{""};
    //    }
    comboboxPortlist = new JComboBox<String>();
    comboboxPortlist.setActionCommand("Portfolio list");
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist.addItem(finalPortList[i]);
    }
    panel4.add(comboboxPortlist);
    comboboxPortlist.setBounds(15, 40, 200, 25);

    JLabel stockComposition = new JLabel("Stock Composition: ");
    panel4.add(stockComposition);
    stockComposition.setBounds(20, 55, 200, 50);

    stockCompDisplay = new JTextArea(10, 20);
    JScrollPane scrollPane = new JScrollPane(stockCompDisplay);
    stockCompDisplay.setLineWrap(true);
    stockCompDisplay.setWrapStyleWord(true);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Stock Composition: "));
    stockCompDisplay.setBounds(15, 100, 230, 200);
    panel4.add(scrollPane);
    panel4.add(stockCompDisplay);

    JLabel dateTotalValue = new JLabel("Total value on which date: ");
    panel4.add(dateTotalValue);
    dateTotalValue.setBounds(250, 3, 200, 50);

    dateTotalValueInput = new JTextField(100);
    panel4.add(dateTotalValueInput);
    dateTotalValueInput.setBounds(250, 40, 200, 25);

    JLabel totalValue = new JLabel("Total value: ");
    panel4.add(totalValue);
    totalValue.setBounds(260, 55, 200, 50);

    totalValueDisplay = new JTextArea(10, 20);
    JScrollPane scrollPane2 = new JScrollPane(totalValueDisplay);
    totalValueDisplay.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane2.setBorder(BorderFactory.createTitledBorder("Total value: "));
    totalValueDisplay.setBounds(260, 100, 300, 200);
    panel4.add(totalValueDisplay);

    errorDate = new JLabel("");
    panel4.add(errorDate);
    errorDate.setBounds(260, 285, 200, 50);

    confirm = new JButton("Confirm");
    confirm.setBounds(450, 38, 130, 30);
    confirm.setActionCommand("confirm");
    panel4.add(confirm);

    viewComp = new JButton("View comp.");
    viewComp.setBounds(140, 14, 100, 30);
    viewComp.setActionCommand("View comp");
    panel4.add(viewComp);

    //panel 5里面加的内容
    JLabel existingPortAddFromExisting = new JLabel("Existing portfolio: ");
    panel5.add(existingPortAddFromExisting);
    existingPortAddFromExisting.setBounds(20, 3, 200, 50);

    address2 = address2 + getName() + "/" + "portfolioData";
    //String[] portList = (String[]) SearchFile.search(address2).toArray();
    String[] portList2 = {""};
    finalPortList = portList2;
    comboboxPortlist2 = new JComboBox<String>();
    comboboxPortlist2.setActionCommand("Portfolio list");
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist2.addItem(finalPortList[i]);
    }
    panel5.add(comboboxPortlist2);
    comboboxPortlist2.setBounds(15, 40, 200, 25);

    confirmExisting = new JButton("Confirm");
    confirmExisting.setBounds(205, 37, 100, 30);
    confirmExisting.setActionCommand("confirm existing");
    panel5.add(confirmExisting);

    JLabel addMethod = new JLabel("Add/delete method: ");
    panel5.add(addMethod);
    addMethod.setBounds(20, 50, 200, 50);

    String[] options3 = {"Regular (by shares) - add", "Regular (by shares)"
            +
            " - delete", "By weight only - add", "Dollar-cost averaging strategy - add"};
    combobox3 = new JComboBox<String>();
    //the event listener when an option is selected
    combobox3.setActionCommand("add/delete method type selection");
    for (int i = 0; i < options3.length; i++) {
      combobox3.addItem(options3[i]);
    }
    panel5.add(combobox3);
    combobox3.setBounds(15, 85, 200, 25);

    confirmAddMethod = new JButton("Confirm");
    confirmAddMethod.setBounds(205, 83, 100, 30);
    confirmAddMethod.setActionCommand("confirm add method");
    panel5.add(confirmAddMethod);

    // 以下是为panel5 的 Regular (by shares) add/delete
    JLabel regularMethod = new JLabel("Regular (by shares) add/delete: ");
    panel5.add(regularMethod);
    regularMethod.setBounds(15, 120, 200, 25);

    stock = new JLabel("Stock: ");
    panel5.add(stock);
    stock.setBounds(15, 140, 200, 25);

    stockInput2 = new JTextField(10);
    panel5.add(stockInput2);
    stockInput2.setBounds(10, 160, 200, 25);

    shares = new JLabel("Shares: ");
    //shares.setForeground(new Color(0,0,0));
    panel5.add(shares);
    shares.setBounds(15, 180, 200, 25);

    sharesInput2 = new JTextField(10);
    panel5.add(sharesInput2);
    sharesInput2.setBounds(10, 200, 200, 25);

    JLabel regularCommission = new JLabel("Commission fee (ex: 0.1): ");
    //shares.setForeground(new Color(0,0,0));
    panel5.add(regularCommission);
    regularCommission.setBounds(10, 220, 200, 25);

    regularCommissionInput = new JTextField(10);
    panel5.add(regularCommissionInput);
    regularCommissionInput.setBounds(10, 240, 200, 25);

    JLabel addDeleteDate = new JLabel("Date (ex: 2022-09-01): ");
    //shares.setForeground(new Color(0,0,0));
    panel5.add(addDeleteDate);
    addDeleteDate.setBounds(10, 260, 200, 25);

    addDeleteDateInput = new JTextField(10);
    panel5.add(addDeleteDateInput);
    addDeleteDateInput.setBounds(10, 280, 200, 25);

    confirmRegular = new JButton("Confirm add/delete");
    confirmRegular.setBounds(80, 310, 150, 30);
    confirmRegular.setActionCommand("confirm regular");
    panel5.add(confirmRegular);

    // 以下是为panel5 的 by weight only
    JLabel weightOnly = new JLabel("By weight only / DCA strategy: ");
    panel5.add(weightOnly);
    weightOnly.setBounds(270, 120, 200, 25);

    JLabel amountInvestOnly = new JLabel("Total amount invest: ");
    panel5.add(amountInvestOnly);
    amountInvestOnly.setBounds(270, 140, 200, 25);

    amountInvestOnlyInput = new JTextField(10);
    panel5.add(amountInvestOnlyInput);
    amountInvestOnlyInput.setBounds(270, 160, 200, 25);

    JLabel stock2 = new JLabel("Stock: ");
    panel5.add(stock2);
    stock2.setBounds(270, 180, 200, 25);

    String[] portList3 = {""};
    finalPortList = portList3;
    comboboxPortlist3 = new JComboBox<String>();
    comboboxPortlist3.setActionCommand("choose stock by weight only");
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist3.addItem(finalPortList[i]);
    }
    panel5.add(comboboxPortlist3);
    comboboxPortlist3.setBounds(270, 200, 200, 25);

    JLabel weightPercent = new JLabel("Weight percent: ");
    panel5.add(weightPercent);
    weightPercent.setBounds(270, 220, 200, 25);

    weightPercentInput = new JTextField(10);
    panel5.add(weightPercentInput);
    weightPercentInput.setBounds(270, 240, 200, 25);

    JLabel regularCommission2 = new JLabel("Commission fee (ex: 0.1): ");
    //shares.setForeground(new Color(0,0,0));
    panel5.add(regularCommission2);
    regularCommission2.setBounds(270, 260, 200, 25);

    weightOnlyCommissionInput = new JTextField(10);
    panel5.add(weightOnlyCommissionInput);
    weightOnlyCommissionInput.setBounds(270, 280, 200, 25);

    JLabel addDeleteDate2 = new JLabel("Date (ex: 2022-09-01) - only enter for weight-only: ");
    //shares.setForeground(new Color(0,0,0));
    panel5.add(addDeleteDate2);
    addDeleteDate2.setBounds(270, 300, 400, 25);

    weightOnlyDateInput = new JTextField(10);
    panel5.add(weightOnlyDateInput);
    weightOnlyDateInput.setBounds(270, 320, 200, 25);

    percentAdd = new JButton("+");
    percentAdd.setBounds(490, 240, 30, 25);
    percentAdd.setActionCommand("Add weight only percent add");
    panel5.add(percentAdd);

    confirmAddingByWeightOnly = new JButton("Confirm weight only");
    confirmAddingByWeightOnly.setBounds(590, 200, 200, 30);
    confirmAddingByWeightOnly.setActionCommand("confirm adding finally by weight only");
    panel5.add(confirmAddingByWeightOnly);

    confirmAddingByDCA = new JButton("Confirm add by DCA");
    confirmAddingByDCA.setBounds(590, 240, 200, 30);
    confirmAddingByDCA.setActionCommand("confirm adding finally by DCA");
    panel5.add(confirmAddingByDCA);

    // for dollar cost averaging strategy
    weightPercent = new JLabel("Start date (ex: 2022-09-01) - enter only for DCA strategy: ");
    panel5.add(weightPercent);
    weightPercent.setBounds(270, 340, 500, 25);

    startDateInput = new JTextField(10);
    panel5.add(startDateInput);
    startDateInput.setBounds(270, 360, 200, 25);

    weightPercent = new JLabel("End date (ex: 2022-09-01) - enter only for DCA "
            +
            "strategy - can leave blank: ");
    panel5.add(weightPercent);
    weightPercent.setBounds(270, 380, 500, 25);

    endDateInput = new JTextField(10);
    panel5.add(endDateInput);
    endDateInput.setBounds(270, 400, 200, 25);

    weightPercent = new JLabel("Frequency (Enter amount in days) - "
            +
            "enter only for DCA strategy: ");
    panel5.add(weightPercent);
    weightPercent.setBounds(270, 420, 500, 25);

    frequencyInput = new JTextField(10);
    panel5.add(frequencyInput);
    frequencyInput.setBounds(270, 440, 200, 25);

    errorMessageRegular = new JLabel("");
    panel5.add(errorMessageRegular);
    errorMessageRegular.setBounds(15, 340, 400, 25);

    errorMessageWeight = new JLabel("");
    panel5.add(errorMessageWeight);
    errorMessageWeight.setBounds(270, 470, 400, 25);

    //panel 6里面加的内容
    existingPortAddFromExisting = new JLabel("Existing portfolio: ");
    panel6.add(existingPortAddFromExisting);
    existingPortAddFromExisting.setBounds(20, 3, 200, 50);

    address2 = address2 + getName() + "/" + "portfolioData";
    //String[] portList = (String[]) SearchFile.search(address2).toArray();
    String[] portList4 = {""};
    finalPortList = portList4;
    comboboxPortlist4 = new JComboBox<String>();
    comboboxPortlist4.setActionCommand("Portfolio list");
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist4.addItem(finalPortList[i]);
    }
    panel6.add(comboboxPortlist4);
    comboboxPortlist4.setBounds(15, 40, 200, 25);

    JLabel costDate = new JLabel("Date for cost basis (ex: 2022-09-01): ");
    panel6.add(costDate);
    costDate.setBounds(15, 60, 400, 25);

    costDateInput = new JTextField(10);
    panel6.add(costDateInput);
    costDateInput.setBounds(15, 80, 200, 25);

    confirmDateCostBasis = new JButton("Confirm");
    confirmDateCostBasis.setBounds(220, 80, 100, 25);
    confirmDateCostBasis.setActionCommand("confirm date cost basis");
    panel6.add(confirmDateCostBasis);

    costDisplay = new JTextArea(10, 20);
    JScrollPane scrollPane4 = new JScrollPane(costDisplay);
    costDisplay.setLineWrap(true);
    costDisplay.setWrapStyleWord(true);
    scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane4.setBorder(BorderFactory.createTitledBorder("Cost display: "));
    costDisplay.setBounds(15, 130, 300, 100);
    panel6.add(scrollPane);
    panel6.add(costDisplay);

    errorMessageCost = new JLabel("");
    panel6.add(errorMessageCost);
    errorMessageCost.setBounds(15, 260, 300, 25);

    //panel 7里面加的内容
    portName = new JLabel("Portfolio name: ");
    //portName.setForeground(new Color(0,0,0));
    panel7.add(portName);
    portName.setBounds(15, 15, 200, 25);

    portNameInputNew = new JTextField(10);
    panel7.add(portNameInputNew);
    portNameInputNew.setBounds(10, 40, 200, 25);

    amountInvestOnly = new JLabel("Total amount invest: ");
    panel7.add(amountInvestOnly);
    amountInvestOnly.setBounds(15, 65, 200, 25);

    amountInvestOnlyInputNew = new JTextField(10);
    panel7.add(amountInvestOnlyInputNew);
    amountInvestOnlyInputNew.setBounds(15, 90, 200, 25);

    stock2 = new JLabel("Stock: ");
    panel7.add(stock2);
    stock2.setBounds(15, 115, 200, 25);

    stockName = new JTextField(10);
    panel7.add(stockName);
    stockName.setBounds(15, 140, 200, 25);

    weightPercent = new JLabel("Weight percent: ");
    panel7.add(weightPercent);
    weightPercent.setBounds(15, 165, 200, 25);

    weightPercentInputNew = new JTextField(10);
    panel7.add(weightPercentInputNew);
    weightPercentInputNew.setBounds(15, 190, 200, 25);

    percentAddNew = new JButton("Add");
    percentAddNew.setBounds(220, 190, 60, 25);
    percentAddNew.setActionCommand("Add more DCA");
    panel7.add(percentAddNew);

    weightPercent = new JLabel("Start date (ex: 2022-09-01): ");
    panel7.add(weightPercent);
    weightPercent.setBounds(15, 240, 500, 25);

    startDateInputNew = new JTextField(10);
    panel7.add(startDateInputNew);
    startDateInputNew.setBounds(15, 265, 200, 25);

    weightPercent = new JLabel("End date (ex: 2022-09-01) - can leave blank if "
            +
            "not sure end date: ");
    panel7.add(weightPercent);
    weightPercent.setBounds(15, 290, 700, 25);

    endDateInputNew = new JTextField(10);
    panel7.add(endDateInputNew);
    endDateInputNew.setBounds(15, 315, 200, 25);

    weightPercent = new JLabel("Frequency (Enter amount in days): ");
    panel7.add(weightPercent);
    weightPercent.setBounds(15, 340, 500, 25);

    frequencyInputNew = new JTextField(10);
    panel7.add(frequencyInputNew);
    frequencyInputNew.setBounds(15, 365, 200, 25);

    JLabel commission = new JLabel("Commission fee: ");
    panel7.add(commission);
    commission.setBounds(15, 390, 500, 25);

    commissionInput = new JTextField(10);
    panel7.add(commissionInput);
    commissionInput.setBounds(15, 415, 200, 25);

    createByDCA = new JButton("Create portfolio");
    createByDCA.setBounds(590, 246, 200, 30);
    createByDCA.setActionCommand("Create portfolio by DCA");
    panel7.add(createByDCA);
    createByDCA.setBounds(15, 440, 160, 25);

    errorCreateDCA = new JLabel("error message");
    panel7.add(errorCreateDCA);
    errorCreateDCA.setBounds(15, 470, 500, 25);


    //Panel 1
    panel.setBackground(Color.yellow);
    panel.setBounds(10, 10, 300, 200);
    //Panel 2
    //panel2.setBackground(Color.PINK);
    panel2.setBounds(320, 10, 800, 200);
    //Panel 3
    panel3.setBackground(Color.pink);
    panel3.setBounds(10, 220, 800, 500);
    //Panel 4
    panel4.setBackground(Color.pink);
    //panel4.setBounds(320,220,600,330);
    panel4.setBounds(10, 220, 800, 500);
    //Panel 5
    panel5.setBackground(Color.pink);
    //panel5.setBounds(10,430,800,400);
    panel5.setBounds(10, 220, 800, 500);
    //Panel 6
    panel6.setBackground(Color.pink);
    panel6.setBounds(10, 220, 800, 500);
    //Panel 7
    panel7.setBackground(Color.pink);
    panel7.setBounds(10, 220, 800, 500);

    panel8.setBackground(Color.pink);
    panel8.setBounds(10, 220, 800, 500);

    // Panel border
    panel.setBorder(br);
    //panel2.setBorder(br);
    panel3.setBorder(br);
    panel4.setBorder(br);
    panel5.setBorder(br);
    panel6.setBorder(br);
    panel7.setBorder(br);
    panel8.setBorder(br);
    //panel4.add(label4);

    //adding the panel to the Container of the JFrame
    c.add(panel);
    c.add(panel2);
    c.add(panel3);
    c.add(panel4);
    c.add(panel5);
    c.add(panel6);
    c.add(panel7);
    c.add(panel8);
    setVisible(true);
    panel3.setVisible(false);
    panel4.setVisible(false);
    panel5.setVisible(false);
    panel6.setVisible(false);
    panel7.setVisible(false);
    panel8.setVisible(false);
    //pack();
  }

  /**
   * Method to display panel3.
   */
  public void createNewDisplay3() {
    panel3.setVisible(true);
    panel4.setVisible(false);
    panel5.setVisible(false);
    panel6.setVisible(false);
    panel7.setVisible(false);
    panel8.setVisible(false);
  }

  /**
   * Method to display panel4.
   */
  public void createNewDisplay4() {
    panel4.setVisible(true);
    panel3.setVisible(false);
    panel5.setVisible(false);
    panel6.setVisible(false);
    panel7.setVisible(false);
    panel8.setVisible(false);
  }

  /**
   * Method to display panel5.
   */
  public void createNewDisplay5() {
    panel5.setVisible(true);
    panel3.setVisible(false);
    panel4.setVisible(false);
    panel6.setVisible(false);
    panel7.setVisible(false);
    panel8.setVisible(false);
  }

  /**
   * Method to display panel6.
   */
  public void createNewDisplay6() {
    panel6.setVisible(true);
    panel3.setVisible(false);
    panel4.setVisible(false);
    panel5.setVisible(false);
    panel7.setVisible(false);
    panel8.setVisible(false);
  }

  /**
   * Method to display panel7.
   */
  public void createNewDisplay7() {
    panel7.setVisible(true);
    panel3.setVisible(false);
    panel4.setVisible(false);
    panel5.setVisible(false);
    panel6.setVisible(false);
    panel8.setVisible(false);
  }


  /**
   * Method to display panel8.
   */
  public void createNewDisplay8() {
    panel8.setVisible(true);
    panel3.setVisible(false);
    panel4.setVisible(false);
    panel5.setVisible(false);
    panel6.setVisible(false);
    panel7.setVisible(false);
  }

  /**
   * Set the listener when click the button.
   */
  public void setListener(ActionListener listener) {
    createNew.addActionListener(listener);
    confirmCreate.addActionListener(listener);
    addAnother.addActionListener(listener);
    viewExisting.addActionListener(listener);
    confirm.addActionListener(listener);
    viewComp.addActionListener(listener);
    addFromExisting.addActionListener(listener);
    confirmExisting.addActionListener(listener);
    confirmAddMethod.addActionListener(listener);
    confirmRegular.addActionListener(listener);
    confirmDateCostBasis.addActionListener(listener);
    viewCostBasis.addActionListener(listener);
    //confirmStockByWeightOnly.addActionListener(listener);
    percentAdd.addActionListener(listener);
    confirmAddingByWeightOnly.addActionListener(listener);
    confirmAddingByDCA.addActionListener(listener);
    createDollarCost.addActionListener(listener);
    createByDCA.addActionListener(listener);
    percentAddNew.addActionListener(listener);
    loadFileBtn.addActionListener(listener);
    loadFile.addActionListener(listener);
    loginBtn.addActionListener(listener);
  }

  // for panel 7

  /**
   * Get portfolio name.
   */
  public String getPortNameDCA() {
    return portNameInputNew.getText();
  }

  /**
   * Get amount to invest.
   */
  public String getTotalAmount() {
    return amountInvestOnlyInputNew.getText();
  }

  /**
   * Get stock entered.
   */
  public String getS() {
    return stockName.getText();
  }

  /**
   * Get the weight.
   */
  public String getW() {
    return weightPercentInputNew.getText();
  }

  /**
   * Get the date start.
   */
  public String getStartDateNew() {
    return startDateInputNew.getText();
  }

  /**
   * Get the date end.
   */
  public String getEndDateNew() {
    return endDateInputNew.getText();
  }

  /**
   * Get load.
   */
  public String getLoad() {
    return loadFileInput.getText();
  }

  /**
   * Get frequency.
   */
  public String getFreqNew() {
    return frequencyInputNew.getText();
  }

  /**
   * Get commission fee.
   */
  public String getCommissionNew() {
    return commissionInput.getText();
  }

  /**
   * set the error message.
   */
  public void setErrorDCA(String text) {
    errorCreateDCA.setText(text);
  }

  /**
   * clear the user input1.
   */
  public void clear11() {
    portNameInputNew.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear12() {
    stockName.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear13() {
    weightPercentInputNew.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear14() {
    startDateInputNew.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear15() {
    endDateInputNew.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear16() {
    frequencyInputNew.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear17() {
    commissionInput.setText("");
  }

  /**
   * clear the user input1.
   */
  public void clear18() {
    errorCreateDCA.setText("");
  }

  // add/delete from existing: for weight only

  /**
   * Get amount invested.
   */
  public String getAmountInvest() {
    return amountInvestOnlyInput.getText();
  }

  /**
   * Get the stock user chooses for the portfolio.
   */
  public String getStockSelection() {
    return (String) comboboxPortlist3.getSelectedItem();
  }

  /**
   * Get weight.
   */
  public String getWeight() {
    return weightPercentInput.getText();
  }

  /**
   * Get commission fee.
   */
  public String getCommission() {
    return weightOnlyCommissionInput.getText();
  }

  /**
   * Get date entered for the choice of weight only.
   */
  public String getDateWeightOnly() {
    return weightOnlyDateInput.getText();
  }

  /**
   * clear input field.
   */
  public void clear1() {
    amountInvestOnlyInput.setText("");
  }

  /**
   * clear input field.
   */
  public void clear2() {
    weightPercentInput.setText("");
  }

  /**
   * clear input field.
   */
  public void clear3() {
    weightOnlyCommissionInput.setText("");
  }

  /**
   * clear input field.
   */
  public void clear4() {
    weightOnlyDateInput.setText("");
  }

  // add/delete from existing: DCA strategy

  /**
   * get the start date of DCA.
   */
  public String getStartDate() {
    return startDateInput.getText();
  }

  /**
   * get the end date of DCA.
   */
  public String getEndDate() {
    return endDateInput.getText();
  }

  /**
   * get the frequency of DCA.
   */
  public String getFrequency() {
    return frequencyInput.getText();
  }

  /**
   * get the portfolio name.
   */
  public String getPortName() {
    return portNameInput.getText();
  }

  /**
   * get the stock.
   */
  public String getStock() {
    return stockInput.getText();
  }

  /**
   * get the date for cost basis.
   */
  public String getDateCost() {
    return costDateInput.getText();
  }

  /**
   * get commission fee.
   */
  public String getCommissionFee() {
    return commissionFeeInput.getText();

  }

  /**
   * get date input when regular add/delete.
   */
  public String getAddDeleteDateInput() {
    return addDeleteDateInput.getText();
  }

  /**
   * get commission fee.
   */
  public String getCommissionFeeInput() {
    return regularCommissionInput.getText();
  }

  // 这两个key跟panel3里面用的一样的，所以如果get不到数据的话就换个key

  /**
   * get the stock for regular.
   */
  public String getStockRegular() {
    return stockInput2.getText();
  }

  /**
   * get the regular shares.
   */
  public String getSharesRegular() {
    return sharesInput2.getText();
  }

  /**
   * get shares.
   */
  public String getShares() {
    return sharesInput.getText();
  }

  /**
   * get date.
   */
  public String getDate() {
    return dateTotalValueInput.getText();
  }

  /**
   * get the selected item in a combobox.
   */
  public String combobox() {
    return (String) combobox.getSelectedItem();
  }

  /**
   * get the selected item in a combobox.
   */
  public String stockDisplay() {
    return (String) comboboxPortlist.getSelectedItem();
  }

  /**
   * get the selected item in a combobox.
   */
  public String addDeleteMethod() {
    return (String) combobox3.getSelectedItem();
  }

  // add/delete from existing panel里面的选择portfolio

  /**
   * get the selected item in a combobox - portfolio list.
   */
  public String stockDisplay2() {
    return (String) comboboxPortlist2.getSelectedItem();
  }

  /**
   * get the selected item in a combobox - portfolio list.
   */
  public String stockDisplay4() {
    return (String) comboboxPortlist4.getSelectedItem();
  }

  /**
   * set the text for checking stock composition.
   */
  public void setStockComp(String text) {
    stockCompDisplay.setText(text);
  }

  /**
   * set the cost.
   */
  public void setCost(String text) {
    costDisplay.setText(text);
  }

  /**
   * set the error message when creating regular.
   */
  public void setRegularError(String text) {
    errorMessageRegular.setText(text);
  }

  /**
   * get the input name.
   */
  public String getName() {
    return nameInput.getText();
  }

  /**
   * set load error message.
   */
  public void setLoadMsg(String text) {
    loadMsg.setText(text);
  }

  /**
   * set the status message.
   */
  public void setStatusCreate(String text) {
    addSuccess.setText(text);
  }

  /**
   * set the cost error message.
   */
  public void setCostError(String text) {
    errorMessageCost.setText(text);
  }

  /**
   * set the cost text.
   */
  public void setCostText(String text) {
    costDisplay.setText(text);
  }

  /**
   * show the error message for bad dates.
   */
  public void showErrorDate(String text) {
    errorDate.setText(text);
  }

  /**
   * set the date.
   */
  public void setDateDisplay(String text) {
    totalValueDisplay.setText(text);
  }

  /**
   * set text for error message for weight.
   */
  public void setWeightError(String text) {
    errorMessageWeight.setText(text);
  }

  /**
   * set the content of a combobox of portfolio name list.
   *
   * @param list the list to be placed in the combobox
   */
  public void setFinalPortList(List<String> list) {
    String[] result2 = new String[list.size()];
    result2 = list.toArray(result2);
    comboboxPortlist.removeAllItems();
    comboboxPortlist2.removeAllItems();
    comboboxPortlist4.removeAllItems();
    finalPortList = result2;
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist.addItem(finalPortList[i]);
      comboboxPortlist2.addItem(finalPortList[i]);
      comboboxPortlist4.addItem(finalPortList[i]);
    }
    panel4.add(comboboxPortlist);
    panel5.add(comboboxPortlist2);
    panel6.add(comboboxPortlist4);
  }

  /**
   * set the content of a combobox of stock list.
   *
   * @param list the list to be placed in the combobox for stock in specific portfolio.
   */
  public void setStockList(List<String> list) {
    String[] result = new String[list.size()];
    result = list.toArray(result);
    finalPortList = result;
    comboboxPortlist3.removeAllItems();
    for (int i = 0; i < finalPortList.length; i++) {
      comboboxPortlist3.addItem(finalPortList[i]);
    }
    panel5.add(comboboxPortlist3);
  }

  /**
   * se the status.
   *
   * @param text text of status.
   */
  public void setStatusName(String text) {
    nameStatus.setText(text);
  }

  /**
   * clear the user input.
   */
  public void clearInputStringStock() {
    stockInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringShares() {
    sharesInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringName() {
    nameInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringPortName() {
    portNameInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringTotalValue() {
    errorDate.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringTV() {
    totalValueDisplay.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearInputStringStatus1() {
    addSuccess.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearStockInput() {
    stockInput2.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearShareInput() {
    sharesInput2.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearErrorCost() {
    errorMessageCost.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearCostDisplay() {
    costDisplay.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearCommissionRegular() {
    regularCommissionInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearAddDeleteRegular() {
    addDeleteDateInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearStartDate() {
    startDateInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearEndDate() {
    endDateInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearFrequency() {
    frequencyInput.setText("");
  }

  /**
   * clear the user input.
   */
  public void clearCom() {
    commissionFeeInput.setText("");
  }
}
