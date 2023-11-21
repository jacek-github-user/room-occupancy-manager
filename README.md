# How to run
2 ways:
- ./gradlew bR
- ./gradlew jDB && docker compose up

In both cases the app will get up & running on port 8080

# How to test
./gradlew test

# How to interact
Service exposes 2 groups of endpoints:
### for calculating max revenue: GET /rooms/calculate-revenue/premium/{premium}/economy/{economy}, e.g.:
  `curl --location 'localhost:8080/rooms/calculate-revenue/premium/2/economy/3'`

### for setting up & managing client offers state:
- adding new set of offers: POST /guests, e.g.:
`curl --location 'localhost:8080/guests' \
  --header 'Content-Type: application/json' \
  --data '{
  "guests": [100]
  }'
`
- displaying current state of offers: GET /guests, e.g.:
`curl --location 'localhost:8080/guests'`
- deleting all offers: DELETE /guests, e.g.:
`curl --location --request DELETE 'localhost:8080/guests'`

# Other
- basic validation was added with the use of jakarta validation api
- no security added 