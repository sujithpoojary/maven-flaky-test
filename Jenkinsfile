pipeline {
<<<<<<< HEAD
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
=======
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

>>>>>>> 3ad9b0c (Initial Commit)
