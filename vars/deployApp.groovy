#!/usr/bin/env groovy

def call() {
    echo "Deploying to EC2 instance on ${env.EC2_ADDRESS}"
    def shellCmds = "bash ./server_cmds.sh ${env.IMAGE_LATEST}"
    sshagent(['ec2-ssh-key']) {
    // -o flag avoids SSH popup
    sh "scp -o StrictHostKeyChecking=no server_cmds.sh ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_ADDRESS} ${shellCmds}"
    }

    if(env.BRANCH_NAME == 'feature/deploy-on-k8s') {
        echo 'Deploying Docker image...'
        withKubeConfig([credentialsId: 'lke-credentials', serverUrl: 'https://ab39570d-0578-4bb9-acb2-d06ce32ba0fd.eu-central-1.linodelke.net:443']) {
            withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                sh "kubectl create secret docker-registry my-registry-key --docker-server=docker.io --docker-username=$USER --docker-password=$PASSWORD"
            }
            sh 'envsubst < kubernetes/deployment.yaml | kubectl apply -f -'
        }
    } catch (Exception e) {
        echo "An exception occurred: " + e.toString()   
        }
    }
}
