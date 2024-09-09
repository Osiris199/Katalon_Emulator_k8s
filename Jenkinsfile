pipeline {

  environment {
    HOME = "${env.WORKSPACE}"
    MY_SECRET_KEY = credentials('Katalon_API_key')
    imageCheck = sh(script: 'docker search --format "{{.Name}}" vaibhavx7/android-emulator', returnStdout: true).trim()
    dockerimagename = "vaibhavx7/android-emulator"
    dockerImage = ""
  }
  
  agent any

  parameters {
        string(name: 'TEST_SUITE', defaultValue: '', description: 'Name of test suite to be executed')
	string(name: 'TYPE_OF_TEST', defaultValue: '', description: 'Type of test cases to be executed')
	string(name: 'EXEC_PROFILE', defaultValue: '', description: 'Test execution profile')
	string(name: 'PROJECT_NAME', defaultValue: '', description: 'Name of project to execute')
  }

  stages {

    stage('SCM Checkout') {
      steps{
        script {
	  echo "$MY_SECRET_KEY"
	  git branch: 'master', credentialsId: 'Github_cred', url: 'https://github.com/Osiris199/Katalon_Emulator_k8s.git'
        }
      }
    }

    stage('Build image') {
       when {
	        expression {
	           return !(env.imageCheck == "vaibhavx7/android-emulator")
	        }
        } 
	steps {
               script {
          	   dockerImage = docker.build(dockerimagename, "--build-arg TEST_SUITE=\"${params.TEST_SUITE}\" --build-arg TYPE_OF_TEST=\"${params.TYPE_OF_TEST}\" --build-arg EXEC_PROFILE=\"${params.EXEC_PROFILE}\" --build-arg PROJECT_NAME=\"${params.PROJECT_NAME}\" -f ${env.WORKSPACE}/Dockerfile_Android .")
        	}
        }
    }

    stage('Pushing Images') {
       environment {
        registryCredential = 'Docker_Hub_cred'
       }
       when {
	        expression {
	           return !(env.imageCheck == "vaibhavx7/android-emulator")
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

}
