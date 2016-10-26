package controllers;

import play.mvc.*;
import views.html.*;
import de.htwg.blackjack.Blackjack;
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
}
