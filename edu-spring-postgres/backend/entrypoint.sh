#!/bin/sh
set -e

if [ -f /run/secrets/db-password ]; then
  export POSTGRES_PASSWORD="$(cat /run/secrets/db-password)"
fi

exec java -cp "app:app/lib/*" com.company.project.Application
