# Flatfinder
Flatfinder, find your new flat "easy and faster" ;)

This App will look every 5 minutes for new offers matching your search criterias.
Matching offers will notify you via a telegram message.

**Warning**. If you use it, don't parse the providers pages to often to reduce load on their side. 

## Setup
Configuration is done in the application.yml file

### Database
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
### Create Telegram Bot
Follow steps described here:
https://core.telegram.org/bots#6-botfather

```
telegram.token=  //API Token
telegram.client_id= //Username
```

### Configure Searchcriteria
Following platforms are supported:
- https://www.immobilienscout24.de/
- https://www.immonet.de/

Only the first page of the results will be parsed.
So you should sort your results to show the last recent offers first. 
Both providers save the complete searchquery in url.

Use complete url as values for:
```
immoscout.url=
immonet.url=
```