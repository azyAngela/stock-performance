Design idea:

What's new:

1. Model

In assignment6, i changed the data type which store in single stock object, because we allow user to use double as number of shares in Dollar-cost Strategy or add stock by weight. But in simple add or sell stock action we still not allow user to buy fractional shares.

The core to finish Dollar-cost Strategy in our project is to scan a local file everytime when we start this program. Onece the program receive an action which will be done in the future date, it will record the information like startdate, enddate...etc  in a local file named Dollar-cost.txt.

And when program start A new method named scanLocal will scan this file and compare the localdate with the date in record, once eligible, it will autometicly finish add or sell action.

I add some methods in flexible Portfolio class to implement "add by wight" and "Dollar-cost Strategy". They are "addByWeight", "getUnitPrice", recordInvest".

Add some exceptions throws in addStock, deleStock..etc.
 
Change StockApi class to an interface, because we maybe be use another api next time.


2. View
I added another view class named JFrameView besides the View class that was implemented before for text-based interface. This class will support for GUI, JFrameView extends JFrame. In this view, I made 8 panels to display different kind of information, such as display cost basis, create portfolio, view existing, etc. In the class, I created many methods that will do the operations such as get the text from textInput field, or clear the input field, and so on.

3. Controller
Besides the previous written controller and its interface, I added another class called controllerNew that will handle the new GUI view and updated model. controllerNew implements ActionListener, and have a constructor that takes in the portfolioModel interface and JFrameView. In the actionPerformed main method, I put all the logic for display messages and panels when clicking each of the corresponding button.


A5:
Model:

I created an interface "StockModel" can represent stock, two classes "singleStock“ and "Portfolio" implement it.

I think a portfolio consists of several single stocks, so before creating a portfolio class, I need to build a singleStock class.

In singleStock class we can create an object represent a single stock, which has a stock symbol and number of shares of this stock.

In portfolio class we put single stocks' symbol and number of shares in a HashMap, use this map and portfolio name to build a portfolio object.

The API has a time restriction which executes 5 times per minute, so I design to download data to local, and do following steps locally.

There is a method called "setCsv" in singleStock which can download the stock information to our folder.

"getValue" method in "singleStock" class can read the data from local data file and return the value in certain date, so a portfolio's value is the sum of each single stock inside, and I use the closing price to calculate the total price in a day. 

I designed serval helper methods in the helper folder, we can use these methods easily to process data such as the file that users loads in; to check if the date entered is valid, check if the entered String represents an integer; the self-written json parser; make a folder in certain addresses; search all files; as well as using the API.






