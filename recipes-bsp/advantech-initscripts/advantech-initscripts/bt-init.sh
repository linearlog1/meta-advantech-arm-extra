#!/bin/sh


btinit()
{

brcm-patchram-plus -d --enable_hci --no2bytes --tosleep 200000 -baudrate 115200 --patchram /lib/firmware/bcm/BCM4343B0.hcd /dev/ttymxc1
hciattach /dev/ttymxc1 any 115200 flow
}

btinit
