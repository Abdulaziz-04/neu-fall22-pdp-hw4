package portfolio.controllers;

import portfolio.entities.Page;

public interface PageController {
  void render();
  Page gotCommand(String command) throws Exception;
}
