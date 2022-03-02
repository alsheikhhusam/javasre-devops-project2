pipeline {
  environment {
    registry = 'timhansz/weather-api'
    dockerHubCreds = 'docker_hub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Test') {
    when  { anyOf {
      branch 'feature/*'
      branch 'dev'
      }
    }
      steps {
        dir('API1/WeatherAPI') {
          withMaven {
            sh 'mvn test'
          }
            }
            dir('API2/Twilio-api') {
              withMaven {
                  sh 'mvn test'
              }
            }
            dir('API3/QueryAPI') {
              withMaven {
                  sh 'mvn test'
              }
            }
      }
    }
    stage('Build') {
      when { anyOf {
        branch 'feature/*'
        branch 'dev'
        branch 'main'
        }
      }
        steps {
          dir('API1/WeatherAPI') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
          dir('API2/Twilio-api') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
          dir('API3/QueryAPI') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
    }
    stage('Docker Build') {
        when { anyOf {
        branch 'main'
        }
      }
        steps {
          dir('API1/WeatherAPI') {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build "$registry"
            }
          }
          dir('API2/Twilio-api') {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build "$registry"
            }
          }
          dir('API3/QueryAPI') {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build "$registry"
            }
          }
        }
    }
  }
}
