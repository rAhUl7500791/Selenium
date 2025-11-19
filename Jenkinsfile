pipeline {
    agent any

    triggers {
        cron('0 21 * * *')
        githubPush()
    }

    stages {

        stage('Smoke Tests') {
            steps {
                bat 'mvn clean test -DsuiteXmlFile=testng.xml -Dgroups=smoke'
            }
        }

        stage('Regression Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng.xml -Dgroups=regression'
            }
        }

        stage('Publish Test Reports') {
            steps {

                // JUnit XML results
                junit 'target/surefire-reports/*.xml'

                // Publish TestNG HTML Report
                publishHTML([
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])

                // (If you use Extent Reports)
                archiveArtifacts artifacts: 'reports/**', followSymlinks: false
            }
        }
    }
}
