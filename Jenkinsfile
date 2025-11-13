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
        // Set to true to activate demo flaky tests (can also be overridden in Jenkins job configuration)
        FLAKY_DEMO = 'true'
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
                sh 'mvn -B clean test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml', allowEmptyArchive: true
                    // Flaky Test Handler plugin: parse JUnit XML and mark tests that pass on rerun as flaky
                    // Requires plugin: https://plugins.jenkins.io/flaky-test-handler/
                    flakyTestIncludePattern(testResultsPattern: 'target/surefire-reports/*.xml')
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
