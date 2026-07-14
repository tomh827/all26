# Raspberry Pi Setup

This is the new setup procedure for 2027, which does not
use the unmaintained "WPILibPi" distribution.

We use only the Raspberry Pi 5.  We have a bunch of 4's,
but we don't use them, they're too slow.

## Burn the SD Card

* Use the [Raspberry Pi Imager](https://www.raspberrypi.com/software/)

* Use the [Raspberry Pi OS 64-bit Lite "Trixie" image](https://www.raspberrypi.com/software/operating-systems/)
    * The "lite" option is behind the "other images" option.  Don't use the "full" image.

## Configure the network

Set up the static ethernet addressing scheme.

* In the `bootfs` volume, edit file called `network-config`, using Notepad.
* This is a file containing configuration in
  the [format](https://docs.cloud-init.io/en/latest/reference/network-config.html) 
  used by `cloud-init`.
* Use an IP between 10.1.0.30 and 10.1.0.35.
* Make the ethernet `optional` to avoid holding up the boot.
* The robot hosts the pi needs to talk to are all on the same
  segment, so no gateway is required for that.
* Ror the part of the setup involving the internet,
  you'll be bridging with your laptop, so there
  needs to be a default gateway.  What should it be?  You usually
  use either 10.1.0.5 (driver station) or 10.1.0.2 (impersonating
  RoboRIO). The most common setup is 10.1.0.5, if you're driving
  (which is most of the time).  So we set up two default routes.
* The resulting file should look like this:

```yaml
network:
  version: 2
  ethernets:
    eth0:
      addresses: 
        - 10.1.0.31/24
      routes:
        - to: default
          via: 10.1.0.5
          metric: 100
        - to: default
          via: 10.1.0.2
          metric: 200
      optional: true
```

## Turn off the radios

Turn off the wifi and bluetooth radios (because FRC prohibits them)

* In the `bootfs` volume, find the file called `config.txt`, using Notepad.
* This file uses a simple format, `name=value`
* Add these lines at the top of `config.txt`:

```ini
dtoverlay=disable-wifi-pi5
dtoverlay=disable-bt-pi5
```

## Network

Give the Pi access to the internet:

* Install the SD card and turn on the Pi.
* Connect it to your laptop with ethernet.
* Set up your laptop network as 10.1.0.5 or 10.1.0.2.
* Share your laptop wifi

## Connect

* Get a command window ("cmd" on Win11)
* type `ssh pi@10.1.0.31` (or whatever the address is)
* use password `raspberry`
* verify the pi can see the internet (`ping 8.8.8.8`)

## Update

* Make sure the OS is up to date.

```
sudo apt update 
sudo apt upgrade 
```

## Packages

* Install system packages.

```
sudo apt install libcamera-dev 
sudo apt install python3-picamera2 
sudo apt install python3-aiohttp 
```

## Python

To avoid dependency between our app and the Raspberry Pi
python installation, we use the python "virtual environment"
feature.

* Create a virtual environment.
```
python -m venv ./env
```
* Install required libraries in the virtual environment.
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

For now, the supervisor installation is described in `studies/piborg`.

## References

* network-config  [ref](https://forums.raspberrypi.com/viewtopic.php?t=396296)
  [ref](https://forums.raspberrypi.com/viewtopic.php?t=396987)
* config.txt   [ref](https://help.pisignage.com/hc/en-us/articles/52406458481817-Disabling-Wi-Fi-and-Bluetooth-on-Raspberry-Pi)
  [ref](https://www.raspberrypi.com/documentation/computers/config_txt.html)