# Copyright (C) 2017 Advantech

SUMMARY = "Read Version Information on the built images"
DESCRIPTION = "Built Image includes bootloader, kernel and rootfs"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://ADVANTECH-LICENSE.txt;md5=5ce1bdf9929c5c1b0281430c0254159e"

SRCBRANCH ?= "master"
SRC_URI = "git://172.16.9.237/~/code/nxp/Adv_VersionInfo.git;protocol=ssh;branch=${SRCBRANCH};user=gituser"
SRCREV = "${AUTOREV}"

require recipes-qt/qt5/qt5.inc

DEPENDS = "qtbase"

S = "${WORKDIR}/git"

do_install () {
	#remove version file if existed
	rm -rf version-adv
	#uboot 512M/1G version
        echo ${UBOOT_CONFIG} >> version-adv
        #rootfs version
	VERSION_A_IDENTIFIER=2
        if [ "${UBOOT_CONFIG}" != "1Gram" ] ; then
            VERSION_A_IDENTIFIER=1
        fi
        VERSION_B_IDENTIFIER=$(date +"%y%m%d")
        echo "${VERSION_A_IDENTIFIER}_${VERSION_B_IDENTIFIER}_${VERSION_C_IDENTIFIER}${VERSION_D_IDENTIFIER}" >> version-adv


	#version info file in /etc
	install -d ${D}${sysconfdir}/
	install -m 0444 ${WORKDIR}/build/version-adv ${D}${sysconfdir}/
	#qt binary file in /usr/local and softlink in /usr/bin
	install -d ${D}${prefix}/local/
	install -m 0755 ${WORKDIR}/build/DeviceInfo ${D}${prefix}/local/
	install -d ${D}${prefix}/bin
        ln -s ../local/DeviceInfo ${D}${prefix}/bin/DeviceInfo
	#desktop file in /usr/share/applications
	install -d ${D}${datadir}/applications
	install -m 0644 ${S}/DeviceInfo.desktop ${D}${datadir}/applications/
 
}

FILES_${PN} += "${prefix}/local/DeviceInfo \
		${prefix}/bin/DeviceInfo \
                ${datadir}/applications"

