#!/bin/bash

# Default message indicating docker-compose up -d
message="Docker Compose services have been started without rebuilding."

# Parse command line arguments
while getopts "b" opt; do
  case ${opt} in
    b )
      build=true
      message="Docker Compose services have been started with rebuilding."
      ;;
    \? )
      echo "Usage: $0 [-b]" >&2
      exit 1
      ;;
  esac
done
shift $((OPTIND -1))

# Run docker-compose up
if [ "$build" = true ]; then
  docker-compose up -d --build
else
  docker-compose up -d
fi

# Print the message indicating the action taken
echo "$message"
