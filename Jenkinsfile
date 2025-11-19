pipeline {
    agent any

    triggers {
        cron('0 21 * * *')     // Runs every night 9 PM
        githubPush()           // Runs on every push
    }

    stages {

        stage('Smoke Tests') {
            steps {
                sh 'mvn clean test -Dgroups=smoke'
            }
        }

        stage('Regression Tests') {
            steps {
                sh 'mvn test -Dgroups=regression'
            }
        }

        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report'
                ])
            }
        }
    }
}
