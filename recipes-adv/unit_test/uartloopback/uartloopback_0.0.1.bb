# Copyright (C) 2017 Advantech

SUMMARY = "Uart loopback test tool"
DESCRIPTION = "Uart loopback test tool"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://ADVANTECH-LICENSE.txt;md5=5ce1bdf9929c5c1b0281430c0254159e"

SRCBRANCH ?= "master"
# SRC_URI = "git://172.16.9.237/~/code/nxp/uartloopback.git;protocol=ssh;branch=${SRCBRANCH};user=gituser"
SRC_URI = "git://github.com/linearlog1/uartloopback.git;protocol=git;branch=${SRCBRANCH}"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"
INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "ldflags"

do_compile () {
	make
}

do_install () {
	install -d ${D}/unit_tests
	install -m 0755 ${S}/uart-loopback ${D}/unit_tests/
}

FILES_${PN} += "/unit_tests"
