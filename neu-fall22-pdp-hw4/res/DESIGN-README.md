# Portfolio

## Design changes

### GUI
In order to support GUI that has different input behavior than TUI, we implemented new View and Controller for GUI using the same View and Controller interface as before.
Since our TUI handle input in string, we send a callback into View and pass it back to a Contoller.
Both GUI and TUI call the same main model (PortfolioModelImpl.)

### Buy strategy
We create a new interface called BuySchedule for buying strategy. 
It contains information such as which stock to buy, start date, end date and buying frequency days.
We have added these following items to cooperate the changes:

1. New field in Portfolio for holding a list of BuySchedule. (Also get method to get the list)
2. ScheduleRunner class and its interface for calculate the list of transactions from scheduled strategy.
3. New methods in main model to add schedule into Portfolio (PortfolioModel.addSchedule())
4. PortfolioParser parse text with version. Going forward we can easily parse older version of Portfolio.

## Project Overview

Our project consists of multiple Controllers, Views and Models. One Controller responses for handling
one user interface page including main page (MainPage), portfolio value page (InfoPage), create
portfolio page (CreatePage), portfolio examine page (LoadPage) and portfolio performance page (
PerformancePage). It also has six Views for each page.

At the highest level of the program, there is one FrontController for managing those Controllers.

## Controller

Controllers coresponse for one user interface page. It mainly handles user input by calling methods in main model (PortfolioModel) based on the purpose of the action and generate the View.

1. Get View from Controller and render it
2. Get user input
3. Call Controller.handleInput() function and if user need to be navigated to other page (or so
   called Controller), it will replace current Controller with the new Controller.

### List of classes in this layer

- FrontController (interface)
- FrontControllerImpl
- PageController (interface)
- FlexibleCreatePageController
- InflexibleCreatePageController
- InfoPageController
- MainPageController
- LoadPageController
- PerformancePageController
- IOService (interface) *Service interface for reading and writing string to external storage*
- FileIOService *IOService for reading and writing file*
- New SwingPageController (8 files)

## Model

Our main model is PortfolioModel. It holds other models as dependencies.

### List of classes in this layer
- PortfolioModel (interface)
- PortfolioModelImpl
- Portfolio (interface)
- PortfolioAbs (abstract implements Portfolio)
- InflexiblePortfolio
- FlexiblePortfolio
- Transaction
- TransactionType
- PortfolioWithValue
- PortfolioEntryWithValue
- StockListEntry
- StockPrice
- PortfolioFormat
- PortfolioPerformance
- PortfolioParser (interface)
- PortfolioTextParser
- StockPriceApi (interface) *API interface for getting stock data*
- AlphaVantageAPI
- StockQueryService *Service interface for getting stock data from external source*
- StockQueryServiceImpl *Call StockPriceApi using cache*

### Additional classes
- Cache (interface)
- SimpleCache *Cache with custom timeout duration*

## View

Each View corespones to one user interface page. It takes some Model as contrustors to be use it to
display text.

### List of classes in this layer

- View (interface)
- ViewAbs (abstract)
- LoadPageView
- FlexibleCreatePageView
- InflexibleCreatePageView
- InfoPageView
- MainPageView
- PerformancePageView
- New View for GUI (8 files)

### Additional classes

- ViewFactory (interface)
- DefaultSysOutViewFactory *Create View with System.out as a output PrintStream*
- SwingViewFactory

## Life Cycle

Application life cycle can be described as follows:

1. When user request come, FrontController will send the request to one of the Controllers
2. Controller handles request by calling PortfolioModel
3. PortfolioModel return immutable model object as a result
4. Controller create View using returned models
5. FrontController render View and repeat step 1.
