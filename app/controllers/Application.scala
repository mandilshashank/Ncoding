package controllers

import play.api.db.DB
import play.api.mvc._
import anorm._
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    Ok(views.html.App.index())
  }

  def sayHello = Action { request =>
    Ok(views.html.App.sayHello(request.getQueryString("myName").getOrElse("")))
  }

  def login = Action{
    Ok(views.html.App.login())
  }

  def loginCheck(username: String, password: String) = {
    DB.withConnection("NCoding") { implicit c =>
      val findUser: Row = SQL("Select count(*) as count from user where " +
        "username={username} and password={password}").on("username" -> username,
        "password"->password).apply().head

      val count:Int = findUser[Int]("count")

      if(count >= 1){
        //User found in database
        Action{Redirect(routes.Application.index())}
      } else {
        //User not found in database
        Action{Redirect(routes.Application.login())}
      }
    }
  }
}