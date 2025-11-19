pipeline {
    agent any

    triggers {
        cron('0 21 * * *')    // 9 PM daily
        githubPush()          // on every push
    }

    stages {

        stage('Smoke Tests') {
            steps {
                bat 'mvn clean test -Dgroups=smoke'
            }
        } 

        stage('Regression Tests') {
            steps {
                bat 'mvn test -Dgroups=regression'
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
