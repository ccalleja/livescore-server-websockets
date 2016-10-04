# Overview
A simple spring boot application which fetches data at a scheduled interval,
transforms the data using streams and stores in cache. Data is available to clients
via get requests or websocket configuration.	


##HOW TO RUN PROJECT USING INTELLIJ
- clone project
- maven clean install
- go on com.tipico.livescore.Application class file, right click on it and select **run Application**
- open localhost:8080/index.html
- click on Connect

##TODOS / Improvements
- intelligent get data based on results returned
- send only changed games
- send games to user only on socket registration
- gradle?
- html page showing websocket stats
- research on socket limits (max connections, load balancing etc etc)
