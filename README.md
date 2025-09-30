# Secure File Delivery System

This is a Spring Boot 3.x application backed by a Postgres database for persistence requirements.

Observability requirements are fulfilled with:

1. The OTel Java agent for signal producing. These include logs, metrics and distributed traces
2. An OTel collector with the following exporters
    1. Logs - with Datadog and AWS CloudWatch Logs as destinations
    2. Metrics - with Datadog as a destination
    3. Traces - with Datadog and AWS XRay as destinations

Security requirements are fulfilled with:

1. Auth0 as an external identity provider
2. Spring Security with role-based access controls for REST endpoints
3. A private S3 bucket for storing documents
4. S3 presigned URLs with time-bound access

Documentation requirements are fulfilled with OpenAPI with SwaggerDocs UI.

The project is decoupled into 5 distinct layers:

1. `domain` - for domain entities
2. `service` - for business logic concerns
3. `rs` - for REST layer concerns
4. `core` - for cross-cutting concerns like monitoring configuration
5. `test-core` - for tests cross-cutting concerns like Testcontainers configuration
6. `dto` - for internal dtos
7. `rs-dto` - for REST dtos

## Getting Started

Clone this project as a start.

### Integration Testing

Full end-to-end test coverage is implemented for `service` and `rs` layers.

For testing we use the following Testcontainers modules:

- Postgres for persistence needs
- Localstack for AWS S3 interactions

### Running All Automated Tests

```bash
./gradlew test
```

### Running Locally

The project can also be started up locally with each service running in a Docker container.

The application is configurable through the .env file. Below are some requires sources and configs:

**AWS Resources**

IAM - an IAM principal with access for pushing logs to CloudWatchLogs, pushing traces to AWS XRay and S3 getObject and PutObject access

S3 - an S3 bucket with name  `AWS_S3_STATEMENTS_BUCKET_NAME` (see .env file). Also configure the object key prefix with `AWS_S3_STATEMENTS_OBJECT_KEY_PREFIX`

CloudWatch (Optional) - a CloudWatch Logs group with name `CLOUDWATCH_LOG_GROUP` and a log stream with name `CLOUDWATCH_LOG_STREAM`

AWS XRay (Optional) - enable XRay from your AWS console

Credentials - configure a profile with credentials for the IAM principal with name `AWS_PROFILE_NAME`

If you?d like to create your resources outside `af-south-1` , then also configure:

- `AWS_REGION` for the region
- `AWS_S3_ENDPOINT` to match the region
- `LOGS_API_ENDPOINT` to match the region
- `TRACES_API_ENDPOINT` to match the region

**Datadog**

I personally prefer using Datadog as a full Observability tool so I included logs, metrics and traces exporter to Datadog. The only requirement here is a Datadog API key (the one included is revoked). These are the relevant configs:

- `DATADOG_API_KEY`
- `DATADOG_API_URL`

**Start Services**

Now we can start up all the services with

```bash
./start-locally.sh
```

This script builds the Docker images and starts up the containers. You should see:

```bash
[+] Building 2/2
 ? file-delivery-service  Built                                                                                                                                                                       0.0s 
 ? pg-db                  Built                                                                                                                                                                       0.0s 
[+] Running 3/3
 ? Container postgres               Healthy                                                                                                                                                           2.2s 
 ? Container otel-collector         Running                                                                                                                                                           0.0s 
 ? Container file-delivery-service  Started
```

You can access Swagger UI at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Usage

Visit Swagger UI at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

Retrieve an access token for test user

