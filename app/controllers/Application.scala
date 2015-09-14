package controllers

import play.api.data.Form
import play.api.db.DB
import play.api.mvc._
import anorm._
import play.api.Play.current

object Application extends Controller {
  var loggedIn = false;
  def index = Action {
    Ok(views.html.App.index())
  }

  def sayHello = Action { request =>
    Ok(views.html.App.sayHello(request.getQueryString("myName").getOrElse("")))
  }

  def login(error: String) = Action{
    Ok(views.html.App.login(error))
  }

  def loginCheck() = Action {
    request => {
      val postParams = request.body.asFormUrlEncoded.get;
      val username = postParams.get("username").map(_.head).get;
      val password = postParams.get("password").map(_.head).get;

      DB.withConnection { implicit c =>
        val findUser: Row = SQL("Select count(*) as count from user where " +
          "username='"+username+"' and password='"+password+"'").apply().head

        val count:Long = findUser[Long]("count")
        System.out.print(Row);

        if(count >= 1){
          loggedIn=true;
          //User found in database
          Redirect("/dataPage");
        } else {
          //User not found in database
          Redirect(routes.Application.login("true"))
        }
      }
    }
  }

  def dataPage = Action{ request => {
      if(request.body.asFormUrlEncoded != None) {
        val postParams = request.body.asFormUrlEncoded.get;
        val logout = postParams.get("logout").map(_.head).getOrElse("undefined");
        if (logout == "Logout") {
          loggedIn = false;
          Redirect(routes.Application.login("false"))
        }
      }

      if(loggedIn){
        val start_next:Int = request.getQueryString("start_next").getOrElse("0").toInt;
        val end_next:Int = request.getQueryString("end_next").getOrElse("25").toInt;
        val next: String = request.getQueryString("next").getOrElse("");

        val start_prev:Int = request.getQueryString("start_prev").getOrElse("0").toInt;
        val end_prev:Int = request.getQueryString("end_prev").getOrElse("25").toInt;
        val prev: String = request.getQueryString("prev").getOrElse("");

        DB.withConnection { implicit c =>
          val findDonation: List[(String, String)] = SQL("Select * from data")().map(row =>
            row[String]("schoolname") -> row[String]("awardamount")).toList;

          val findCount: Row = SQL("Select count(*) as count from data").apply().head
          val count: Long = findCount[Long]("count")

          //Produce a table here
          if(next.equals("Next")) {
            Ok(views.html.App.dataRender(findDonation, start_next, end_next, count))
          } else if(prev.equals("Prev")) {
            Ok(views.html.App.dataRender(findDonation, start_prev, end_prev, count))
          } else {
            Ok(views.html.App.dataRender(findDonation, 0, 25, count))
          }
        }
      } else {
        Redirect(routes.Application.login("false"))
      }
    }
  }
}