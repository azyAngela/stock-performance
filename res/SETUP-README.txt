1. All  libraries used:
- Swing, awt


2. How to run program from the JAR file:
Enter java -jar A6.jar

Instructions: In the new interface, portfolio is automatically selected as flexible portfolio, and you need to enter your name first before doing any action. Then there are 6 buttons on the side, click on each button will show its corresponding part.

 - Create new:
 1.enter portfolio name, stock, and shares as integer, and commission fee. Click the "add" button on the side, will enable you to add more stocks into this portfolio. If you do not need to add more stocks, just click Submit.

 - View existing:
 1. Existing portfolio under the name entered will be shown here, choose the portfolio you want to see the stock composition and total value. Click on the "View comp." and "Confirm" then you will be able to see the corresponding information.

 - Load file:
 1. enter the file name, then click "Load" to load the file, load status message will let you know if it's successfully loaded.

 - Add/delete from existing
 1. regular add/delete by share: choose the portfolio from the drop-down menu, choose the add/delete method(Regular - add or Regular - delete), enter stock, shares, commission fee, and date for regular add/delete, can do the operation on any specific date, can add/delete more than one stock, just continuously confirm and enter the stock information. when clicking "confirm add/delete", the stock will be automatically added/deleted based on what method you choose in the drop-down menu.
 2. By weight only: only enter total amount invest, stock(in drop down menu there are will have stocks that are in the portfolio selected), weight percent, commission fee, and date. while the investment amount is not fully distributed, click on the "add" button to continuously add stock and its percentage, and click on "confirm weight only" if weight add up to 1 and it finished the process.
 3. DCA strategy: Enter the information as instructions on "by weight only" method, except don't enter the date. Instead, you will enter the start date, end date, and frequency. then click on "Confirm add by DCA" when you are finished and weight add up to 1.

 - View cost basis:
 1. choose the portfolio name, enter the date you want to check for the cost basis, can be any date, and cost basis will show up when you click "confirm".

 - Create new(Dollar-cost averaging strategy)
 1. enter the portfolio name, total amount invest, stock, weight percentage, commission fee, start date, end date, and frequency. Click on "Add" will enable you to add more stocks and make the weight percentage count to 1. Click on "create portfolio" when you are all done.