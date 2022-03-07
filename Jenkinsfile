pipeline {
  environment {
    api1Registry = 'husamalsheikh/weatherapi'
    api2Registry = 'husamalsheikh/twilioapi'
    api3Registry = 'husamalsheikh/queryapi'
    dockerHubCreds = 'docker_hub'
    dockerImage1 = ''
    dockerImage2 = ''
    dockerImage3 = ''
  }
  agent any
  stages {

    stage('Test') {
    when  { anyOf {
      branch 'feature/*'
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

    stage('Maven Package') {
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
          branch 'dev'
        }
      }
        steps {
          dir('API1/WeatherAPI') {
            script {
                echo "$api1Registry:$currentBuild.number"
                dockerImage1 = docker.build "$api1Registry"
            }
          }
          dir('API2/Twilio-api') {
            script {
                echo "$api2Registry:$currentBuild.number"
                dockerImage2 = docker.build "$api2Registry"
            }
          }
          dir('API3/QueryAPI') {
            script {
                echo "$api3Registry:$currentBuild.number"
                dockerImage3 = docker.build "$api3Registry"
            }
          }
        }
    }

    stage('Docker Push') {
      when { anyOf {
        branch 'dev'
        branch 'main'
        }
      }
      steps {
        dir('API1/WeatherAPI') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage1.push("latest")
            }
          }
        }
        dir('API2/Twilio-api') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage2.push("latest")
            }
          }
        }
        dir('API3/QueryAPI') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage3.push("latest")
            }
          }
        }
      }
    }

    stage('Notify Discord') {
      when {
        branch 'dev'
        branch 'main'
      }
      steps {
        discordSend description: "Build #$currentBuild.number",
          link: BUILD_URL, result: currentBuild.currentResult,
          title: JOB_NAME,
          webhookURL: "https://discord.com/api/webhooks/949839260050141205/rJ48IDNgUUpKpIgePlV97ieIPf4srG73pv9ZUSGcgf-g0Hp5Zzm4aSNGv6m0lOwDd-SJ"
      }
    }

    stage('Wait for SRE approval to Deploy') {
      when {
        branch 'dev'
      }
      steps {
        script {
          try {
            timeout(time: 60, unit: 'MINUTES') {
              approved = input message: 'Deploy to production?', ok: 'Continue',
              parameters: [choice(name: 'Approved', choices: 'Yes\nNo', description: 'Deploy build to production')]
              if(approved != 'Yes') {
                error('Build did not pass approval')
              }
            }
          } catch(error) {
            error('Build failed because timout was exceeded.')
          }
        }
      }
    }

    stage('Deploy to GKE') {
        when {
            branch 'dev'
        }
        steps{
           sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./K8s/deployment.yaml'
           sh 'cat ./K8s/deployment.yaml'
            step([$class: 'KubernetesEngineBuilder',
                projectId: 'windy-album-339219',
                clusterName: 'project2-gke',
                zone: 'us-central1',
                manifestPattern: 'K8s/',
                credentialsId: 'project2-gke',
                verifyDeployments: true
            ])
        }
    }

    stage('Clean Workspace') {
      steps {
        cleanWs()
      }
    }
  }
}