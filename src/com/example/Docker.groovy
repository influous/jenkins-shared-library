#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildImage(String imageName) {
        script.echo 'Building Docker image'
        script.withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
        script.sh "docker build -t $imageName ."
        script.sh "echo '${PASSWORD}' | docker login -u $USER --password-stdin"
        script.sh "docker push $imageName"
        }
    }

    def dockerLogin() {
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
            script.sh "echo '${script.PASSWORD}' | docker login -u $script.USER --password-stdin"
        }
    }

    def dockerPush(String imageName) {
        script.sh "docker push $imageName"
    }
    
}