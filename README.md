# Domino Chain app
A REST API that computes and returns the Domino chain with the highest value of common values between connected pieces.

## Restrictions and rules
* Tile values on either side need to range between 1 and 10. 
* Tiles with the same values i.e [4,4] are not valid. 
* Two pieces can be connected in the chain if they share a common value
* Each piece can be used only once in the chain
* The list of Tiles can contain duplicate Tiles. For example tile [1,2] which is a valid tile can occur more than once in * the set.

## Some notes on the algorithm
The algorithm tries to accommodate all pieces in the chain by running 1000 runs of possible games. It then chooses to respond with the chain that has the greatest sum of common values between connected Tiles.

## The API request
Use Postman or similar tool to test the POST request.

Example POST Request Mapping: http://localhost:8080/chain/compute

Example body JSON:

```json
{
    "startingTile": 
    {"leftValue": 1, "rightValue": 9},
    "tiles":[
        {"leftValue": 2, "rightValue": 9},
        {"leftValue": 3, "rightValue": 5},
        {"leftValue": 9, "rightValue": 3},
        {"leftValue": 9, "rightValue": 1},
        {"leftValue": 1, "rightValue": 2}
       
    ]
}
```

A Postman example request is saved in src/main/resources/domino.postman_collection.json that can be imported into Postman.

## The API response
A JSON object containing the resulting chain in a string format along with the maximum sum of common values between connected pieces as well as any remaining unallocated Tiles.

For example

```json
{
    "chain": "[[5, 3], [3, 9], [9, 2], [2, 1], [1, 9], [9, 1]]",
    "maxSumVal": 24,
    "unallocatedTiles": []
}
```



## How to run
To run using maven simply run: mvn spring-boot:run

