pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/dockersamples/example-voting-app'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'docker-compose run --rm tests'
            }
        }

        stage('Deploy') {
            steps {
                sshagent(['YOUR_GCP_SSH_CREDENTIALS']) {
                    sh 'scp -r * YOUR_GCP_INSTANCE_IP:/path/to/app'
                    sh 'ssh YOUR_GCP_INSTANCE_IP "docker-compose up -d"'
                }
            }
        }
    }

    post {
        failure {
            sh 'ssh YOUR_GCP_INSTANCE_IP "docker-compose down"'
            sh 'ssh YOUR_GCP_INSTANCE_IP "docker-compose up -d"'
        }
    }
}
