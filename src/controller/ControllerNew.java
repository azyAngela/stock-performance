package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import helper.AutoInvest;
import helper.CheckDate;
import helper.LoadFile;
import helper.MakeFolder;
import helper.SearchFile;
import model.FlexiblePortfolio;
import model.Portfolio;
import model.PortfolioCreator;
import model.PortfolioModel;
import model.SingleStock;
import view.JFrameView;

/**
 * This class is the controller class for the new GUI view and updated model.
 */
public class ControllerNew implements ActionListener {
  private JFrameView view;
  Map<String, Double> finalMap;
  boolean haveCreated = false;
  boolean encounteredMap = false;
  boolean submitted = false;
  String address = "src/resource/stockData"; // singleStock stores here
  String address2 = "src/resource/UserData/"; // all portfolio names
  String address3 = "src/resource/UserData/";

  String address4 = "src/resource/UserData/"; // a copy of address 2
  String method;

  Double totalWeight = 0.0;

  /**
   * This is the GUI view's controller's constructor.
   *
   * @param m the portfolio model - interface
   * @param v GUI view
   */
  public ControllerNew(PortfolioModel m, JFrameView v) {
    PortfolioModel model = m;
    view = v;
    view.setListener(this);
    if (!encounteredMap) {
      finalMap = new HashMap<>();
    }
    //view.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    AutoInvest.scanLocal();
    switch (e.getActionCommand()) {
      case "create new":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay3();
        }
        break;

      case "submit":
        String stockName = view.getStock();
        String shares = view.getShares();
        double commissionFee = Double.parseDouble(view.getCommissionFee());
        SingleStock name = new SingleStock(stockName, address, address);
        if (Objects.equals(name.setCsv(), "invalid symbol")) {
          view.setStatusCreate("Stock symbol invalid!");
        } else if (!CheckDate.validInt(shares)) {
          view.setStatusCreate("Shares cannot be fractional! ");
        } else {
          finalMap.put(stockName, Double.parseDouble(shares));
          System.out.println(finalMap);
          view.clearInputStringStock();
          view.clearInputStringShares();
          // make the folder and create the txt files:
          MakeFolder.makeUser(view.getName());
          address2 = address2 + view.getName() + "/" + "portfolioData";
          address3 = address3 + view.getName() + "/" + "portfolioData/";
          if (Objects.equals(view.combobox(), "Flexible portfolio")) {
            PortfolioCreator newPort = new PortfolioCreator();
            FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(finalMap,
                    view.getPortName(), "flexible");
            fp.savePortfolio(address2);
            fp.initCost(view.getName(), commissionFee);
            view.clearInputStringName();
            view.clearInputStringShares();
            view.clearInputStringStock();
            view.clearInputStringPortName();
            view.clearInputStringStatus1();
            view.clearCom();
            address2 = address4;
            view.setStatusCreate("Saved successfully! ");
            submitted = true;
          }
        }
        break;

      case "Add more stock":
        encounteredMap = true;
        String stockName2 = view.getStock();
        String shares2 = view.getShares();
        SingleStock name2 = new SingleStock(stockName2, address, address);
        if (Objects.equals(name2.setCsv(), "invalid symbol")) {
          view.setStatusCreate("Stock symbol invalid!");
        } else if (!CheckDate.validInt(shares2)) {
          view.setStatusCreate("Shares cannot be fractional! ");
        } else {
          if (submitted) {
            finalMap = new HashMap<>();
          }
          finalMap.put(stockName2, Double.parseDouble(shares2));
          System.out.println(finalMap);
          view.setStatusCreate("Saved successfully! ");
          view.clearInputStringStock();
          view.clearInputStringShares();
          view.clearCom();
        }
        break;

      //等都写完了之后要加一下如果改变了名字再次点view existing的时候要重置
      // 展示的portfolio detail给他加成scroll的东西
      case "view existing":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay4();
          // 现在报错是因为如果输入的名字不在folder里，就会nullPointer， 所以要判断这个getName（）是不是
          // null还是是不是报错，try catch？
          address2 = address2 + view.getName() + "/" + "portfolioData/" + view.getPortName();
          System.out.println("after several another operation: " + address2);
          List<String> list = SearchFile.search(address2);
          view.setFinalPortList(list);
          address2 = address4;
          //这里用来show stock composition
        }
        break;

