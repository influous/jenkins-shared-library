#!/usr/bin/env groovy

def call() {
    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'PASSWORD', usernameVariable: 'USER')]) {
    sh 'git config --global user.email "jenkins@kook.work"'
    sh 'git config --global user.name "jenkins"'
    sh "git remote set-url origin https://${PASSWORD}@github.com/${USER}/devops-maven.git"
    sh 'git add .'
    sh 'git commit -m "CI: Version bump"'
    sh 'git push origin HEAD:jenkins-jobs'
    }
}
