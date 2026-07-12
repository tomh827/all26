# Raspberry Pi Setup

This is the new setup procedure for 2027, which does not
use the unmaintained "WPILibPi" distribution.

## SD Card

* Use the [Raspberry Pi Imager](https://www.raspberrypi.com/software/)

* Use the [Raspberry Pi OS Lite "Trixie" image](https://www.raspberrypi.com/software/operating-systems/)

* Edit the bootfs network-config to set up a static ethernet IP
  to something like 10.1.0.30, 10.1.0,31, ...
  ([ref](https://forums.raspberrypi.com/viewtopic.php?t=396296))
  ([ref](https://forums.raspberrypi.com/viewtopic.php?t=396987))
 
* Edit the bootfs config.txt to turn off the wifi and
  bluetooth radios (because FRC prohibits them)
  ([ref](https://help.pisignage.com/hc/en-us/articles/52406458481817-Disabling-Wi-Fi-and-Bluetooth-on-Raspberry-Pi))

## Network

Give the Pi access to the internet:

* Install the SD card and turn on the Pi.
* Connect it to your laptop with ethernet.
* Set up your laptop network as 10.1.0.5.
* Share your laptop wifi

## Connect

* Get a command window ("cmd" on Win11)
* type `ssh pi@10.1.0.30` (or whatever the address is)
* use password `raspberry`

## Update

* Make sure the OS is up to date:
```
sudo apt update 
sudo apt upgrade 
```

## Packages

* Install system packages:
```
sudo apt install libcamera-dev 
sudo apt install python3-picamera2 
sudo apt install python3-aiohttp 
```

## Python

* Create a virtual environment
```
python -m venv ./env
```
* Install required libraries
```
source ./env/bin/activate
python -m ensurepip --default-pip
python -m pip install --upgrade pip
python -m pip install numpy
python -m pip install opencv-python
python -m pip install robotpy
python -m pip install robotpy-apriltag
python -m pip install robotpy-cscore
```

## Supervisord

TODO: finish this part