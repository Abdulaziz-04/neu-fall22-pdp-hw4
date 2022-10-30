package portfolio.views.impl;

import portfolio.views.View;

public class LoadPageView implements View {

  private final String errorMessage;
  public LoadPageView(String errorMessage){
    this.errorMessage = errorMessage;
  }
  @Override
  public void render() {

  }
}
