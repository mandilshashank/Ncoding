package controllers

import play.api.data.Form
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

  def loginCheck() = Action {
    request => {
      val postParams = request.body.asFormUrlEncoded.get;
      val username = postParams.get("username").map(_.head).get;
      val password = postParams.get("password").map(_.head).get;

      System.out.print(username + "    " +password)
      DB.withConnection { implicit c =>
        val findUser: Row = SQL("Select count(*) as count from user where " +
          "username='"+username+"' and password='"+password+"'").apply().head

        val count:Long = findUser[Long]("count")
        System.out.print(Row);

        if(count >= 1){
          //User found in database
          Redirect(routes.Application.index())
        } else {
          //User not found in database
          Redirect(routes.Application.login())
        }
      }
    }
  }
}