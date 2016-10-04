	
#HOW TO RUN PROJECT USING INTELLIJ
- clone project
- maven clean install
- go on com.tipico.livescore.Application class file, right click on it and select **run Application**
- open localhost:8080/index.html
- click on Connect

##IMPROVEMENTS
- transform data using paralell processing (java 1.8 streams)
- intelligent get data based on results returned
- send only changed games
- send games to user only on socket registration
- gradle?
- html page shpwing websocket stats
- research on socket limits (max connections, load balancing etc etc)
