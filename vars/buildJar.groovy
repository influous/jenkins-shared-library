#!/usr/bin/env groovy

def call() {
    echo "Building the application (JAR) v${params.VERSION}..."
    sh 'mvn clean package'
}
