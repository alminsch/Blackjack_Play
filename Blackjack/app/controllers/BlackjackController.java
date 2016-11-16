package controllers;

import play.mvc.*;
import views.html.*;

import java.util.HashMap;
import java.util.Map;

import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.view.tui.TextUI;

public class BlackjackController extends Controller {
	public Result blackjack(String command) {
 		TextUI tui = Blackjack.getInstance().getTUI();
 		tui.userinputselection(command);
		return ok(tui.getTUI());
	}
	public Result spielregeln() {
		return ok(blackjack.render());
	}
	
	public Result index() {
    	IController controller = Blackjack.getInstance().getController();
        return ok(index.render("Your new application is ready.",controller));
    }
	
	public static Result json() {
	Map<String, Object> obj = new HashMap<String, Object>();
	return null;
	
	}
}
