#!/usr/bin/env groovy

def call(String imageName) {
    return new Docker(this).buildDockerImage(imageName)
}