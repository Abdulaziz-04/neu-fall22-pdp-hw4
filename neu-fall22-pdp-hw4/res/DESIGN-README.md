# Portfolio

The program is implemented using a variation of MVC pattern. Instead of dividing class in to traditional MCV pattern that has Model, View and Controller, there will be one more layer called Service. In MVC point of view, we can consider service as one of the Model layer. 

> Middleware -> Controller -> Service -> Model

## Project Overview

Our project consists of multiple Contollers, Views and Models. One Controller responses for handling one user interface page including main page (MainPage), portfolio value page (InfoPage), create portfolio page (CreatePage) and portfolio examine page (LoadPage). It also has four Views for each page. 

At the highest level of the program, there is one EventLoop for managing those Controllers.

## Middleware (Main)
The middleware responses for managing group of controllers' action.
Since our project is not so big, we have one EventLoop class acts as a middleware. In our application, it maintains one controller during one time period. When run, it will:
1. Get View from Controller and render it
2. Get user input
3. Call Controller.handleInput() function and if user need to be navigated to other page (or so called Controller), it will replace current Controller with the new Controller.

### List of classes in this layer
- EventLoop (interface)
- EventLoopImpl

## Controller
Controllers coresponse for one user interface page. It mainly handles user input by calling Service(Model) based on the purpose of the action and generate the View. 

For example in LoadPageController, the Controller will handle input "back" by returning MainPageController, but will call PortfolioService to load Portfolio from storage when user input filename.

### List of classes in this layer
- PageController (interface)
- CreatePageController
- InfoPageController
- MainPageController
- LoadPageController
  
### Additional classes
- PageControllerFactory

## Service
As mentioned, Service can be considered as a Model in MVC pattern. Each Service contains the functionality that can be reusable for Controllers and other Services. The Services return Models to the Controllers.

### List of classes in this layer
- IOService (interface) *Service interface for reading and writing string to external storage*
- FileIOService *IOService for reading and writing file*
- PortfolioService (interface) *Service interface for creating and retrieving Portfolio*
- PortfolioServiceImpl 
- StockPriceApi (interface) *API interface for getting stock data*
- AlphaVantageAPI
- StockQueryService *Service interface for getting stock data from external source*
- StockQueryServiceImpl *Call StockPriceApi using cache*

### Additional classes
- Cache (interface)
- SimpleCache *Cache with custom timeout duration*

## Model
Models in the project are data model which is similar to the concept of DTO/Entity. Most of them do not contain business logic and mainly serve for storing data. The Models are implemented to be immutable once create and will be passed through Views class in the top layer for data transfer purpose only. 

### List of classes in this layer
- Portfolio
- PortfolioEntry
- PortfolioWithValue
- PortfolioEntryWithValue
- StockListEntry
- StockPrice

## View
Each View corespones to one user interface page. It takes some Model as contrustors to be use it to display text.

### List of classes in this layer
- View (interface)
- ViewAbs (abstract)
- LoadPageView
- CreatePageView
- InfoPageView
- MainPageView

### Additional classes
- ViewFactory (interface)
- DefaultSysOutViewFactory *Create View with Systen.out as a output PrintStream*

## Life Cycle
Application life cycle can be described as follows: 
1. When user request come, EventLoop will send the request to one of the Controllers
2. Controller handles request by calling service(s)
3. Service do the job and return Model as a result 
4. Controller create View using returned Models
5. EventLoop render View and repeat step 1.
