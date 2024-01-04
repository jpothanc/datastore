# Datastore

Datastore is a highly efficient caching service developed on Java Spring Boot, tailored to optimize database query performance using read-through caching techniques.

## Overview

The application significantly improves response times by intelligently caching frequently accessed data from configured databases. It seamlessly manages cached data, reducing latency and enhancing overall system efficiency.

## Key Features

- **Read-Through Caching:** Utilizes a sophisticated read-through caching mechanism to store and serve frequently requested data, minimizing database load and response time.
- **Configurable Catalogue:** Offers a structured and customizable catalogue format to define database queries, encompassing parameters, sources, and caching strategies for different data segments.
- **Preloading Options:** Supports preloading critical data into the cache, enhancing responsiveness by ensuring immediate access to vital information.
- **Health Checks:** Implements health checks to verify data integrity, ensuring reliability and consistency within the cached data.

## Configuration

### Catalogue Structure

The catalogue orchestrates database queries and their caching behaviors through a detailed yet flexible structure. Here's an illustrative configuration:
```json
{
    "catalogue" : {
      "trading" : {
        "omanUsers" : {
          "datasource" : "RefData",
          "query" : "select * from Users where retired != 1",
          "preload" : true,
          "indexes" : "username",
          "health": "select * from Users where 1 = 2"
        },
        "omanDesks" : {
          "datasource" : "RefData",
          "query" : "select * from Desks",
          "preload" : false,
          "indexes" : "",
          "health": "select * from Desks where 1 = 2"
        }
      },
      "products" : {
        "stock" : {
          "datasource" : "ProdData",
          "query" : "select * from equities where exchange = {0}",
          "preload" : true,
          "indexes" : "",
          "health": "select * from equities where 1 = 2"
        },
        "restricted" : {
          "datasource" : "ProdData",
          "query" : "select * from restricted",
          "preload" : true,
          "indexes" : "",
          "health": "select * from restricted where 1 = 2"
        }
      }
    }
}
```

