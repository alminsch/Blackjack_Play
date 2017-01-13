package model;

import akka.actor.*;

public class WebSocketActor extends UntypedActor {
	
	public static Props props(ActorRef out) {
		return Props.create(WebSocketActor.class,out);
	}
	
	private final ActorRef out;
	
	public WebSocketActor(ActorRef out) {
		this.out = out;
	}
	
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			out.tell(controllers.BlackjackController.getJson(message.toString()),self());
		}
	}
	
}