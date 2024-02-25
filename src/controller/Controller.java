package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import helper.CheckDate;
import helper.LoadFile;
import helper.MakeFolder;
import helper.SearchFile;
import model.Portfolio;
import model.PortfolioCreator;
import model.SingleStock;
import model.FlexiblePortfolio;
import view.IView;

/**
 * this is a class represent Controller.
 */
public class Controller implements IController {
  private Scanner in;

  private IView view;

  private Portfolio portfolio;
  //private PrintStream output;


  String address = "src/resource/stockData"; // singleStock stores here

  String address2 = "src/resource/UserData/"; // all portfolio names are stored inside this address

  String address3 = "src/resource/UserData/"; // data stored under user's name

  String address4 = "src/resource/UserData/"; // copy of address 3 to deal with A

  public String name;

  Map<String, Double> finalMap;

  /**
   * constructor.
   *
   * @param portfolio portfolio
   * @param in        InputStream
   * @param view      IView
   * @param out       PrintStream
   */
  public Controller(Portfolio portfolio, InputStream in, IView view, PrintStream out) {
    this.portfolio = portfolio;
    this.view = view;
    this.in = new Scanner(in);
    PrintStream output;
    output = out;
  }

  @Override
  public void goMain() {
    boolean quit = false;
    view.enterFI();
    String answerFI = this.in.next();
    switch (answerFI) {
      // if choose inflexible
      case "B":
        goHelper("inflexible");
        break;
      case "A":
        goHelper("flexible");
        break;
      case "Q":
        break;
      default:
        view.showOptionError();
        break;
    }
  }

