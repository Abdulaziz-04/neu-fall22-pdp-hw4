# Setup

This is the SETUP-README for our project.

note: Portfolio file will be save to the base location that you run cmd, not the location of JAR file. For example if you run cmd with

> C:\Users\Myok>java -jar C:\Users\Myok\Desktop\neu-fall22-pdp-hw4\res\neu-fall22-pdp-hw4.jar

The program will read / write file from C:\Users\Myok

## Using the program

The procedure to use this program: (input exit will exit the program)

1. Go to the directory which contains neu-fall22-pdp-hw4.jar (in res folder).

2. Execute the neu-fall22-pdp-hw4.jar file.

3. If user want to excute text-based, execute jar file with "cli" argument

> java -jar neu-fall22-pdp-hw4.jar cli

## GUI:

### Main Page:

1. After execute jar file, user will see the main page GUI.

2. There are three buttons : create a flexible portfolio; load portfolio; Create dollar cost
   average portfolio. User can click this three button to different page.

#### Option 1: Flexible Create Page
The user will see flexible create page GUI. (Back button is on the top of left, it will back
main page after user clicks it)

1. There is a checkbox for commission fee. User can click it to choose it will have or not have
   commission fee for this transaction. If it has transaction fee, user need to input that fee
   in text field. If not, it will not show the text field.

2. In the text filed labeled "Date for transaction", user inputs the date in given format.(If
   the format is error, it will show it.)

3. In the text filed labeled "Stock Symbol", user input the symbol of stock. (If the input is
   negative and not an integer, it will show error.)

4. In the text filed labeled "Share of Stock", User inputs a shares of stocks.(If the input is
   negative and not an integer, it will show error.)

5. User clicks the "BUY" or "SELL" button to buy or sell stock. (The transactions will show in
   Table)

6. If user want to add more transactions, do 1-5 procedures repeatedly. Otherwise, user clicks
   the finish button to finish adding transactions.

7. Then user will see the text filed labeled "Name of Portfolio", user inputs the name of
   this portfolio.

8. User click the "Create and Save to file" button, it will save successfully if there is
   not any error. And then it will automatically go to the load page.

#### Option 2: Load Page

User can see Load page. (Back button is on the top of left, it will back
main page after user clicks it)

1. There is a text filed labeled "Name of Portfolio", user inputs the name of a portfolio.

2. User click the load button. If it exists, user can see the composition of it in first table
   and the investment in another table. Otherwise, show the error message to user.

3. The second table, it will have a view button, it can view the information detail of strategy
   in it. The information of strategy page can only see from here.

4. For existing portfolio, there are five buttons for user:
   1. Show composition, value, cost of basis for specific date -- go to determine page
   2. Show performance of this portfolio -- go to performance page
   3. Buy or sell stocks (modify portfolio) -- go to create page to modify the transaction
   4. Add new strategy to portfolio -- go to schedule create page(Add New Strategy to Existing
      Portfolio) to add new strategies
   5. Buy stock by weight strategy on a day for this portfolio -- go to one time strategy page
      (Specific Date Strategy)

##### Option 1 for Load Page: Determine Page

User can see the determine page. (It will show the value and cost of basis together. Back button
is on the top of left, it will back load page after user clicks it)

1. User inputs the date that they want to determine in giving format in text field. The format is
   yyyy-MM-dd ex."2022-10-11". User can see the error message when the format is error or if there is
   price available for that day. Otherwise, it will show the total value of the portfolio and
   the cost of basis on that day.

2. User can repeatedly enter the different date to see portfolio value.

3. If user click "back" button, user will be directed back to the load page.

##### Option 2 for Load Page: Performance Page

User can see the performance page. (Back button is on the top of left, it will back load page
after user clicks it)

1. User inputs the start date of the timespan and end date of timespan in the text filed that
   labeled "Start Date" and "End date". It will show the error message if there is an error.

2. User clicks "show performance" button, the user can see the performance in this timespan.

3. User can repeatedly input different start date and end date to perform.


##### Option 3 for Load Page: Flexible Create Page to modify it.(buy or sell stocks)

User will see the flexible create page (it actually the modify page) again to buy or sell the
stocks. (Back button is on the top of left, it will back load page after user clicks it)

1. There is a checkbox for commission fee. User can click it to choose it will have or not have
   commission fee for this transaction. If it has transaction fee, user need to input that fee
   in text field. If not, it will not show the text field.

2. In the text filed labeled "Date for transaction", user inputs the date in given format.(If
   the format is error, it will show it.)

3. In the text filed labeled "Stock Symbol", user input the symbol of stock. (If the input is
   negative and not an integer, it will show error.)

4. In the text filed labeled "Share of Stock", User inputs a shares of stocks.(If the input is
   negative and not an integer, it will show error.)

5. User clicks the "BUY" or "SELL" button to buy or sell stock. (The transactions will show in
   Table)

6. If user want to add more transactions, do 1-5 procedures repeatedly. Otherwise, user clicks
   the finish button to finish adding transactions. (After click finish button, it will
   automatically back to the load page)

