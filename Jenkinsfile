pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        dir('API 1') {
          withMaven {
            sh 'mvn test'
          }
        }
      }
    }

  }
}
