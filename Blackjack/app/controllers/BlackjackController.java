package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.view.tui.TextUI;
import model.WebSocketActor;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.LegacyWebSocket;
import play.mvc.WebSocket;
import views.html.*;
import play.Logger;

public class BlackjackController extends Controller {
	
	static IController controller = Blackjack.getInstance().getController();
	
	public Result blackjackindex() {
		return ok(blackjackindex.render("Welcome to Blackjack"));
	}
	
	public Result blackjack(String command) {
		Blackjack.getInstance().getTUI().userinputselection(command);
		return ok(index.render("Blackjack ready",controller));
	}
	
	public Result start() {
		return ok(start.render("Start Page"));
	}
	
	public Result rules() {
		return ok(rules.render("Spielregeln"));
	}

	public Result index(String command) {
        return ok(index.render("Blackjack ready",controller));
    }

	public Result jsonCommand(String command) {
		Blackjack.getInstance().getTUI().userinputselection(command);
		return json("");
	}
	
	public Result addNewPlayer(String player) {
		Blackjack.getInstance().getController().addnewPlayer(player);
		return json("");
	}
	
	public Result json(String command) {
		return ok(controller.json());
	}
	
	public static String getJson(String command) {
		if(!command.equals("null")) {
			Blackjack.getInstance().getTUI().userinputselection(command);
		}
		return controller.json();
	}
	
	public LegacyWebSocket<String> createWebSocket() {
		Logger.info("creating websocket with actor");
		return WebSocket.withActor(WebSocketActor::props);
	}
}
