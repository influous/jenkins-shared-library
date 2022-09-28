#!/usr/bin/env groovy

def call() {
    echo "Building the application (JAR) v${NEWEST_VERSION}..."
    sh 'mvn package'
}
