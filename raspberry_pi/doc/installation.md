# Installation instructions

Execute these from the "terminal" window of vscode on your laptop, or on the Raspberry Pi.

## Start with an update

```
sudo apt update 
sudo apt upgrade 

```

## Apt packages:

```
sudo apt install python3-pip 
sudo apt install python3-setuptools 
sudo apt install python3-wheel 
sudo apt install python3-numpy 
sudo apt install libcamera-dev 
sudo apt install python3-picamera2 
sudo apt install python3-aiohttp 

```

## Python packages:

on the pi, these should be installed as sudo apt install python3-<thing>

```
python3 -m pip install numpy --break-system-packages
python3 -m pip install robotpy --break-system-packages 
python3 -m pip install robotpy-cscore --break-system-packages
python3 -m pip install robotpy-apriltag --break-system-packages
python3 -m pip install opencv-python --break-system-packages

```

## Gyro

```
python3 -m pip install hidapi --break-system-packages
python3 -m pip install adafruit-blinka --break-system-packages
python3 -m pip install adafruit-circuitpython-lsm6ds --break-system-packages

```

## Quick Paste

you can also just paste this into the pi and type y a bunch in 2025, update 2026, need to use old numpy version

```
sudo apt update 
sudo apt upgrade 
sudo apt install -y python3-pip 
sudo apt install -y python3-setuptools 
sudo apt install -y python3-wheel 
sudo apt install -y python3-numpy 
sudo apt install -y libcamera-dev 
sudo apt install -y python3-picamera2 
sudo apt install -y python3-aiohttp 
python3 -m pip install robotpy --break-system-packages 
python3 -m pip install robotpy-cscore --break-system-packages
python3 -m pip install robotpy-apriltag --break-system-packages
python3 -m pip install opencv-python --break-system-packages
python3 -m pip install hidapi --break-system-packages
python3 -m pip install adafruit-blinka --break-system-packages
python3 -m pip install adafruit-circuitpython-lsm6ds --break-system-packages
python3 -m pip install numpy==1.26.4 --break-system-packages

```

# Developing on Linux

If you're using Linux for development, we're currently using Ubuntu 26.04,
which uses python 3.14.14.

* create a venv for the build
    * `python -m venv ./env` (or wherever)
* upgrade the environment
    * `source env/bin/activate`
    * `python -m ensurepip --default-pip`
    * `python -m pip install --upgrade pip`
    * `python -m pip install numpy`
    * `python -m pip install robotpy`
    * `python -m pip install robotpy-apriltag`
    * `python -m pip install robotpy-cscore`
    * `python -m pip install opencv-python`

finally, choose this venv  as the interpreter in vscode.

* shift-ctrl-p, "Python: select interpreter"


# Developing on Windows

If you're writing code on windows, and you want to run the tests,
you'll have to install:

```
py -m pip install aiohttp
```