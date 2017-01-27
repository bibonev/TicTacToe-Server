README - Boyan Bonev - bib508

Implemented:
 - exit button
 - list of online players
 - request/accept/reject features
 - alerts (pop up appropriate messages)
 - scoreboard
 - dealing with uniqueness of usernames (increase by 1 the last similar name)
 - dealing ambiguity -> when a third user requests to play agains someone who is already in a game, he/she cant
 - everything is in GUI
 - when the board is closed by one of the users, the connection breaks
 
Not implemented:
 - all of the things listed in the marking scheme have been implemented (in my opinion)
 
Usage:
 Button exit 		- acts like quit
 Button refresh 	- gets the online users and sets them into the JList
 Button connect 	- establish a connection with the selected user from the JList (be carefull to select an item after you have pressed refresh, not scoreboard)
 Button scoreboard 	- gets the scoreboard from the server and sets it to the JList 
 
