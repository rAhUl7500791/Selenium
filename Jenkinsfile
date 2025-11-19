pipeline {
    agent any

    triggers {
        cron('0 21 * * *')        // daily 9 PM
        githubPush()              // on every push
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

        stage('Publish Test Reports') {
            steps {

                // JUnit XML reports (for Jenkins pie chart)
                junit 'target/surefire-reports/*.xml'

                // Publish TestNG HTML Report
                publishHTML([
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report'
                ])

                // Extent Report (optional)
                archiveArtifacts artifacts: 'reports/**', followSymlinks: false
            }
        }
    }
}
