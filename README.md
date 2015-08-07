# HECActivation
######Alternative to PayPal's Headless Express Checkout flow

Headless Express Checkout is PayPal's current checkout flow for trusted merchants on set-top boxes without traditional browsers. It is being phased out by the end of 2015 and needs to be replaced. One of the proposed solutions is an activation code system, where the merchant prompts the consumer to go to paypal.com/activate and enter in an activation code the merchant receives from PayPal and displays to the consumer; upon entering the code, the consumer logs into PayPal and goes through the standard express checkout flow. In this flow, the activation code acts as a shorter key that maps to an express checkout token. This Android TV app is a prototype proof-of-concept for an HEC replacement involving an activation code; it mocks a Netflix flow on a set-top box.

An alternative flow to replace Headless Express Checkout with a notification based system can be found [here] (https://github.com/apmadhani/HECPrototype).

##Getting Started

```bash
    # clone the repo
    $ git clone git@github.com:apmadhani/HECPrototype.git
    $ cd HECPrototype

    # set up configurations
    $ cp app/src/main/res/values/credentials.xml.example app/src/main/res/values/credentials.xml
    $ vim app/src/main/res/values/credentials.xml
```

At this point, you'll have to replace url:port in credentials.xml with the address of a mock merchant server. You can download Android Studio and Android emulator [here] (https://developer.android.com/sdk/index.html) in order to modify/run the app.

##Mock Server Information

This app will communicate with the merchant server, which will communicate with the PayPal servers to get express checkout links. Currently, paypal.com/activate has not been set up, so the mock merchant server will need to set up its own activation page; this activation page should receive the activation code, and, if its valid, redirect to the PayPal express checkout flow. The merchant server must have the following endpoints:

| Endpoint                              | Description                                           |
| ------------------------------------- | ----------------------------------------------------- |
| [/cart/submit](#cartsubmit)           | Submit the item(s) in the user's cart.                |
| [/payment/send](#paymentsend)         | Send the PayPal EC link to the user's phone or email. |
| [/payment/status](#paymentstatus)     | Check on the status of a payment.                     |

The endpoints should have the following API's:

### /cart/submit

Submit the item(s) in the user's cart.

Request `data`

```json
{
    "email": ...,
    "password": ...
    "cart": [
        ...
    ]
}
```

Response `data`

```json
{
    "message": "Your cart has been successfully submitted.",
    "token": ...
}
```

### /payment/send

Recieve an activation code that links to an express checkout flow.

Request `data`

```json
{
    "email": ...,
    "password": ...,
    "ec_token": ...,
    "payment": {
        "method": ...,
    }
}
```

The `method` field should be "activationcode".

Response `data`

```json
{
    "activationcode": ...
}
```

### /payment/status

Check on the status of a payment.

This endpoint uses long polling. It will not respond until the user has either completed or cancelled their payment.

Requests `data`

```json
{
    "email": ...,
    "password": ...,
    "ec_token": ...
}
```

Response `data`

```json
{
    "status": ...
}
```
