# EORI Common Component HODS Proxy

Proxy requests from the EORI Common Component Frontend (public zone) to backend services (protected zone)

Other related ECC services:
- Backend service: [EORI Common Component](https://github.com/hmrc/eori-common-component)
- Stubs: [EORI Common Component Stubs](https://github.com/hmrc/eori-common-component-hods-stubs)
- Registration service: [EORI Common Component Registration Frontend](https://github.com/hmrc/eori-common-component-registration-frontend)
- Subscription service: [EORI Common Component Frontend](https://github.com/hmrc/eori-common-component-frontend)


### Service Manager Commands

What's running?

    sm2 -s

Start the required development services (make sure your service-manager-config folder is up to date)

    sm2 --start EORI_COMMON_COMPONENT_ALL

Stop all ECC related services

    sm2 --stop EORI_COMMON_COMPONENT_ALL

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
