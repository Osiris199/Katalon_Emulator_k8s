# Katalon_Emulator_k8s
Run katalon case inside k8s pod (with android emulator)

Hi there this is Vaibhav,
In our project, we're leveraging Katalon as our automation test tool to run Android mobile test cases. Our setup involves a Dockerfile comprising Katalon Studio engine and an Android emulator. We then generate a Docker image from this file and push it to Docker Hub for further use. Utilizing Kubernetes (k8s) and its clusterization platform, we execute these test cases within pods.

Here's a breakdown of our current process:

1) Once the pod is up and running, it initiates the execution of test cases.
2) Upon completion, it transfers the test reports to the local machine.
3) Finally, the pod is terminated.

Currently, our test cases are set up statically, but we're actively working on updating this process.
