#!/bin/sh

# Configuration
NEXUS_URL="http://nexus:8081"  # Use service name inside Docker network
ADMIN_USER="admin"
ADMIN_PASS="admin123"
REPO_NAME="java-shared-classes"

# Wait for Nexus to become ready
echo "⏳ Waiting for Nexus to start..."
until curl -s -o /dev/null -w "%{http_code}" -u "$ADMIN_USER:$ADMIN_PASS" "$NEXUS_URL/service/rest/v1/status" | grep -q '^200$'; do
  echo "Nexus not ready yet, retrying in 5 seconds..."
  sleep 5
done
echo "✅ Nexus is running."

# Check if repo already exists
REPO_EXISTS=$(curl -s -u "$ADMIN_USER:$ADMIN_PASS" "$NEXUS_URL/service/rest/v1/repositories" | grep "\"name\":\"$REPO_NAME\"")

if [ -n "$REPO_EXISTS" ]; then
  echo "ℹ️ Repository '$REPO_NAME' already exists. Skipping creation."
else
  echo "🔧 Creating Maven hosted repository: $REPO_NAME"

  curl -s -o /dev/null -w "%{http_code}" \
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
        \"versionPolicy\": \"RELEASE\",
        \"layoutPolicy\": \"PERMISSIVE\"
      }
    }"

  echo ""
  echo "✅ Repository '$REPO_NAME' created."
fi