##### Option 4 for Load Page: Scheduled Create Page to add new strategy to portfolio

User will see the Scheduled Create Page (it actually the add new strategy to portfolio page) again
to add new strategy to portfolio. (Back button is on the top of left, it will back load page
after user clicks it)

1. In the text filed labeled "Stock Symbol", user input the symbol of stock. (If the input is
   negative and not an integer, it will show error.)

2. In the text filed labeled "Weight", user inputs the weight of this stock, which can to be
   integer or double.

3. User click "Add" button to add the symbol and weight.(The symbol and weight will show in table)

4. If user want to add more symbol and weight, do 1-3 procedures repeatedly. Otherwise, user clicks
   the finish button to finish adding this strategy.

5. Then, User need to input the name for current portfolio in labeled "Name" text filed.

6. User input the investment amount for this strategy in labeled "Amount" text filed.

7. User input the start date for this strategy in labeled "Start Date" text filed.

8. User input the end date for this strategy in labeled "End Date" text filed. (If not have end
   date, did not input anything)

9. User input the commission fee for this strategy in labeled "Commission fee" text filed.

10. User click the "Create & Save to file" button to finish modify. (After click this button, it
    will automatically back to the load page (if there is not any error, the new strategy will
    show on load page)


##### Option 5 for Load Page: one time strategy page

User will see the one time strategy page (it actually add new strategy to portfolio on a
specific date. Back button is on the top of left, it will back load page
after user clicks it).

1. In the text filed labeled "Stock Symbol", user input the symbol of stock. (If the input is
   negative and not an integer, it will show error.)

2. In the text filed labeled "Weight", user inputs the weight of this stock, which can to be
   integer or double.

3. User click "Add" button to add the symbol and weight.(The symbol and weight will show in table)

4. If user want to add more symbol and weight, do 1-3 procedures repeatedly. Otherwise, user clicks
   the finish button to finish adding this strategy.

5. User input the investment amount for this strategy in labeled "Amount" text filed.

6. User input the transaction  date for this strategy in labeled "Transaction Date" text filed.

7. User input the commission fee for this strategy in labeled "Commission fee" test filed.

8. User click the "Create & Save to file" button to finish modify. (After click this button, it
   will automatically back to the load page, the new one will show on first table of load
   page)

#### Option 3: Scheduled Create Page -- Create dollar cost average portfolio Page
User will see the Scheduled Create Page. (Back button is on the top of left, it will back main page
after user clicks it)

1. In the text filed labeled "Stock Symbol", user input the symbol of stock. (If the input is
   negative and not an integer, it will show error.)

2. In the text filed labeled "Weight", user inputs the weight of this stock, which can to be
   integer or double.

3. User click "Add" button to add the symbol and weight.(The symbol and weight will show in table)

4. If user want to add more symbol and weight, do 1-3 procedures repeatedly. Otherwise, user clicks
   the finish button to finish adding this strategy.

5. Then, user need to input the name for current portfolio in labeled "Name" text filed.

6. User input the investment amount for this strategy in labeled "Amount" text filed.

7. User input the start date for this strategy in labeled "Start Date" text filed.

8. User input the end date for this strategy in labeled "End Date" text filed.(If not have end
   date, did not input anything)

9. User input the commission fee for this strategy in labeled "Commission fee" text filed.

10. User click the "Create & Save to file" button to finish modify. (After click this button, it
    will automatically back to the load page, the new strategy will show on load page)

11. If there is something error, it will show on the screen.






## Text-based:

### Main Page:

1. After execute jar file, user will see the main menu screen.

2. Users can choose "1", "2" or "3. If user inputs "1", user will go to the inflexible create page.
   If user inputs "2", user will go to the flexible create page. If user input "3", user will go
   to the load page. (If user wants to determine a portfolio value, the cost of basis, show the
   performance of a portfolio, user need to go to load page first. If the number that user
   inputted is not "1", "2", or "3" the program will show the error message to user and user
   need to input again).

#### Option 1: Inflexible Create Page
The user will see inflexible create page.

1. User can input the symbol of a stock and the amount, which is number of shares, in the giving format. The format is (symbol,shares) ex. AA,100 (All the letter need to be in uppercase). If user inputs is not correct, user can see different error messages for why the input is not correct. Otherwise, the user can see the stocks and shares which were inputted before.

2. User can input next stock and shares.

3. If user want to finish input the stock into the portfolio. Please input "end"(in lowercase). And then user can see the portfolio composition and will be asked to input the name of the portfolio.

4. User inputs the name of this portfolio. If the name is valid, it will create the portfolio successfully. Otherwise, it will fail to create. (At this step, it will save the portfolio as a file. The portfolio name have some restrictions, which mentioned in program).

5. After that, user will automatically go to the load page.

#### Option 2: Flexible Create Page
The user will see flexible create page.

1. User inputs the date to do the transaction. The format is "2022-10-10". Other input will
   be error which will show in the end.(If the format is error, it will show it.)

2. User inputs a symbol of the stock that they want to do this transaction. (If there is any
   error,it will show it.)

3. User inputs the date to the type of transaction: "BUY" or "SELL".

4. User inputs a shares of stocks.(If the input is negative and not an integer, it will show error.)

5. User inputs the commission fee for this transaction, which needs to be double format.(If it
   is not a double, it will show error)

6. User can choose to input next stock transaction or not. If yes, enter yes. Other input will
   end of input transaction.

7. If user chose yes before do 1-5 again. If user chose no before and the user can input the
   portfolio name. (If the name is valid, it will create the portfolio successfully and go to
   the load page automatically. Otherwise, user need to input name again.)

8. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the
   main page.

#### Option 3: Load Page
User can see Load page.

1. User inputs the name of a portfolio. If it exists, user can see the composition. Otherwise, show the error message to user.

2. For existing portfolio, there are three options for user:
   1. Determine the value and cost of basis.
   2. Show the performance of the portfolio.
   3. Modify the portfolio (Buy or sell the stock for this portfolio) --This option only show if
      the portfolio is flexible.

   User inputs "1", it will go to determine page. User input "2", it will go to performance page.
   User inputs "3", it will go to create page to modify the transaction. (after modify, it will  
   automatically go back to the load page)

3. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the main page.

##### Option 1 for Load Page: Determine Page
User can see the determine page. (It will show the value and cost of basis together)

1. User inputs the date that they want to determine in giving format. The format is yyyy-MM-dd
   ex."2022-10-11". User can see the error message when the format is error or if there is
   price available for that day. Otherwise, it will show the total value of the portfolio and
   the cost of basis on that day.

2. User can repeatedly enter the different date to see portfolio value.

3. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the
   load page.

##### Option 2 for Load Page: Performance Page
User can see the performance page.

1. User inputs the start date of the timespan. It will show the error message if there is an error.

2. User inputs the end date of the timespan. It will show the error message if there is an error.
   Otherwise, the user can see the performance in this timespan.

3. User can repeatedly input different start date and end date to perform.

4. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the
   load page.

##### Option 3 for Load Page: Flexible Create Page to modify it.
User will see the flexible create page again to buy or sell the stocks.

1. User inputs the date to do the transaction. The format is "2022-10-10". Other input will
   be error which will show in the end.(If the format is error, it will show it.)

2. User inputs a symbol of the stock that they want to do this transaction. (If there is any
   error,it will show it.)

3. User inputs the date to the type of transaction: "BUY" or "SELL".

4. User inputs a shares of stocks.(If the input is negative and not an integer, it will show error.)

5. User inputs the commission fee for this transaction, which needs to be double format.(If it
   is not a double, it will show error)

6. User can choose to input next stock transaction or not. If yes, enter yes. Other input will
   end of input transaction. It will automatically back to the load page.



### Create a flexible portfolio for text-based
#### purchase 3 different stocks at different dates for text-based
The procedure to create a flexible portfolio with 3 different stocks at different date. (The input
will without
quotes)
1. After neu-fall22-pdp-hw4.jar file execute, user inputs "2". Press enter.

2. After create page show, user inputs "2014-01-02". Press enter.

3. User inputs "AAPL". Press enter.

4. User inputs "BUY". Press enter

5. User inputs "100". Press enter.

6. User inputs "123.45". Press enter.

7. User inputs "yes" (must be lower case). Press enter.

8. Do the 2 to 7 again. For 2: input "2017-11-15". For 3: input "AA". For 4: input "BUY".
   For 5: input "1000". For 6: input "125.2:. For 7: input "yes" (must be lower case).

9. Do the 2 to 7 again. For 2: input "2021-10-13". For 3: input "AAA" For 4: input "BUY".
   For 5: input "1000". For 6: input "100.2:. For 7: input "no".

10. User inputs the portfolio name, ex: "ppp". Press enter.

11. User will go to load page directly.

#### Query the value and cost basis of that portfolio on two specific dates

1. User input "1". Press enter.(User will go to determine page.)

2. User input "2018-10-11". (It will show cost of basis and value together)

3. User input "2022-09-16". (It will show cost of basis and value together)

### Create an inflexible portfolio for text-based

#### 3 different stocks

The procedure to create an inflexible portfolio with 3 different stocks. (The input will without
quotes)

AAA, AA and A are valid stock symbol.

1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

2. After showing the create page, the user inputs "AAA,100". Press enter.

3. User inputs "AA,1000". Press enter.

4. User inputs "A,200". Press enter.

5. User inputs "end" (must be lower case). Press enter.

6. User inputs the portfolio name, ex: "love". Press enter. (It will automatically go to load
   page.)

#### 2 different stocks

The procedure to create an inflexible portfolio with 2 different stocks.

1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

2. After create page show, user inputs "AAPL,100". Press enter.

3. User inputs "A,1000". Press enter.

4. User inputs "end"( must be lower case). Press enter.

5. User inputs the portfolio name, ex: "222". Press enter. (It will automatically go to load
   page.)


## List of available stock and date
- List of the valid stock symbol is attached in the Excel file listing_status.csv in this folder.
- Date is anytime between 2012-01-01 to 2022-10-31