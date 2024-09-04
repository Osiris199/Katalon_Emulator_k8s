#!/bin/bash

main_function() {
    echo "katalon report path is $KATALON_REPORT_PATH"
    echo "katalon report path local is $KATALON_LOCAL_REPORT_PATH"

    printf "===> SCRIPT FOR CHECKING CASE STATUS AND TERMINATING POD <===\n"
    check_katalon_process
    sleep 2
}

check_katalon_process() {
    timeout=900  # 15 minutes
    start_time=$(date +%s)
    
    while true; do
        if pgrep -f "katalonc" >/dev/null; then
            echo "Katalonc process started successfully."
            break
        fi
        
        current_time=$(date +%s)
        elapsed_time=$((current_time - start_time))
        
        if [ $elapsed_time -ge $timeout ]; then
            echo "Timeout: Katalonc process did not start within 15 minutes"
            exit 1
        fi
        
        echo "Waiting for Katalonc process to start..."
        sleep 10
    done

    check_suite_status
}


check_suite_status() {
  while true; do

    if pgrep -x "katalonc" >/dev/null; then
        echo "Test suite is still running...waiting for execution to be completed...!!!"
        sleep 10
    else
        echo "Test suite executed...!!!"
        break
    fi
  done

  delete_pod
}

delete_pod() {
  copy_reports
  kubectl delete deployment deployment -n default
  kubectl delete service android-service
  kubectl delete service vnc-service
  echo "Pod Deleted"
}

control_c() {
    echo ""
    exit
}

copy_reports() { 
    POD_NAME=$(minikube kubectl -- get pods --no-headers -o custom-columns=':metadata.name' | grep -i deployment)
    cd empresa/
    ls -la
    pwd
    kubectl cp $POD_NAME:/empresa/Reports/. -c android-emulator /home/siddhatech/Reports
    echo "files transferred to /home/siddhatech/Reports path on local machine."
}

trap control_c SIGINT SIGTERM SIGHUP

main_function

exit
