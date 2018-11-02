SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL += " \
    dhcp-server \
    dhcp-client \
    init-ifupdown \
    advantech-initscripts \
    initscripts \
    fbida \
    parted \
    openssh \
    stress \
    "
