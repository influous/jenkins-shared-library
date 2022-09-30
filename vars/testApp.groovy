#!/usr/bin/env groovy

def call() {
    echo 'Testing application...'
    echo "Executing pipeline on branch $env.BRANCH_NAME"
}