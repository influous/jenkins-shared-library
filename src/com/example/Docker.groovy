#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildImage(String imageBuild, String imageLatest) {
        script.echo 'Building Docker image'
        script.sh "docker build -t $imageBuild -t $imageLatest ."
    }

    def dockerLogin() {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
            script.sh "echo '${script.PASSWORD}' | docker login -u ${script.USER} --password-stdin"
        }
    }

    def dockerPush(String imageBuild, String imageLatest) {
        script.sh "docker push $imageBuild"
        script.sh "docker push $imageLatest"
        script.sh "docker rmi $imageBuild"
    }
    
}