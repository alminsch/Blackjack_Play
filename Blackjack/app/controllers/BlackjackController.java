package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.view.tui.TextUI;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.blackjack;
import views.html.index;

public class BlackjackController extends Controller {
	
	static IController controller = Blackjack.getInstance().getController();
	
	public Result blackjack(String command) {
 		TextUI tui = Blackjack.getInstance().getTUI();
 		tui.userinputselection(command);
		return ok(tui.getTUI());
	}

	public Result spielregeln() {
		return ok(blackjack.render());
	}

	public Result index() {

        return ok(index.render("Your new application is ready.",controller));
    }

	public Result jsonCommand(String command) {
		Blackjack.getInstance().getTUI().userinputselection(command);
		return json();
	}

	public Result json() {
		Map<String, Object> obj = new HashMap<String, Object>();
		List<Player> players = controller.getPlayerList();
		for(Player p : players) {
			List<Card> cards = p.getCardsInHand();
			obj.put(p.getPlayerName(), cards);
			}
		String json = Json.stringify(Json.toJson((obj)));
		return ok(json);
	}
	
	public Result addNewPlayer(String playerName) {
		controller.addnewPlayer(playerName); //rückgabewert: wenn erfolgreich Spieler hinzugefügt worden ist
		return ok();
	}
	
	public Result get() {
	    Map<String, String[]> queryParameters = request().queryString();
	    return ok(String.format("Here's my server-side data using $.get(), and you sent me [%s]", queryParameters.get("foo")[0]));
	}
}
