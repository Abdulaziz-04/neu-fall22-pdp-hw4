# Setup

This is the SETUP-README for our project.

note: Portfolio file will be save to the base location that you run cmd, not the location of JAR file. For example if you run cmd with 

> C:\Users\Myok>java -jar C:\Users\Myok\Desktop\neu-fall22-pdp-hw4\res\neu-fall22-pdp-hw4.jar

The program will read / write file from C:\Users\Myok

## Using the program

The procedure to use this program:

1. Go to the directory which contains neu-fall22-pdp-hw4.jar (in res folder).

2. Execute the neu-fall22-pdp-hw4.jar file.

## Main Page:

1. After execute jar file, user will see the main menu screen. 

2. Users can choose "1" or "2". If user inputs 1, user will go to the create page. If user inputs 2, user will go to the examine page. (If user wants to determine a portfolio value, the user need go to examine page or create page first. If the number that user inputted is not 1 or 2, the program will show the error message to user and user need to input again).

### Option 1: Create Page
The user will see create page.

1. User can input the symbol of a stock and the amount, which is number of shares, in the giving format. The format is (symbol,shares) ex. AA,100 (All the letter need to be in uppercase). If user inputs is not correct, user can see different error messages for why the input is not correct. Otherwise, the user can see the stocks and shares which were inputted before.
    
2. User can input next stock and shares.
    
3. If user want to finish input the stock into the portfolio. Please input "end"(in lowercase). And then user can see the portfolio composition and will be asked to input the name of the portfolio.

4. User inputs the name of this portfolio. If the name is valid, it will create the portfolio successfully. Otherwise, it will fail to create. (At this step, it will save the portfolio as a file. The portfolio name have some restrictions, which mentioned in program).

5. After that, users can choose to determine this portfolio or not. If user inputs "yes" (should be lower case), it will go to determine page. Otherwise, it will go back to main page.

6. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the main page.

### Option 2: Examine Page
User can see examine page.

1. User inputs the name of a portfolio. If it exists, user can see the composition. Otherwise, show the error message to user.

2. For existing portfolio, user can choose go to determine or not. If user inputs "yes", (lower case), it will go to determine page. Otherwise, it will go back to main page.

3. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the main page.

## Determine Page

User can see the determine page.

1. User inputs the date that they want to determine in giving format. The format is yyyy-MM-dd ex."2022-10-11". User can see the error message when the format is error or if there is price available for that day. Otherwise, it will show the total value of the portfolio.

2. User can repeatly enter the different date to see portfolio value.

3. If user inputs "back" (lower case) at anytime in this page, user will be directed back to the main page.

## Create a portfolio with 3 different stocks

The procedure to create a portfolio with 3 different stocks. (The input will without quotes)

AAA, AA and A are valid stock symbol.

  1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

  2. After showing the create page, the user inputs "AAA,100". Press enter.

  3. User inputs "AA,1000". Press enter.

  4. User inputs "A,200". Press enter.

  5. User inputs "end"( must be lower case). Press enter.

  6. User inputs the portfolio name, ex: "love". Press enter.

  7. User can choose go to determine or not. If yes, user inputs "yes". Other input will go back to main menu. Press enter.

## Create a portfolio with 2 different stocks

The procedure to create a portfolio with 2 different stocks.

  1. After neu-fall22-pdp-hw4.jar file execute, user inputs "1". Press enter.

  2. After create page show, user inputs "AAPL,100". Press enter.

  3. User inputs "A,1000". Press enter.

  4. User inputs "end"( must be lower case). Press enter.

  5. User inputs the portfolio name, ex: "222". Press enter.

  6. User can choose go to determine or not. If yes, user inputs "yes". Other input will go back to main menu. Press enter.

## List of available stock and date
- List of the valid stock symbol is attached in the excel file listing_status.csv in this folder.
- Date is anytime between 2015-01-01 to 2022-10-31