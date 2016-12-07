package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.view.tui.TextUI;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class BlackjackController extends Controller {
	
	static IController controller = Blackjack.getInstance().getController();
	
	public Result blackjack(String command) {
 		TextUI tui = Blackjack.getInstance().getTUI();
 		tui.userinputselection(command);
		return ok(tui.getTUI());
	}
	
	public Result start() {
		return ok(start.render("Start Page"));
	}
	
	public Result rules() {
		return ok(rules.render("Spielregeln"));
	}

	public Result index() {
        return ok(index.render("Your new application is ready.",controller));
    }

	public Result jsonCommand(String command) {
		Blackjack.getInstance().getTUI().userinputselection(command);
		return json();
	}

	
	public Result json() {
		List<Map> array = new ArrayList<Map>();

		Map<String, Object> players = new HashMap<String, Object>();
		Map<String, Object> cards = new HashMap<String, Object>();
		Map<String, Object> dealer = new HashMap<String, Object>();
		
	
		List<String> playerlist = new ArrayList<String>();
		
		List<List> cardArray = new ArrayList<List>();
		for(Player p : controller.getPlayerList()) {
			playerlist.add(p.getPlayerName());
			cardArray.add(p.getCardsInHand());
		}
		
		players.put("players", playerlist);
		cards.put("cards", cardArray);
		dealer.put("dealer", controller.getDealerCards());

		array.add(players);
		array.add(cards);

		String json = Json.stringify(Json.toJson(array));
		return ok(json);
	}
	
	public Result addNewPlayer(String playerName) {
		controller.addnewPlayer(playerName); 
		//todo:rückgabewert: wenn erfolgreich Spieler hinzugefügt worden ist?
		return ok();
	}
	
	public Result get() {
	    Map<String, String[]> queryParameters = request().queryString();
	    return ok(String.format("Here's my server-side data using $.get(), and you sent me [%s]", queryParameters.get("foo")[0]));
	}
}
