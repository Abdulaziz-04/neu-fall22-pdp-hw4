# Portfolio

## Design changes

As per last assignment feedback, we have change some of our project structure.

1. Before our controller interacts with multiple models, now controller only call on main model. Our
   main model (PortfolioModel) now responses for managing and maintaining a portfolio.
2. IO file read/write has been moved to controller layer.
3. Remove unnecessary factory class

## Change to cooperate new requirement for this assignment
1. PortfolioEntry class is renamed to Transaction. It has three more fields which are date, transaction type and commission fee.
2. New PerformancePageController and PerformancePageView for showing portfolio performance overtime.
3. More functions added in Portfolio interface (calculate cost of basis, performance)

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

### Additional classes

- ViewFactory (interface)
- DefaultSysOutViewFactory *Create View with System.out as a output PrintStream*

## Life Cycle

Application life cycle can be described as follows:

1. When user request come, FrontController will send the request to one of the Controllers
2. Controller handles request by calling PortfolioModel
3. PortfolioModel return immutable model object as a result
4. Controller create View using returned models
5. FrontController render View and repeat step 1.
