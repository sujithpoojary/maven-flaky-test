pipeline {
  agent any
  options { timestamps(); ansiColor('xterm') }
  tools { maven 'maven' }

  parameters {
    string(name: 'test', defaultValue: '', description: 'Optional Surefire selector (e.g. MyTest#method or com.foo.*)')
    string(name: 'RETRY_COUNT', defaultValue: '2', description: 'Number of selective rerun attempts')
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

    stage('Test (Surefire Deflake: selective)') {
      steps {
        script {
          int maxRetries = (params.RETRY_COUNT ?: '2') as int
          String baseFlags = params.test?.trim() ? "-Dtest='${params.test.trim()}'" : ""
          boolean passed = false

          // 1) Initial full run (discover failures)
          echo "🧪 Attempt #1: full suite"
          int rc = sh(script: "mvn -B clean test ${baseFlags}", returnStatus: true)
          junit testResults: 'target/surefire-reports/*.xml',
                allowEmptyResults: true,
                testDataPublishers: [junitFlakyTestPublisher()]
          if (rc == 0) { passed = true }

          // Helper: collect failed tests as Class#method (Perl-based, sandbox-safe)
          def collectFailedSelectors = {
            String out = sh(
              script: '''
                set -eu
                tmp="target/surefire-reports/.failed-selectors.txt"
                : > "$tmp"
                for f in target/surefire-reports/TEST-*.xml; do
                  [ -f "$f" ] || continue
                  perl -0777 -ne '\''
                    while (m{<testcase\\b([^>]*)>.*?(?:<failure\\b|<error\\b).*?</testcase>}sg) {
                      my $a = $1;
                      my ($c) = $a =~ /classname="([^"]+)"/;
                      my ($n) = $a =~ /name="([^"]+)"/;
                      if (defined $c && defined $n) { print "$c#$n\\n" }
                    }
                  '\'' "$f" >> "$tmp"
                done
                if [ -s "$tmp" ]; then
                  sort -u "$tmp"
                fi
              ''',
              returnStdout: true
            ).trim()
            return out ? out.readLines() : []
          }

          // 2) Selective reruns
          int attempt = 2
          while (!passed && attempt <= maxRetries + 1) {
            def failedSelectors = collectFailedSelectors()
            if (!failedSelectors || failedSelectors.isEmpty()) {
              echo "✅ No failed tests found to rerun."
              passed = true
              break
            }

            String selectorArg = "-Dtest='" + failedSelectors.join(",") + "'"
            echo "🧪 Attempt #${attempt}: rerunning failed tests only (${failedSelectors.size()} selectors)"
            // IMPORTANT: no 'clean' here — keep reports for parsing
            rc = sh(script: "mvn -B test ${selectorArg}", returnStatus: true)
            junit testResults: 'target/surefire-reports/*.xml',
                  allowEmptyResults: true,
                  testDataPublishers: [junitFlakyTestPublisher()]
            if (rc == 0) {
              passed = true
              break
            }
            attempt++
          }

          if (!passed) {
            error("❌ Tests failed after ${maxRetries + 1} attempts (including selective reruns).")
          }
        }
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml',
                            allowEmptyArchive: true
        }
      }
    }
  }

  post {
    success { echo 'Build & tests succeeded (Selective deflake applied).' }
    failure { echo 'Build failed or tests still failed after selective retries.' }
  }
}
