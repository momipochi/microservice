#!/bin/sh

set -e

NEXUS_URL="http://nexus:8081"
REPO_NAME="java-shared-classes"
REPO_CHECK_URL="$NEXUS_URL/repository/$REPO_NAME/"

echo "⏳ Waiting for Nexus to be ready at $NEXUS_URL..."

# Wait for Nexus to be up
until curl -s --head --fail "$NEXUS_URL" > /dev/null; do
  echo "🔄 Nexus not reachable yet..."
  sleep 5
done

echo "✅ Nexus is reachable."

# Wait until the repository responds with HTTP 200
echo "⏳ Waiting for repository $REPO_NAME to be available..."

until curl -s --head --fail "$REPO_CHECK_URL" | grep "200 OK" > /dev/null; do
  echo "🔄 Waiting for repository '$REPO_NAME'..."
  sleep 5
done

echo "✅ Repository '$REPO_NAME' is ready."

# Run Maven build
echo "🚀 Starting Maven build..."
cd /build
mvn clean deploy -DskipTests -X
