# Server

## Run the server as a daemon

    pm2 start index.js
    
## Lists all running pm2-ified node services

    pm2 list
    
## Lists info for the service

    pm2 info <name of index.js>
    
## Show logs (tail)

    pm2 logs <name of index.js>
    
## Nuke the service

    pm2 delete <name of index.js>