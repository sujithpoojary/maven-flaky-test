pipeline {
    agent any
    options {
        timestamps()
        ansiColor('xterm')
    }
    tools {
        maven 'maven'
    }
    environment {
        JAVA_HOME = tool name: 'jdk17', type: 'jdk'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Setup') {
            steps {
                sh 'java -version'
                sh 'mvn -v'
            }
        }
        stage('Test') {
            steps {
                // Run tests; Surefire reruns failing tests 2 times to help identify flakes
                sh 'mvn clean install -DskipITs -Dsurefire.rerunFailingTestsCount=5'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true, testDataPublishers: [junitFlakyTestPublisher()]
                    archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml', allowEmptyArchive: true
                }
            }
        }
    }
    post {
        success {
            echo 'Build & tests succeeded.'
        }
        failure {
            echo 'Build failed or tests failed.'
        }
    }
}
