pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        dir('KubernetesProjectDemo') {
          withMaven {
            sh 'mvn test'
          }
        }
      }
    }

  }
}
