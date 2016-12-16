package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;
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
		return ok();
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
	
	public Result addNewPlayer(String player) {
		Blackjack.getInstance().getController().addnewPlayer(player);
		return json();
	}
	
	public Result json() {
		List<Map> array = new ArrayList<Map>();

		Map<String, Object> players = new HashMap<String, Object>();
		Map<String, Object> cards = new HashMap<String, Object>();
		Map<String, Object> dealer = new HashMap<String, Object>();
		Map<String, Object> statusLine = new HashMap<String, Object>();
		Map<String, Object> betDisplay = new HashMap<String, Object>();
		

		List<List> playerlist = new ArrayList<List>();
		List<String> playerName = new ArrayList<String>();
		List<String> cardValue = new ArrayList<String>();
		List<String> budget = new ArrayList<String>();
		
		List<List> cardArray = new ArrayList<List>();
		List<List> dealerlist = new ArrayList<List>();
	
		for(Player p : controller.getPlayerList()) {
			playerName.add(p.getPlayerName());
			cardValue.add(p.getHandValue()[0] + "/" + p.getHandValue()[1]);
			budget.add(Integer.toString(p.getbudget()));
			
			cardArray.add(p.getCardsInHand());
		}
		
		// player
		playerlist.add(playerName);
		playerlist.add(cardValue);
		playerlist.add(budget);
		players.put("players", playerlist);
		
		// cards
		cards.put("cards", cardArray);
		
		// dealer
		dealerlist.add(controller.getDealerCards());
		dealerlist.add(Arrays.asList(controller.getDealer().getHandValue()[0] + "/" + controller.getDealer().getHandValue()[1]));
		dealer.put("dealer", dealerlist);
		
		// statusLine
		statusLine.put("statusline", controller.getStatus());
		
		// Bet
		betDisplay.put("bet", Arrays.asList(controller.getDisplayBet(), controller.getTotalPlayerBet()));
		
		array.add(players);
		array.add(cards);
		array.add(dealer);
		array.add(statusLine);
		array.add(betDisplay);

		String json = Json.stringify(Json.toJson(array));
		return ok(json);
	}
	
	public Result get() {
	    Map<String, String[]> queryParameters = request().queryString();
	    return ok(String.format("Here's my server-side data using $.get(), and you sent me [%s]", queryParameters.get("foo")[0]));
	}
}
