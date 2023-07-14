# Delivery Service
## Execute
You can run the program using the jar file or the exe file provided in the root of this repository.
To run with java, run:

``` java -jar delivery-svc-0.0.jar ```

## Green Delivery

Days of the month that are divisible by 7 or 13 offer green delivery

## How to use the program

Put your product list in a json file with the following format

```json
[
    {
        "productId": 0,
        "name": "tomato",
        "deliveryDays": [1, 3, 6],
        "productType": "NORMAL",
        "daysInAdvance": 0
    }
]
```
Other than that, run the program and follow the commands!


## Package
To create the .jar executable, navigate to the root of the project (delivery/)
in there run the following command:

``` mvnw clean package -DskipTests ```

This will package the program into an executable jar file.

