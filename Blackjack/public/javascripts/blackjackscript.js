var uri = "ws://localhost:9000/socket";
var websocket;
var maxAmountCards = 6;

window.onload = getCards();

function getCards() {
$.get('/json',
	function(data) {
		processData(data);
});
}
    	
function jsonCommand(command) {
$.post('/json/' + command,
	function(data) {
		processData(data);
	});
}

function jsonNewPlayer() {
	var name = $("#inputname").val();
	$.post('/newPlayer/' + name,
		function(data) {
			processData(data);	
	});
}


function processData(data) {
	var json = JSON.parse(data);
	// Player Cards
  	var cards = json[1].cards;
  	var players = json[0].players[0].length;
  	
  	for (var i = 0; i < players; i++) {
      	if (cards.length > 0) {
			var playerCards = cards[i];
			var amountPlayerCards = playerCards.length;
			for (var j = 0; j < amountPlayerCards; j++) {
				var cardPath = "/images/" + playerCards[j] + ".png";
				$("#pc" + i + "-" + j).attr("src", cardPath);
			}
			// reset playercards
			if (json[3].statusline[1] === "DURING_BET") {
				for (var x = 0; x < maxAmountCards; x++) {
					$("#pc" + i + "-" + x).attr("src","");
				}
				resetCards = 0;
			}					
      	}
  		// Spielername, Kartenwert, Budget
		$("#playername-player" + i).text("Spieler " + (i + 1) + ": " + json[0].players[0][i]);
		$("#cardvalue-player" + i).text("Wert:   " + json[0].players[1][i]);
		$("#budget-player" + i).text("Budget: " + json[0].players[2][i]);
		$("#bet-player" + i).text("Wetteinsatz: " + json[0].players[3][i]);
		// TODO: aktueller Wetteinsatz
  	}
	
	// Dealer Cards
	var dealercards = json[2].dealer[0];
	for (var i = 0; i < dealercards.length; i++) {
		var cardPath = "/assets/images/" + dealercards[i] + ".png";
		$("#dc" + i).attr("src", cardPath);
	}
	// reset dealercards
	if (json[3].statusline[1] === "DURING_BET") {
		for (var i = 0; i < maxAmountCards; i++) {
			$("#dc" + i).attr("src", "");
		}
	}

	$("#dealer-cardvalue").text(json[2].dealer[1][0]);

	// Bet Anzeige
	$("#bet-field").text("Bet:    " + json[4].bet[0]);
	$("#total-field").text("Total:  " + json[4].bet[1]);
	// Status
	$("#statusline").text(json[3].statusline[0]);
}