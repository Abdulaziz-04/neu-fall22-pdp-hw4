# Setup

This is the SETUP-README for our project.

note: Portfolio file will be save to the base location that you run cmd, not the location of JAR file. For example if you run cmd with 

> C:\Users\Myok>java -jar C:\Users\Myok\Desktop\neu-fall22-pdp-hw4\res\neu-fall22-pdp-hw4.jar

The program will read / write file from C:\Users\Myok

## Using the program

The procedure to use this program: (input exit will exit the program)

1. Go to the directory which contains neu-fall22-pdp-hw4.jar (in res folder).

2. Execute the neu-fall22-pdp-hw4.jar file.

## Main Page:

1. After execute jar file, user will see the main menu screen. 

2. Users can choose "1", "2" or "3. If user inputs "1", user will go to the inflexible create page.
   If user inputs "2", user will go to the flexible create page. If user input "3", user will go 
   to the load page. (If user wants to determine a portfolio value, the cost of basis, show the 
   performance of a portfolio, user need to go to load page first. If the number that user 
   inputted is not "1", "2", or "3" the program will show the error message to user and user 
   need to input again). 

### Option 1: Inflexible Create Page
The user will see inflexible create page.

1. User can input the symbol of a stock and the amount, which is number of shares, in the giving format. The format is (symbol,shares) ex. AA,100 (All the letter need to be in uppercase). If user inputs is not correct, user can see different error messages for why the input is not correct. Otherwise, the user can see the stocks and shares which were inputted before.
    
2. User can input next stock and shares.
    
3. If user want to finish input the stock into the portfolio. Please input "end"(in lowercase). And then user can see the portfolio composition and will be asked to input the name of the portfolio.

4. User inputs the name of this portfolio. If the name is valid, it will create the portfolio successfully. Otherwise, it will fail to create. (At this step, it will save the portfolio as a file. The portfolio name have some restrictions, which mentioned in program).

5. After that, users can choose to determine this portfolio or not. If user inputs "yes" (should be lower case), it will go to determine page. Otherwise, it will go back to main page.

6. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the main page.

### Option 2: Flexible Create Page
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
   portfolio name. (If the name is valid, it will create the portfolio successfully. Otherwise, 
   we need to input name again.

8. User inputs any input except back will go to load page.

9. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the 
main page.

### Option 3: Load Page
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

#### Option 1 for Load Page: Determine Page

User can see the determine page. (It will show the value and cost of basis together)

1. User inputs the date that they want to determine in giving format. The format is yyyy-MM-dd 
   ex."2022-10-11". User can see the error message when the format is error or if there is 
   price available for that day. Otherwise, it will show the total value of the portfolio and 
   the cost of basis on that day.

2. User can repeatedly enter the different date to see portfolio value.

3. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the 
   load page.

#### Option 2 for Load Page: Performance Page

User can see the performance page.

1. User inputs the start date of the timespan. It will show the error message if there is an error.

2. User inputs the end date of the timespan. It will show the error message if there is an error.
   Otherwise, the user can see the performance in this timespan.

3. User can repeatedly input different start date and end date to perform.

4. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the
      load page.

#### Option 3 for Load Page: Flexible Create Page to modify it.

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

## Create a flexible portfolio
### purchase 3 different stocks at different dates
The procedure to create a flexible portfolio with 3 different stocks at different date. (The input
will without
quotes)
1. After neu-fall22-pdp-hw4.jar file execute, user inputs "2". Press enter.

2. After create page show, user inputs "2014-01-02". Press enter. 

3. User inputs "AAPL". Press enter.

4. User inputs "BUY". Press enter

5. User inputs "100". Press enter.

6. User inputs "123.45". Press enter.

7. User inputs "yes". Press enter.

8. Do the 2 to 7 again. For 2: input "2017-11-15". For 3: input "AA". For 4: input "BUY".
   For 5: input "1000". For 6: input "125.2:. For 7: input "yes".

9. Do the 2 to 7 again. For 2: input "2021-10-13". For 3: input "AAA" For 4: input "BUY".
   For 5: input "1000". For 6: input "100.2:. For 7: input "no".

10. User inputs the portfolio name, ex: "ppp". Press enter.

11. User can input "yes" to go to the determine page.

### Query the value and cost basis of that portfolio on two specific dates

1. User input "2018-10-11". (It will show cost of basis and value together)

2. User input "2021-09-16". (It will show cost of basis and value together)

## Create an inflexible portfolio

### 3 different stocks

The procedure to create an inflexible portfolio with 3 different stocks. (The input will without 
quotes)

AAA, AA and A are valid stock symbol.

  1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

  2. After showing the create page, the user inputs "AAA,100". Press enter.

  3. User inputs "AA,1000". Press enter.

  4. User inputs "A,200". Press enter.

  5. User inputs "end" (must be lower case). Press enter.

  6. User inputs the portfolio name, ex: "love". Press enter.

  7. User can choose go to determine or not. If yes, user inputs "yes". Other input will go back to main menu. Press enter.

### 2 different stocks

The procedure to create an inflexible portfolio with 2 different stocks.

  1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

  2. After create page show, user inputs "AAPL,100". Press enter.

  3. User inputs "A,1000". Press enter.

  4. User inputs "end"( must be lower case). Press enter.

  5. User inputs the portfolio name, ex: "222". Press enter.

  6. User can choose go to determine or not. If yes, user inputs "yes". Other input will go back to main menu. Press enter.


## List of available stock and date
- List of the valid stock symbol is attached in the Excel file listing_status.csv in this folder.
- Date is anytime between 2012-01-01 to 2022-10-31