      case "Add/delete from existing":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay5();
          // 现在报错是因为如果输入的名字不在folder里，就会nullPointer， 所以要判断这个getName（）是不是
          // null还是是不是报错，try catch？
          address2 = address4;
          address2 = address2 + view.getName() + "/" + "portfolioData";
          List<String> list = SearchFile.search(address2);
          view.setFinalPortList(list);
          address2 = address4;
        }
        break;

      case "View cost basis":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay6();
          // 现在报错是因为如果输入的名字不在folder里，就会nullPointer， 所以要判断这个getName（）是不是
          // null还是是不是报错，try catch？
          address2 = address2 + view.getName() + "/" + "portfolioData";
          List<String> list = SearchFile.search(address2);
          view.setFinalPortList(list);
        }
        break;

      //setCost
      case "confirm date cost basis":
        if (CheckDate.validDate(view.getDateCost())) {
          String portAns = view.stockDisplay4();
          address3 = address4;
          address3 = address3 + view.getName() + "/" + "portfolioData/" + portAns;
          Map<String, Double> m = LoadFile.getInfoMap(address3);

          int index = portAns.indexOf("_");
          // after extraction of end part of portfolio name
          String singlePortName = portAns.substring(0, index);
          PortfolioCreator newPort = new PortfolioCreator();
          FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(m,
                  singlePortName, "flexible");
          double costBasis = fp.getCost(view.getDateCost(), view.getName());
          if (costBasis == -2.0) {
            view.setCostError("Date invalid!");
            view.clearCostDisplay();
          } else {
            view.setCostText("Cost basis: " + costBasis);
            view.clearErrorCost();
          }
        } else {
          view.setCostError("Date format error! Enter date in the form 2022-09-01.");
          view.clearCostDisplay();
        }
        address3 = address4;
        break;

      case "confirm existing":
        String portNameSelection = view.stockDisplay2();
        address3 = address4;
        address3 = address3 + view.getName() + "/" + "portfolioData/" + portNameSelection;
        System.out.println("for key set address: " + address3);
        Map<String, Double> map = LoadFile.getInfoMap(address3);
        List<String> keysOfMap = new ArrayList<>(map.keySet());
        System.out.println("map key set: " + keysOfMap);
        view.setStockList(keysOfMap);
        address3 = address4;
        break;

      case "confirm regular":
        method = view.addDeleteMethod();
        String portNameSelection2 = view.stockDisplay2();
        address3 = address3 + view.getName() + "/" + "portfolioData/" + portNameSelection2;
        Map<String, Double> map3 = LoadFile.getInfoMap(address3);
        int index = portNameSelection2.indexOf("_");
        String singlePortName = portNameSelection2.substring(0, index);
        PortfolioCreator newPort = new PortfolioCreator();
        FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(map3,
                singlePortName, "flexible");
        String stockRegular = view.getStockRegular();
        String shareRegular = view.getSharesRegular();
        Double numShares = Double.valueOf(shareRegular);
        String dateRegular = view.getAddDeleteDateInput();
        System.out.println(view.getCommissionFeeInput());
        System.out.println(Double.valueOf(view.getCommissionFeeInput()));
        Double commission = Double.valueOf(view.getCommissionFeeInput());
        if (Objects.equals(method, "Regular (by shares) - add")) {
          SingleStock createSingleStock = new SingleStock(stockRegular, address, address);
          if (Objects.equals(createSingleStock.setCsv(), "invalid symbol")) {
            view.setRegularError("Failed! Stock symbol is invalid!");
          } else if (!CheckDate.validInt(shareRegular)) {
            view.setRegularError("Failed! Shares cannot be fractional!");
          } else if (!CheckDate.validDate(dateRegular)) {
            view.setRegularError("Failed! Date invalid!");
          } else if (CheckDate.isPastDate(dateRegular)) {
            view.setRegularError("Failed! Date is a past date!");
          } else {
            String todayDate = String.valueOf(java.time.LocalDate.now());
            if (Objects.equals(dateRegular, todayDate)) {
              fp.addStock(stockRegular, numShares, view.getName());
              fp.updateCost(stockRegular, numShares, "add", view.getName(), commission);
              view.setRegularError("Added! You can add another.");
            } else {
              Map<String, Double> m1 = new HashMap<>(); // 先新建一个空的MAP，这个是为以后的按weight准备的，
              // 这个情况下用不到，所以是空的。
              Map<String, Double> m2 = new HashMap<>(); // 再建一个MAP把需要add或delete的symbol和share
              // 放进去
              m2.put(stockRegular, numShares);
              fp.recordInvest(view.getName(), 0.0, m1, m2, dateRegular, dateRegular,
                      0, "add", commission);
              view.setRegularError("Added! You can add another.");
            }
          }
        } else if (Objects.equals(method, "Regular (by shares) - delete")) {
          if (!CheckDate.validInt(shareRegular)) {
            view.setRegularError("Failed! Shares cannot be fractional!");
          } else if (!CheckDate.validDate(dateRegular)) {
            view.setRegularError("Failed! Date invalid!");
          } else if (CheckDate.isPastDate(dateRegular)) {
            view.setRegularError("Failed! Date is a past date!");
          } else {
            String todayDate = String.valueOf(java.time.LocalDate.now());
            if (Objects.equals(dateRegular, todayDate)) {
              int result = fp.deleteStock(stockRegular, Integer.parseInt(shareRegular),
                      view.getName());
              if (result == 1) {
                view.setRegularError("Failed! Not enough shares to be deleted!");
              } else if (result == 2) {
                view.setRegularError("Failed! Make sure stock is valid. ");
              } else {
                fp.updateCost(stockRegular, numShares, "delete", view.getName(), commission);
                view.setRegularError("Deleted! You can delete another.");
              }
            } else {
              Map<String, Double> m1 = new HashMap<>(); // 先新建一个空的MAP，这个是为以后的按weight准备的，
              // 这个情况下用不到，所以是空的。
              Map<String, Double> m2 = new HashMap<>(); // 再建一个MAP把需要add或delete的symbol和share
              // 放进去
              m2.put(stockRegular, numShares);
              fp.recordInvest(view.getName(), 0.0, m1, m2, dateRegular, dateRegular,
                      0, "delete", commission);
              view.setRegularError("Deleted! You can delete another.");
            }
          }
        }
        view.clearStockInput();
        view.clearShareInput();
        view.clearCommissionRegular();
        view.clearAddDeleteRegular();
        address3 = address4;
        break;

      case "confirm adding finally by weight only": // final confirm button
        method = view.addDeleteMethod();
        if (Objects.equals(method, "By weight only - add")) {
          if (totalWeight == 1.0) {
            view.setWeightError("Successfully saved! You are done.");
            totalWeight = 0.0;
            view.clear1();
          } else {
            view.setWeightError("Invest amount is not distributed well, try again!");
          }
        } else {
          view.setWeightError("Please choose DCA for method if that's you want.");
        }
        view.clear2();
        view.clear3();
        view.clear4();
        break;

      case "confirm adding finally by DCA":
        method = view.addDeleteMethod();
        if (Objects.equals(method, "Dollar-cost averaging strategy - add")) {
          if (totalWeight == 1.0) {
            view.setWeightError("Successfully saved! You are done.");
            totalWeight = 0.0;
            view.clear1();
          } else {
            view.setWeightError("Invest amount is not distributed well, try again!");
          }
        } else {
          view.setWeightError("Please choose Weight-only for method if that's you want.");
        }
        view.clear2();
        view.clear3();
        view.clear4();
        view.clearStartDate();
        view.clearEndDate();
        view.clearFrequency();
        break;

      case "Add weight only percent add": // 那个加号
        String portNameSelection3 = view.stockDisplay2();
        address3 = address3 + view.getName() + "/" + "portfolioData/" + portNameSelection3;
        Map<String, Double> map4 = LoadFile.getInfoMap(address3);
        int index2 = portNameSelection3.indexOf("_");
        String singlePortName2 = portNameSelection3.substring(0, index2);
        PortfolioCreator newPort2 = new PortfolioCreator();
        FlexiblePortfolio fp2 = (FlexiblePortfolio) newPort2.createPortfolio(map4,
                singlePortName2, "flexible");

        String amount = view.getAmountInvest();
        String stock = view.getStockSelection();
        String weight = view.getWeight();
        String commissionFees = view.getCommission();
        String date = view.getDateWeightOnly();
        String startDate = view.getStartDate();
        String endDate = view.getEndDate();
        String frequencyWeightOnly = view.getFrequency();
        System.out.println("result" + weight);
        totalWeight += Double.parseDouble(weight);
        String todayDate = String.valueOf(java.time.LocalDate.now());
        method = view.addDeleteMethod();
        if (Objects.equals(method, "By weight only - add")) {
          if (!CheckDate.validDate(date)) {
            view.setWeightError("Failed! Date invalid!");
            totalWeight -= Double.parseDouble(weight);
          } else if (CheckDate.isPastDate(date)) {
            view.setWeightError("Failed! Date is a past date!");
            totalWeight -= Double.parseDouble(weight);
          } else {
            if (Objects.equals(date, todayDate)) {
              if (totalWeight > 1.0) {
                view.setWeightError("Try again! Total weight is more than 1.");
                totalWeight -= Double.parseDouble(weight);
              } else if (totalWeight == 1.0) {
                double num = fp2.addByWeight(stock, Double.parseDouble(weight), view.getName(),
                        Double.parseDouble(amount));
                fp2.updateCost(stock, num, "add", view.getName(),
                        Double.parseDouble(commissionFees));
                view.setWeightError("Success! Click [Confirm weight only] to exit.");
                view.clear1();
              } else {
                double num = fp2.addByWeight(stock, Double.parseDouble(weight), view.getName(),
                        Double.parseDouble(amount));
                fp2.updateCost(stock, num, "add", view.getName(),
                        Double.parseDouble(commissionFees));
                view.setWeightError("Success! You need to split the rest weight to other stocks.");
              }
            } else {
              Map<String, Double> m3 = new HashMap<>(); // 建一个Map，把symbol和share放进去
              Map<String, Double> m4 = new HashMap<>();
              if (totalWeight > 1.0) {
                view.setWeightError("Try again! Total weight is more than 1.");
                totalWeight -= Double.parseDouble(weight);
              } else if (totalWeight == 1.0) {
                m3.put(stock, Double.parseDouble(weight));
                fp2.recordInvest(view.getName(), Double.parseDouble(amount), m3, m4, date,
                        date, 0, "add",
                        Double.parseDouble(commissionFees));
                view.setWeightError("Success! Click [Confirm weight only] to exit.");
                totalWeight = 0.0;
                view.clear1();
              } else {
                m3.put(stock, Double.parseDouble(weight));
                fp2.recordInvest(view.getName(), Double.parseDouble(amount), m3, m4, date,
                        date, 0, "add",
                        Double.parseDouble(commissionFees));
                view.setWeightError("Success! You need to split the rest weight to other stocks.");
              }
            }
          }
        } else if (Objects.equals(method, "Dollar-cost averaging strategy - add")) {
          Map<String, Double> m5 = new HashMap<>(); // 把所有weight都put进去
          Map<String, Double> m6 = new HashMap<>();
          if (totalWeight > 1.0) {
            view.setWeightError("Try again! Total weight is more than 1.");
            totalWeight -= Double.parseDouble(weight);
          } else if (totalWeight == 1.0) {
            m5.put(stock, Double.parseDouble(weight));
            if (endDate == null) {
              endDate = "5050-01-01";
            }
            fp2.recordInvest(view.getName(), Double.parseDouble(amount), m5, m6, startDate,
                    endDate, Integer.parseInt(frequencyWeightOnly), "add",
                    Double.parseDouble(commissionFees));
            view.setWeightError("Success! Click [Confirm add by DCA] to exit.");
            view.clear1();
          } else {
            m5.put(stock, Double.parseDouble(weight));
            if (endDate == null) {
              endDate = "5050-01-01";
            }
            fp2.recordInvest(view.getName(), Double.parseDouble(amount), m5, m6, startDate,
                    endDate, Integer.parseInt(frequencyWeightOnly), "add",
                    Double.parseDouble(commissionFees));
            view.setWeightError("Success! You need to split the rest weight to other stocks.");
          }
        }
        view.clear2();
        view.clear3();
        view.clear4();
        view.clearStartDate();
        view.clearEndDate();
        view.clearFrequency();
        address3 = address4;
        break;

      case "dollar cost strategy":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay7();
        }
        break;

      case "Log in":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          MakeFolder.makeUser(view.getName());
          view.setStatusName("Log in successful!");
        }
        break;

      case "Add more DCA":
        String pName = view.getPortNameDCA();
        String s = view.getS();
        String com = view.getCommissionNew();
        String w = view.getW();

        String tAmount = view.getTotalAmount();
        String startD = view.getStartDateNew();
        String endD = view.getEndDateNew();
        String fre = view.getFreqNew();

        PortfolioCreator newP1 = new PortfolioCreator();
        FlexiblePortfolio p5 = (FlexiblePortfolio) newP1.createPortfolio(finalMap,
                pName, "flexible");

        SingleStock stockCreation = new SingleStock(s, address, address);
        if (Objects.equals(stockCreation.setCsv(), "invalid symbol")) {
          view.setErrorDCA("Stock symbol invalid!");
        } else if (!Objects.equals(endD, "") && !CheckDate.validDate(endD)) {
          view.setErrorDCA("Failed! Date invalid!");
          totalWeight -= Double.parseDouble(w);
        } else {
          finalMap.put(s, 0.0);
          if (!haveCreated) {
            MakeFolder.makeUser(view.getName());
            address2 = address4;
            address2 = address2 + view.getName() + "/" + "portfolioData";
            address3 = address3 + view.getName() + "/" + "portfolioData/";
            p5 = (FlexiblePortfolio) newP1.createPortfolio(finalMap,
                    pName, "flexible");
            p5.savePortfolio(address2);
            p5.initCost(view.getName(), Double.parseDouble(com));
            haveCreated = true;
          }
          totalWeight += Double.parseDouble(w);
          view.setErrorDCA("Stock added! Can add another.");
          Map<String, Double> m5 = new HashMap<>(); // 把所有weight都put进去
          Map<String, Double> m6 = new HashMap<>();
          if (totalWeight > 1.0) {
            view.setErrorDCA("Try again! Total weight is more than 1.");
            totalWeight -= Double.parseDouble(w);
          } else if (totalWeight == 1.0) {
            m5.put(s, Double.parseDouble(w));
            if (endD == null) {
              endD = "5050-01-01";
            }
            p5.recordInvest(view.getName(), Double.parseDouble(tAmount), m5, m6, startD,
                    endD, Integer.parseInt(fre), "add",
                    Double.parseDouble(com));
            view.setErrorDCA("Success! Click [Create portfolio] to exit.");
          } else {
            m5.put(s, Double.parseDouble(w));
            if (endD == null) {
              endD = "5050-01-01";
            }
            p5.recordInvest(view.getName(), Double.parseDouble(tAmount), m5, m6, startD,
                    endD, Integer.parseInt(fre), "add",
                    Double.parseDouble(com));
            view.setErrorDCA("Success! You need to split the rest weight to other stocks.");
          }
          address2 = address4;
          submitted = true;
          //view.clear11();
          view.clear12();
          view.clear13();
          view.clear14();
          view.clear15();
          view.clear16();
          view.clear17();
          //view.clear18();
        }
        break;

      case "Create portfolio by DCA":
        if (totalWeight == 1.0) {
          view.setErrorDCA("Successfully saved! You are done.");
          totalWeight = 0.0;
          view.clear1();
        } else {
          view.setErrorDCA("Invest amount is not distributed well, try again!");
        }
        break;

      case "View comp":
        String selectPortName = view.stockDisplay();
        address3 = address4;
        address3 = address3 + view.getName() + "/" + "portfolioData/" + selectPortName;
        String stockComp = LoadFile.getInfo(address3);
        //System.out.println("view comp：" + );
        view.setStockComp(stockComp);
        address2 = address4;
        address3 = address4;
        break;

      case "load file":
        if (Objects.equals(view.getName(), "")) {
          view.setStatusName("Name cannot be empty!");
        } else {
          view.createNewDisplay8();
        }
        break;

      case "load action btn":
        String fileName = view.getLoad();
        String[] result = fileName.split("\\.");
        String[] result2 = result[0].split("/");
        String r2 = result2[result2.length - 1];
        System.out.println("fileName is " + fileName);
        if (LoadFile.checkFile(fileName) == 1) {
          view.setLoadMsg("Invalid file format! Try again.");
        } else {
          LoadFile.duplicateFile(fileName, view.getName(), r2);
          view.setLoadMsg("Load success!");
        }
        break;

      case "confirm":
        if (CheckDate.validDate(view.getDate())) {
          String answerPortName = view.stockDisplay();
          address3 = address4;
          address3 = address3 + view.getName() + "/" + "portfolioData/" + answerPortName;
          System.out.println("result" + address3);
          Map<String, Double> m = LoadFile.getInfoMap(address3);
          // create a flexible portfolio
          int index3 = answerPortName.indexOf("_");
          // after extraction of end part of portfolio name
          String singlePortName3 = answerPortName.substring(0, index3);
          PortfolioCreator newPort3 = new PortfolioCreator();

          FlexiblePortfolio fp3 = (FlexiblePortfolio) newPort3.createPortfolio(m,
                  singlePortName3, "flexible");
          fp3.setMap(m);
          fp3.setName(singlePortName3);

          Portfolio ip = (Portfolio) newPort3.createPortfolio(m,
                  singlePortName3, "inflexible");
          ip.setMap(m);
          ip.setName(singlePortName3);
          String value = String.valueOf(fp3.getValueNew(view.getDate(), view.getName()));
          //String value2 = String.valueOf(ip.getValue(view.getDate(), address));
          if (CheckDate.validDate(view.getDate())) {
            if (Objects.equals(value, "-1.0")
                    || Objects.equals(value, "-2.0") || Objects.equals(value, "-3.0")) {
              view.showErrorDate("Date is invalid!");
              view.clearInputStringTV();
            } else {
              String r = ip.getValue(view.getDate(), address);
              if (Objects.equals(r, "-1.0") || Objects.equals(r, "-2.0")
                      || Objects.equals(r, "-3.0")) {
                r = String.valueOf(0.0);
              }
              view.setDateDisplay("Flexible:\n" + "\n" + "Portfolio name: " + answerPortName + "\n"
                      + "Date chosen: " + view.getDate() + "\n" + "Total value: "
                      + fp3.getValueNew(view.getDate(),
                      view.getName()) + "\n" + "\n" + "Inflexible:\n" + "\n" + "Portfolio name: "
                      + answerPortName + "\n"
                      + "Date chosen: " + view.getDate() + "\n" + "Total value: "
                      + r);
              view.clearInputStringTotalValue();

            }
            address3 = address4;
          } else {
            view.showErrorDate("Enter date in format 2022-01-01.");
            //view.clearInputStringTV();
          }
        }
        break;

      default:
        view.setStatusName("");
        break;
    }
  }

}
