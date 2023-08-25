# EORI Common Component HODS Proxy

Proxy requests from the EORI Common Component Frontend (public zone) to backend services (protected zone)

# Endpoints

## Subscribe

Forwards Subscription requests to Subscription Service (provided by the Messaging Delivery Group)

    POST    /subscribe

Request

    {
      "MDGHeader": {
        "originatingSystem": "MDTP",
        "requestTimeStamp": "2016-07-15T11:24:47.123Z",
        "correlationId": "123e4567-e89b-12d3-a456-426655440000"
      },
      "SAPNumber": "012345678900000000000000000000000000000000",
      "contactInformation": {
        "personOfContact": "first-last",
        "telephoneNumber": "0123456789",
        "mobileNumber": "0123456789",
        "faxNumber": "0123456789",
        "email": "email@example.com"
      },
      "addressInformation": {
        "addressLine1": "line one",
        "addressLine2": "line two",
        "addressLine3": "",
        "addressLine4": "city",
        "postalCode": "postcode",
        "countryCode": "UK"
      }
    }

Response

    {
      "status": "OK",
      "processingDate": "2016-07-15T11:24:47.123Z",
      "FBNumber": "0123456789"
    }
