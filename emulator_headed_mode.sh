#!/bin/bash

emulator_name=${EMULATOR_NAME}
katalon_version=${KATALON_VERSION}
test_suite=${TEST_SUITE_NAME}



main_function() {
    printf "===> SCRIPT FOR STARTING EMULATOR AND KATALON CASES <===\n"
    echo "test suite name : ${test_suite}"
    check_hardware_acceleration_support
    sleep 2
    start_emulator
    sleep 2
    check_emulator_boot_status
    sleep 2
    disable_emulator_animations
    sleep 2
    apply_hidden_policy
    sleep 2
}

check_hardware_acceleration_support() {
    if [[ "$HW_ACCEL_OVERRIDE" != "" ]]; then
        hw_accel_flag="$HW_ACCEL_OVERRIDE"
    else
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # For macOS distribution
            HW_ACCEL_SUPPORT=$(sysctl -a | grep -E -c '(vmx|svm)')
        else
            # For linux distribution
            HW_ACCEL_SUPPORT=$(grep -E -c '(vmx|svm)' /proc/cpuinfo)
        fi

        if [[ $HW_ACCEL_SUPPORT == 0 ]]; then
            hw_accel_flag="-accel off"
        else
            hw_accel_flag="-accel on"
        fi
    fi

    echo "$hw_accel_flag"
}

start_emulator() {
  adb devices | grep emulator | cut -f1 | xargs -I {} adb -s "{}" emu kill

  options="-avd  ${emulator_name} -no-snapshot -noaudio -no-boot-anim -memory 4096 ${hw_accel_flag} -camera-back none -gpu swiftshader_indirect"

  if [[ "$OSTYPE" == *linux* ]] || [[ "$OSTYPE" == *darwin* ]] || [[ "$OSTYPE" == *macos* ]]; then
    echo "${OSTYPE}: emulator ${options}"
    emulator ${options} &
  else
    echo "Unsupported OS"
    return 1
  fi

  if [ $? -ne 0 ]; then
    echo "Error launching emulator"
    return 1
  fi
}


check_emulator_boot_status () {
  printf "===> Checking emulator booting up status <===\n"

  start_time=$(date +%s)
  timeout=${EMULATOR_TIMEOUT:-300}

  spinner=( "⠹" "⠺" "⠼" "⠶" "⠦" "⠧" "⠇" "⠏" )
  i=0

  # Delay before the first check
  sleep 2

  while true; do
    result=$(adb shell getprop sys.boot_completed 2>&1)
    
    if [ "$result" == "1" ]; then
      printf "\e[K===> Emulator is ready : '$result' <===\n"
      cd Katalon_Studio_Engine_Linux_64-${katalon_version}
      #./katalonc -noSplash -runMode=console -projectPath="/empresa/BanReservas-Android-New.prj" -retry=0 -testSuitePath="Test Suites/PAYMENTS/TS12_Pago_Prestamos_Interbanc_Benef_Pago_ACH" -browserType="Android" -deviceId="emulator-5554" -executionProfile="local" -apiKey="2b233a8b-743d-44bd-8e85-dc990101c95e"
      ./katalonc -noSplash -runMode=console -projectPath="/empresa/Android Mobile Tests with Katalon Studio.prj" -retry=0 -testSuitePath="Test Suites/${test_suite}" -browserType="Android" -deviceId="emulator-5554" -executionProfile="default" -apiKey="2b233a8b-743d-44bd-8e85-dc990101c95e"
      break
    elif [ "$result" == "" ]; then
      printf "===> Emulator is still booting! ${spinner[$i]} <===\r"
    else
      printf "===> $result, please wait ${spinner[$i]} <=== \r"
      i=$(( (i+1) % 8 ))
    fi
    
    current_time=$(date +%s)
    elapsed_time=$((current_time - start_time))
    
    if [ $elapsed_time -gt $timeout ]; then
      printf "===> Timeout after ${timeout} seconds elapsed <===\n"
      break
    fi
    
    sleep 4
  done
}


disable_emulator_animations() {
  adb shell "settings put global window_animation_scale 0.0"
  adb shell "settings put global transition_animation_scale 0.0"
  adb shell "settings put global animator_duration_scale 0.0"
}

apply_hidden_policy() {
  adb shell "settings put global hidden_api_policy_pre_p_apps 1;settings put global hidden_api_policy_p_apps 1;settings put global hidden_api_policy 1"
}

control_c() {
    echo ""
    exit
}

trap control_c SIGINT SIGTERM SIGHUP

main_function

exit
