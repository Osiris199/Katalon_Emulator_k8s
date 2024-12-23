pipeline {

  environment {
    HOME = "${env.WORKSPACE}"
    MY_SECRET_KEY = credentials('Katalon_API_key')
    dockerimagename = "vaibhavx7/android-emulator"
    EMAIL_RECIPIENT = 'osiris007x@gmail.com'
    FILE_TO_ATTACH = 'Reports/*.html'
    dockerImage = ""
  }
  
  agent any

  parameters {
        string(name: 'TEST_SUITE', defaultValue: '', description: 'Name of test suite to be executed')
	string(name: 'TYPE_OF_TEST', defaultValue: '', description: 'Type of test cases to be executed')
	string(name: 'EXEC_PROFILE', defaultValue: '', description: 'Test execution profile')
	string(name: 'PROJECT_NAME', defaultValue: '', description: 'Name of project to execute')
	string(name: 'API_KEY', defaultValue: '$MY_SECRET_KEY', description: 'API key')
	  
  }

  stages {
    stage('SCM Checkout') {
      steps{
        script {
	  git branch: 'master', credentialsId: 'Github_cred', url: 'https://github.com/Osiris199/Katalon_Emulator_k8s.git'
	  checkout scm
        }
      }
    }
	  
  //   stage('Check Docker Image') {
  //   steps {
  //       script {
		// def imageCheckRaw 
		// try{
		//    imageCheckRaw = sh(script: 'docker search --format "{{.Name}}" vaibhavx7/android-emulator | grep "^vaibhavx7/android-emulator$"', returnStdout: true)
		//    echo "imageCheckRaw ${imageCheckRaw}"
		// } catch (Exception e) {
  //                  echo "Error occurred: ${e.message}"
  //                  env.imageCheck = ""
  //           	}
		
	 //        if (imageCheckRaw instanceof String && imageCheckRaw != "") {
	 //          def imageCheck = imageCheckRaw.trim() 
  //                 echo "imageCheck result: ${imageCheck}"
	 //          env.imageCheck = imageCheck
	 //       } else {
  //                 echo "No image found for vaibhavx7/android-emulator."
  //                 env.imageCheck = ""
  //              }
  //       }
  //    }
  //  }

    stage('Build image') {
       when {
	        expression {
		   // return !(env.imageCheck == "vaibhavx7/android-emulator") && currentBuild.changeSets.size() > 0
		   return currentBuild.changeSets.size() > 0	
	        }
        } 
	steps {
               script {
          	   dockerImage = docker.build(dockerimagename, "--build-arg TEST_SUITE=\"${params.TEST_SUITE}\" --build-arg TYPE_OF_TEST=\"${params.TYPE_OF_TEST}\" --build-arg API_KEY=\"${params.API_KEY}\" --build-arg EXEC_PROFILE=\"${params.EXEC_PROFILE}\" --build-arg PROJECT_NAME=\"${params.PROJECT_NAME}\" -f ${env.WORKSPACE}/Dockerfile_Android .")
        	   echo "Docker image built: ${dockerImage}"
	       }
        }
    }

    stage('Pushing Images') {
       environment {
        registryCredential = 'Docker_Hub_cred'
       }
       when {
	        expression {
	          // return !(env.imageCheck == "vaibhavx7/android-emulator") && currentBuild.changeSets.size() > 0
		  return currentBuild.changeSets.size() > 0
	        }
        }
	steps {
                script {
            	   docker.withRegistry( 'https://registry.hub.docker.com', registryCredential ) {
            	   dockerImage.push("latest")
          	  }
        	}
        }
    }

    stage('Deploying katalon and android emulator container to Kubernetes') {
      steps {
        script {
		sh "whoami"
	        withCredentials([file(credentialsId: 'Kubeconfig_file', variable: 'KUBECONFIG')]) {
    	        sh '''minikube kubectl -- apply -f deployment.yaml'''
		sh '''minikube kubectl -- apply -f android-service.yaml'''
                sh '''minikube kubectl -- apply -f vnc-service.yaml'''
		sh '''minikube kubectl -- apply -f hpa.yaml'''
          }
        }
      }
    }
	
     stage('VNC Port Forwarding') {
	steps {
	  script {
		  sh "chmod +x -R ${env.WORKSPACE}"
              	  sh "sudo -u siddhatech ./portforward_vnc.sh"
	  }
        }
     }

    stage('Check case status and Terminate pod') {
	steps {
	   script {
		   sh "chmod +x -R ${env.WORKSPACE}"
          	   sh "sudo -u siddhatech ./delete_pod.sh"
	}
      }
    }
  }

  post {
        // always {
        //     mail to: 'osiris007x@gmail.com',
        //          subject: "Build ${currentBuild.fullDisplayName} completed",
        //          body: "The build ${currentBuild.fullDisplayName} has completed.\n\n" +
        //                "Result: ${currentBuild.currentResult}\n" +
        //                "Check the build details at: ${env.BUILD_URL}"
        // }
	always {
            emailext (
                to: "${EMAIL_RECIPIENT}",
                subject: "Build #${currentBuild.number} - ${currentBuild.result}",
                body: """
                    Hello Team, 
                    
                    The build #${currentBuild.number} has finished with status: ${currentBuild.result}.
                    
                    Please find the attached report.
                """,
                attachmentsPattern: "${FILE_TO_ATTACH}",  // Attach the file from the specified location
                mimeType: 'text/html'
            )
        }  
    }
	
}
