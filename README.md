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

## The API response
A JSON object containing the resulting chain in a string format along with the maximum sum of common values between connected pieces as well as any remaining unallocated Tiles


## How to run
To run using maven simply run: mvn spring-boot:run
