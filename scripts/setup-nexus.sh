#!/bin/sh
# Configuration
NEXUS_URL="http://nexus:8081"
ADMIN_USER="admin"
ADMIN_PASS="admin123"
REPO_NAME="java-shared-classes"
# Wait for Nexus to become ready
echo "ý Waiting for Nexus to start..."
until curl -s -o /dev/null -w "%{http_code}" -u "$ADMIN_USER:$ADMIN_PASS" "$NEXUS_URL/service/rest/v1/status" | grep -q '^200$'; do
  echo "Nexus not ready yet, retrying in 5 seconds..."
  sleep 5
done
echo " Nexus is running."
echo "= Ensuring required realms are enabled..."
curl -s -u "$ADMIN_USER:$ADMIN_PASS" -X PUT "$NEXUS_URL/service/rest/v1/security/realms/active" \
  -H "Content-Type: application/json" \
  -d '[
        "NexusAuthenticatingRealm",
        "DefaultRole"
      ]'
echo " Security realms configured."
# Check if repo already exists
REPO_EXISTS=$(curl -s -u "$ADMIN_USER:$ADMIN_PASS" "$NEXUS_URL/service/rest/v1/repositories" | grep "\"name\":\"$REPO_NAME\"")
if [ -n "$REPO_EXISTS" ]; then
  echo "9 Repository '$REPO_NAME' already exists. Skipping creation."
else
  echo "=' Creating Maven hosted SNAPSHOT repository: $REPO_NAME"
  curl -s -o /dev/null -w "\nHTTP Status: %{http_code}\n" \
    -u "$ADMIN_USER:$ADMIN_PASS" \
    -X POST "$NEXUS_URL/service/rest/v1/repositories/maven/hosted" \
    -H "Content-Type: application/json" \
    -d "{
      \"name\": \"$REPO_NAME\",
      \"online\": true,
      \"storage\": {
        \"blobStoreName\": \"default\",
        \"strictContentTypeValidation\": true,
        \"writePolicy\": \"ALLOW\"
      },
      \"maven\": {
        \"versionPolicy\": \"SNAPSHOT\",
        \"layoutPolicy\": \"PERMISSIVE\"
      },
      \"cleanup\": {
        \"policyNames\": []
      }
    }"
  echo " Repository '$REPO_NAME' created."
fi
echo "Accepting eula agreement"
EULA_JSON=$(curl -u admin:admin123 -s -X GET http://nexus:8081/service/rest/v1/system/eula)
DISCLAIMER=$(echo "$EULA_JSON" | jq -r '.disclaimer')
echo "$DISCLAIMER"
curl -u admin:admin123 -X POST http://nexus:8081/service/rest/v1/system/eula \
  -H "Content-Type: application/json" \
  -d "{\"disclaimer\":\"$DISCLAIMER\",\"accepted\":true}"