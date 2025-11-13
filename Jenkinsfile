pipeline {
  agent any
  options {
    timestamps()
    ansiColor('xterm')
  }
  environment {
    JAVA_HOME = tool name: 'jdk17', type: 'jdk'
    PATH = "${JAVA_HOME}/bin:${env.PATH}"
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Setup') {
      steps {
        sh 'java -version'
        sh 'mvn -v'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn -B -e test'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
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

