pipeline {

  environment {
    HOME = "${env.WORKSPACE}"
    MY_SECRET_KEY = credentials('Katalon_API_key')
    dockerimagename = "vaibhavx7/android-emulator"
    EMAIL_RECIPIENT = "vaibhavp@siddhatech.com, arsheenk@siddhatech.com, sudarshanmali@siddhatech.com"
    FILE_TO_ATTACH = '/home/siddhatech/Reports/'
    WORKSPACE_REPORT_DIR = "${WORKSPACE}/Reports"
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
	  git branch: 'master', credentialsId: 'Osiris199_github', url: 'https://github.com/Osiris199/Katalon_Emulator_k8s.git'
	  checkout scm
        }
      }
    }
	  
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
	  
    stage('Copy Reports to workspace') {
            steps {
                sh 'mkdir -p ${WORKSPACE_REPORT_DIR}'
                sh 'find ${FILE_TO_ATTACH} -name "*.html" -exec cp {} ${WORKSPACE_REPORT_DIR}/ \\;'
            }
    }
  }

  post {
    always {
        emailext (
            to: "${EMAIL_RECIPIENT}",
            subject: "Build Notification: #${currentBuild.number} - ${currentBuild.result}",
            body: """
                <html>
                    <body style="font-family: Arial, sans-serif; color: #333; padding: 20px;">
                        <!-- Logo Section -->
                        <div style="text-align: center; margin-bottom: 30px;">
                            <img src="https://www.siddhatech.com/wp-content/uploads/Logo.png" style="width: 150px;">
                        </div>
                        
                        <h2 style="color: #2E86C1;">🚀 Build Notification</h2>
                        
                        <!-- Table Section -->
                        <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
                            <tr style="background-color: #f2f2f2;">
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Project</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px;">K8s Katalon Emulator</td>
                            </tr>
                            <tr>
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Build Number</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px;">#${currentBuild.number}</td>
                            </tr>
                            <tr>
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Status</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px; color: ${currentBuild.result == 'SUCCESS' ? 'green' : 'red'};">${currentBuild.result}</td>
                            </tr>
                            <tr>
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Triggered By</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px;">${env.BUILD_USER ?: 'Automated Trigger'}</td>
                            </tr>
                            <tr>
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Start Time</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px;">${currentBuild.startTimeInMillis ? new Date(currentBuild.startTimeInMillis).format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('UTC')) : 'N/A'}</td>
                            </tr>
                            <tr>
                                <th style="padding: 8px; text-align: left; border: 1px solid #ddd; font-size: 16px;">Duration</th>
                                <td style="padding: 8px; border: 1px solid #ddd; font-size: 16px;">${currentBuild.durationString}</td>
                            </tr>
                        </table>
                        
                        <p>You can find the detailed test report attached to this email.</p>
                        <p>For more details, visit: <a href="${env.BUILD_URL}" style="color:#2E86C1;">Build Console Output</a></p>
                        
                        <!-- Footer Section -->
                        <br/>
                        <div style="text-align: center; font-size: small; color: #888;">
                            <p>💡 This is an automated message from Jenkins CI/CD.</p>
                            <p>For inquiries, please contact the team at <a href="mailto:vaibhavp@siddhatech.com" style="color:#2E86C1;">vaibhavp@siddhatech.com</a></p>
                        </div>
                    </body>
                </html>
            """,
            attachmentsPattern: "Reports/*.html",
            mimeType: 'text/html'
        )
    }
}
	
}
