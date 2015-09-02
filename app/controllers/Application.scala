package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.App.index())
  }

  def sayHello = Action { request =>
    Ok(views.html.App.sayHello(request.getQueryString("myName").getOrElse("")))
  }
}