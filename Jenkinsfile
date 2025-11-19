pipeline {
    agent any

    triggers {
        // GitHub auto trigger
        githubPush()

        // Daily schedule — 9PM IST (3:30 PM UTC)
        cron('30 15 * * *') 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Dependencies') {
            steps {
                sh "mvn -version"
                sh "mvn clean install -DskipTests"
            }
        }

        stage('Run Smoke Suite') {
            steps {
                echo "Running Smoke Tests"
                sh "mvn test -DsuiteXmlFile=testng.xml -Dgroups=smoke"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Run Regression Suite') {
            steps {
                echo 'Running Regression Tests'
                sh "mvn test -DsuiteXmlFile=testng.xml -Dgroups=regression"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**', fingerprint: true
        }
    }
}
