#!/bin/sh

ACCESS_TOKEN=$(curl -v -s -X POST 'https://dev-dfr2z2zh4u525bbm.eu.auth0.com/oauth/token' \
-H 'Content-Type: application/json' \
-d '{
  "client_id": "gL4VVizyVfqIGCjZrPavM9iXGSuZtUVS",
  "client_secret": "AHQUWt4m29Q48AqElMJxVUSB6TTCkPqdrXYw8GiW-N34UXw4kffBs8gzfmZYgLZL",
  "audience": "https://api.filedelivery.app",
  "grant_type": "password",
  "username": "fokaziluvo@gmail.com",
  "password": "DB5D3hnXwu$!kKxF",
  "connection": "Username-Password-Authentication"
}' | jq -r '.access_token')

if [ -z "$ACCESS_TOKEN" ]; then
    echo "❌ Failed to get access token"
    exit 1
fi

echo "✅ Access Token retrieved successfully"
echo "================================================"
echo ""

FILE_PATH=$(find -d "$PWD/monthly_statement.html" | head -n 1)

echo "File to upload: $FILE_PATH"

# Upload customer statement
echo "📤 Upload customer statement (ID: 2002)"
echo "================================================"
response=$(curl -s -w "\n%{http_code}" -X POST --location 'http://localhost:8080/v1/api/workforce/documents/customer/statements/upload?customerId=2002' \
--header "Authorization: Bearer ${ACCESS_TOKEN}" \
--form "fileData=@$FILE_PATH")

echo "Response: $response"

http_code=$(echo "$response" | tail -n1)
body=$(echo "$response" | sed '$d')

echo "Status: $http_code"
echo "Response:"
if [ -z "$body" ]; then
    echo "(empty response)"
else
    echo "$body" | jq . 2>/dev/null || echo "$body"
fi
echo ""