```bash
curl -s -X POST 'https://dev-dfr2z2zh4u525bbm.eu.auth0.com/oauth/token' \
-H 'Content-Type: application/json' \
-d '{
  "client_id": "gL4VVizyVfqIGCjZrPavM9iXGSuZtUVS",
  "client_secret": "AHQUWt4m29Q48AqElMJxVUSB6TTCkPqdrXYw8GiW-N34UXw4kffBs8gzfmZYgLZL",
  "audience": "https://api.filedelivery.app",
  "grant_type": "password",
  "username": "fokaziluvo@gmail.com",
  "password": "DB5D3hnXwu$!kKxF",
  "connection": "Username-Password-Authentication"
}' | jq -r '.access_token'
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InIxQzFKSmZ6M3FfcTd0LXZkUWhyRyJ9.eyJ1c2VyX2lkIjoiYXV0aDB8NjhkMzk5OGI5MzQ0Y2VkMjI1ZDEyYTU0IiwidXNlcl9yb2xlcyI6WyJDVVNUT01FUiIsIldPUktGT1JDRSJdLCJpc3MiOiJodHRwczovL2Rldi1kZnIyejJ6aDR1NTI1YmJtLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2OGQzOTk4YjkzNDRjZWQyMjVkMTJhNTQiLCJhdWQiOiJodHRwczovL2FwaS5maWxlZGVsaXZlcnkuYXBwIiwiaWF0IjoxNzU5MjMwMTk2LCJleHAiOjE3NTkzMTY1OTYsImd0eSI6InBhc3N3b3JkIiwiYXpwIjoiZ0w0VlZpenlWZnFJR0NqWnJQYXZNOWlYR1N1WnRVVlMifQ.irDSUPgPYONkYjBzq7xXvEpG6gRDfm75TddNSFiutlsLIwq000ecaaiLQT_sqdzqi805Zq3NqgUWrjJqs-I5vaa-Ttzf1NCNzKQqjixh3jEKnnAqc6oIr55WjVTvcRJBdWcje6z5eNbyALxCY_gOIuaOEHxFhClj7upuLv5RS-uQYEm43wfafyUtxCS37LxVl1z-cCxIbLisQkVo_vBu7UOtsCwOARfje8jMFKysC0tVuP-Fa2N8T_qP6xiWtbLppzK7DfuHnL3fqtjkM9-ixQ40FMhhBa9bAvUOYhqZZzxPTEhg2m_87nvOx5PTG_XbHzpPc2gvNcHY8LxR6ZSHhg
```

In Swagger UI, authorise requests by pasting the token from above.

**Upload a Statement (Admin)**

Supported with the  **`POST /v1/api/workforce/documents/customer/statements/upload`** endpoint.

Provide a customer Id as well as a file. Pre-seeded customer Ids are 2000 ? 2004 (see db/seed.sql).

The file is uploaded to the configured S3 Bucket. Upload path is not considered a critical path so it is performed async.

A sample statement test file generated with Claude Sonnet 4 is available at samples/monthly_statement.html

**List Statements (Admin)**

Supported with the  **`GET /v1/api/workforce/documents/customer/statements`** endpoint.

This is a listing API with pagination support. It offers a number of filters through the following parameters:

1. Customer Id
2. UserCreated Id
3. fromDate and toDate data range

**Download Statement (Admin)**

Supported with the **`POST /v1/api/workforce/documents/customer/statements/{statementId}/download`** endpoint.

This endpoint will create an S3 presigned URL for the given **`statementId`** . The presigned URL is valid for 7 days by default, however, this can be overriden by providing the **`durationInMillis`** field.

The response contains the the S3 presigned URL which is shared with the customer:

```bash
{
  "url": "https://lfokazi-documents-customer-statements-af.s3.af-south-1.amazonaws.com/customer/statements/2002/1759176497681-monthly_statement.html?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250930T111910Z&X-Amz-SignedHeaders=host&X-Amz-Credential=AKIA6K5V756XBTYGTU6Y%2F20250930%2Faf-south-1%2Fs3%2Faws4_request&X-Amz-Expires=30&X-Amz-Signature=89a2a605e9b9c9dccd21be25603231d4014d16f146c2adf38f76cded8ba1d22e",
  "duration": "PT30S",
  "expiresAt": "2025-09-30T11:19:40.006287132"
}
```

The customer can access the document for the specified duration. Once expired you?ll see:

```
This XML file does not appear to have any style information associated with it. The document tree is shown below.
<Error>
<Code>AccessDenied</Code>
<Message>Request has expired</Message>
<X-Amz-Expires>30</X-Amz-Expires>
<Expires>2025-09-30T11:19:40Z</Expires>
<ServerTime>2025-09-30T11:20:42Z</ServerTime>
<RequestId>Y5XM80XJ62N22PJ8</RequestId>
<HostId>GxNh51avnhiEXRj9O1/e+RFr/3lbnRVyGZck1wh287lvPMkEfXTpZhs3gvbTYI98LQ5cGENSM83xcFRDZurXErhV/d+AzQGG</HostId>
</Error>
```

**Downloads Auditing**

There are two options which can be used for auditing purposes:

1. List Download Requests through the **`GET /v1/api/workforce/documents/customer/statements/downloads`** endpoint
    1. This supports filters by customer id, statement id and admin id who requested the download link
2. List Download Requests through the **`GET /v1/api/workforce/documents/customer/statements/{statementId}/downloads`** endpoint
    1. This will list download requests for a given statement id
3. AWS CloudTrail
    1. With CloudTrail Logs we can track up to the customer?s use of the S3 presigned URL to download the document.
    2. This is more of a source of truth for actual download while the downloads in `statement_download` table only shows requests into the system for a download link. I?ve considered these records as a proxy for actual downloads.
    3. Direct CloudTrail querying from the application is not currently implemented. If that were to be required we could add such functionality.
