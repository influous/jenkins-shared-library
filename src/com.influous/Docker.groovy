#!/usr/bin/env groovy
package com.influous

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.echo 'Building Docker image'
        script.withCredentials[usernamePassword(script.credentialsId: 'docker-hub-repo', script.usernameVariable: 'USER', script.passwordVariable: 'PASSWORD')]) {
            script.sh "docker build -t $imageName . "
            script.sh "echo '${script.PASSWORD}' | docker login -u $script.USER --password-stdin"
            script.sh "docker push $imageName"
        }
    }

}