  /**
   * A helper method for goMain that will identify types of styles(inflexible, flexible).
   *
   * @param style style
   */
  private void goHelper(String style) {
    PortfolioCreator p = new PortfolioCreator();
    boolean quit = false;
    view.showName();
    String inputName = this.in.next();
    if (Objects.equals(inputName, "Q")) {
      quit = true;
    } else {
      name = inputName;
      MakeFolder.makeUser(inputName);
      address2 = address2 + inputName + "/" + "portfolioData";
      address3 = address3 + inputName + "/" + "portfolioData/";
      address4 = address3;
      while (!quit) {
        view.showOptions();
        String option = in.next();
        switch (option) {
          case "A": // if "view existing"
            boolean indicator = false;
            boolean indicator2 = false;
            //boolean indicator3 = false;
            boolean rightPortAnswer = false;
            while (!rightPortAnswer) {
              view.showViewName(inputName);
              view.showPortfolioList(SearchFile.search(address2));
              view.showPortfolioQuestion();
              String answerPortName = this.in.next();
              if (Objects.equals(answerPortName, "Q")) {
                quit = true;
                break;
              } else {
                //while (!rightPortAnswer) {
                if (!SearchFile.search(address2).contains(answerPortName)) {
                  view.showPortError();
                } else {
                  // if the port name is right
                  rightPortAnswer = true;
                  address3 = address3 + answerPortName;
                  view.showStockComposition(); // make getInfo return String!!
                  String info = LoadFile.getInfo(address3);
                  System.out.println(info);
                  while (!indicator2) {
                    view.showDateQuestion();
                    String answerDate = this.in.next();
                    if (Objects.equals(answerDate, "Q")) {
                      quit = true;
                      break;
                    } else {

                      if (CheckDate.validDate(answerDate)) {
                        Map<String, Double> m = LoadFile.getInfoMap(address3);
                        portfolio.setMap(m);
                        portfolio.setName(inputName);
                        if (portfolio.getValue(answerDate, address).equals("-1.0")
                                || portfolio.getValue(answerDate, address).equals("-2.0")
                                || portfolio.getValue(answerDate, address).equals("-3.0")) {
                          view.showDateInvalidError2();
                        } else {
                          if (Objects.equals(style, "flexible")) {
                            Map<String, Double> map = LoadFile.getInfoMap(address3);
                            int index = answerPortName.indexOf("_");
                            // after extraction of end part of portfolio name
                            String singlePortName = answerPortName.substring(0, index);
                            PortfolioCreator newPort = new PortfolioCreator();
                            FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(map,
                                    singlePortName, style);
                            view.noteFlex();
                            view.showPortfolioName(answerPortName);
                            view.showDateChosen(answerDate);
                            view.showDTotalValue(String.valueOf(fp.getValueNew(answerDate,
                                    inputName)));
                            view.noteInflexible();
                            view.showPortfolioName(answerPortName);
                            view.showDateChosen(answerDate);
                            view.showDTotalValue(this.portfolio.getValue(answerDate, address));
                            indicator2 = true;
                          } else if (Objects.equals(style, "inflexible")) {
                            view.showPortfolioName(answerPortName);
                            view.showDateChosen(answerDate);
                            view.showDTotalValue(this.portfolio.getValue(answerDate, address));
                            indicator2 = true;
                          }
                        }
                      } else {
                        view.showDateInvalidError();
                      }
                    }
                  }
                }
              }
              address3 = address4;
            }
            break;

          case "B":
            boolean rightAnswer = false;
            //boolean getToShared = false;
            Map<String, Double> map = new HashMap<>();
            finalMap = map;
            view.createPortName();
            String portName = this.in.next();
            if (Objects.equals(portName, "Q")) {
              quit = true;
              break;
            } else {
              while (!rightAnswer) {
                view.addStock();
                String answerStockName = this.in.next();
                SingleStock name = new SingleStock(answerStockName, address, address);
                //System.out.println(name.setCsv());
                if (Objects.equals(answerStockName, "Q")) {
                  quit = true;
                  break;
                } else if (Objects.equals(name.setCsv(), "invalid symbol")) {
                  view.showStockLabelInvalidError();
                } else {
                  view.addShares();
                  String answerShares = this.in.next();
                  if (Objects.equals(portName, "Q")) {
                    quit = true;
                    break;
                  } else {
                    if (!CheckDate.validInt(answerShares)) {
                      view.showSharesError();
                    } else {
                      view.addAnotherStock();
                      String answerYN = this.in.next();
                      if (Objects.equals(answerYN, "N")) {
                        view.saveToFile();
                        rightAnswer = true;
                      } else if (Objects.equals(answerYN, "Q")) {
                        quit = true;
                        break;
                      }
                      map.put(answerStockName, Double.valueOf(answerShares));
                    }
                  }
                }
              }
            }
            if (Objects.equals(style, "flexible")) {
              FlexiblePortfolio fp = (FlexiblePortfolio) p.createPortfolio(map, portName, style);
              fp.savePortfolio(address2);
              fp.initCost(inputName, 0.02);
            } else if (Objects.equals(style, "inflexible")) {
              Portfolio ip = (Portfolio) p.createPortfolio(map, portName, style);
              ip.savePortfolio(address2);
            }
            break;

          case "C":
            boolean right = false;
            while (!right) {
              view.loadFile();
              String answerFile = this.in.next();
              String[] result = answerFile.split("\\.");
              String[] result2 = result[0].split("/");
              String r = result2[result2.length - 1];
              if (Objects.equals(answerFile, "Q")) {
                quit = true;
                break;
              } else {
                if (LoadFile.checkFile(answerFile) == 1) {
                  view.showInvalidFileFormatError();
                } else {
                  LoadFile.duplicateFile(answerFile, inputName, r);
                  view.loadSuccess();
                  right = true;
                }
              }
            }
            break;

          case "D":
            boolean rightAnswer3 = false;
            boolean rightAnswer4 = false;
            boolean indi1 = false;
            if (Objects.equals(style, "inflexible")) {
              view.errorFirstQn();
            } else if (Objects.equals(style, "flexible")) {
              view.showPortfolioList(SearchFile.search(address2));
              view.dPortOperation();
              String portNameAns = this.in.next();
              //System.out.println("address 3 before adding portName: " + address3);
              if (Objects.equals(portNameAns, "Q")) {
                quit = true;
                break;
              } else {
                if (!SearchFile.search(address2).contains(portNameAns)) {
                  view.showPortError();
                } else {
                  address3 = address3 + portNameAns;
                  Map<String, Double> map2 = LoadFile.getInfoMap(address3);
                  //address3 = address4;
                  int index = portNameAns.indexOf("_");
                  // after extraction of end part of portfolio name
                  String singlePortName = portNameAns.substring(0, index);
                  PortfolioCreator newPort = new PortfolioCreator();
                  FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(map2,
                          singlePortName, style);
                  while (!indi1) {
                    view.deleteOrAdd();
                    String ans = this.in.next();
                    if (Objects.equals(ans, "ADD")) {
                      indi1 = true;
                      // add all stocks to the portfolio
                      while (!rightAnswer3) {
                        view.addStock();
                        String answerStockName = this.in.next();
                        SingleStock name = new SingleStock(answerStockName, address, address);
                        //System.out.println(name.setCsv());
                        if (Objects.equals(answerStockName, "Q")) {
                          quit = true;
                          break;
                        } else if (Objects.equals(name.setCsv(), "invalid symbol")) {
                          view.showStockLabelInvalidError();
                        } else {
                          view.addShares();
                          String answerShares = this.in.next();
                          if (Objects.equals(answerShares, "Q")) {
                            quit = true;
                            break;
                          } else {
                            if (!CheckDate.validInt(answerShares)) {
                              view.showSharesError();
                            } else {
                              Double numShares = Double.parseDouble(answerShares);
                              //System.out.println(address3);
                              //int end = address3.indexOf("_");
                              //address3 = address3.substring(0, address3.length()-14);
                              fp.addStock(answerStockName, numShares, inputName);
                              fp.updateCost(answerStockName, numShares, "add", inputName, 0.02);
                              view.addAnotherStock();
                              String answerYN = this.in.next();
                              if (Objects.equals(answerYN, "N")) {
                                view.saveToFile();
                                rightAnswer3 = true;
                              } else if (Objects.equals(answerYN, "Q")) {
                                quit = true;
                                break;
                              } else if (Objects.equals(answerYN, "Y")) {
                                // if user chooses Y, just go ask again what stock want to add.
                              } else if (!Objects.equals(answerYN, "Y") || !Objects.equals(
                                      answerYN, "N") || !Objects.equals(answerYN, "Q")) {
                                break;
                              }
                            }
                          }
                        }
                        address3 = address4;
                      }
                      // if user want to delete a stock
                    } else if (Objects.equals(ans, "DE")) {
                      while (!rightAnswer4) {
                        view.dStockDelete();
                        String answerStock = this.in.next();
                        //SingleStock name = new SingleStock(answerStockName, address, address);
                        //System.out.println(name.setCsv());
                        if (Objects.equals(answerStock, "Q")) {
                          quit = true;
                          break;
                        } else {
                          view.deleteShares();
                          String answerShares = this.in.next();
                          if (Objects.equals(answerShares, "Q")) {
                            quit = true;
                            break;
                          } else {
                            if (!CheckDate.validInt(answerShares)) {
                              view.showSharesError();
                            } else {
                              int result = fp.deleteStock(answerStock,
                                      Double.parseDouble(answerShares),
                                      inputName);
                              if (result == 1) {
                                view.deleteSharesInsufficient();
                              } else if (result == 2) {
                                view.showDeleteError();
                              } else {
                                fp.updateCost(answerStock, Double.parseDouble(answerShares),
                                        "delete", inputName, 0.02);
                                view.deleteAnotherStock();
                                String answerYN = this.in.next();
                                if (Objects.equals(answerYN, "N")) {
                                  rightAnswer4 = true;
                                  view.deleteSuccess();
                                } else if (Objects.equals(answerYN, "Q")) {
                                  quit = true;
                                  break;
                                }
                              }
                            }
                          }
                        }
                        address3 = address4;
                      }
                      indi1 = true;
                      break;
                    } else if (Objects.equals(ans, "Q")) {
                      quit = true;
                      break;
                    } else {
                      view.errorAddDeleteNone();
                    }
                  }
                }
              }
            } // END THIS ELSE STATEMENT
            break;

          case "E":
            boolean rightBasis = false;
            if (Objects.equals(style, "inflexible")) {
              view.errorFirstQn();
            } else if (Objects.equals(style, "flexible")) {
              view.showPortfolioList(SearchFile.search(address2));
              view.dPortOperation();
              String portNameAns = this.in.next();
              //System.out.println("address 3 before adding portName: " + address3);
              address3 = address3 + portNameAns;
              if (Objects.equals(portNameAns, "Q")) {
                quit = true;
                break;
              } else {
                if (!SearchFile.search(address2).contains(portNameAns)) {
                  view.showPortError();
                } else {
                  while (!rightBasis) {
                    view.eCostBasis();
                    String dateCost = this.in.next();
                    if (Objects.equals(dateCost, "Q")) {
                      quit = true;
                      break;
                    } else {
                      if (CheckDate.validDate(dateCost)) {
                        Map<String, Double> m = LoadFile.getInfoMap(address3);

                        int index = portNameAns.indexOf("_");
                        // after extraction of end part of portfolio name
                        String singlePortName = portNameAns.substring(0, index);
                        PortfolioCreator newPort = new PortfolioCreator();
                        FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(m,
                                singlePortName, style);
                        double costBasis = fp.getCost(dateCost, inputName);
                        if (costBasis == -2.0) {
                          view.showDateInvalidError2();
                        } else {
                          // call the get method to print out cost details!!!!
                          //double costBasis = fp.getCost(dateCost, inputName);
                          view.costBasisDisplay(costBasis);
                          //System.out.println("return value: " +  fp.getValue(dateCost, address));
                          rightBasis = true;
                        }
                      } else {
                        view.showDateInvalidError();
                      }
                    }
                  }
                  address3 = address4;
                }
              }
            }
            break;


          case "F":
            boolean rightPorAnswer = false;
            boolean rightStartDate = false;
            boolean rightEndDate = false;
            if (Objects.equals(style, "inflexible")) {
              view.errorFirstQn();
            } else if (Objects.equals(style, "flexible")) {
              while (!rightPorAnswer) {
                view.showPortfolioList(SearchFile.search(address2));
                view.fViewPerformanceQn();
                String portNamePerformance = this.in.next();
                address3 = address3 + portNamePerformance;
                if (Objects.equals(portNamePerformance, "Q")) {
                  quit = true;
                  break;
                } else {
                  if (!SearchFile.search(address2).contains(portNamePerformance)) {
                    view.showPortError();
                  } else {
                    rightPorAnswer = true;
                    while (!rightStartDate) {
                      view.fTimeRangeStart();
                      String startDate = this.in.next();
                      if (Objects.equals(startDate, "Q")) {
                        quit = true;
                        break;
                      } else {
                        if (CheckDate.validDate(startDate)) {
                          rightStartDate = true;
                          Map<String, Double> m = LoadFile.getInfoMap(address3);
                          int index = portNamePerformance.indexOf("_");
                          // after extraction of end part of portfolio name
                          String singlePortName = portNamePerformance.substring(0, index);
                          PortfolioCreator newPort = new PortfolioCreator();
                          FlexiblePortfolio fp = (FlexiblePortfolio) newPort.createPortfolio(m,
                                  singlePortName, style);

                          while (!rightEndDate) {
                            view.fTimeRangeEnd();
                            String endDate = this.in.next();
                            // 需要加个如果等于Q怎么办
                            if (CheckDate.validDate(endDate)) {
                              rightEndDate = true;
                              int answer = fp.drawChart(startDate, endDate, inputName);
                              if (answer == -2) {
                                view.errorDate1();
                              } else if (answer == -3) {
                                view.errorDate2();
                              } else if (answer == -1) {
                                view.errorDate3();
                              } else {
                                System.out.println("Successfully created the chart!");
                              }
                            } else {
                              view.showDateInvalidError();
                            }
                          }
                        } else {
                          view.showDateInvalidError();
                        }
                      }
                    }
                  }
                }
                address3 = address4;
              }
            }
            break;


          case "Q":
            quit = true;
            break;

          default:
            view.showOptionError();
            break;
        }
      }
    }
  }
}
