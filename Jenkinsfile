pipeline {
    agent any

    triggers {
        cron('0 21 * * *')
        githubPush()
    }

    stages {

        stage('Smoke Tests') {
            steps {
                echo "Running Smoke Suite..."
                bat 'mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dgroups=smoke -DoutputDir=test-output-smoke'
            }
        }

        stage('Regression Tests') {
            steps {
                echo "Running Regression Suite..."
                bat 'mvn test -Dsurefire.suiteXmlFiles=testng.xml -Dgroups=regression -DoutputDir=test-output-regression'
            }
        }

        stage('Publish Test Reports') {
            steps {

                // JUnit XML results for Jenkins
                junit 'target/surefire-reports/*.xml'

                // Smoke HTML Report
                publishHTML([
                    reportDir: 'test-output-smoke',
                    reportFiles: 'index.html',
                    reportName: 'Smoke Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])

                // Regression HTML Report
                publishHTML([
                    reportDir: 'test-output-regression',
                    reportFiles: 'index.html',
                    reportName: 'Regression Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])

                // Extent Report (optional)
                archiveArtifacts artifacts: 'target/extent-report.html', followSymlinks: false
            }
        }
    }
}
