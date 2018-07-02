DESCRIPTION = "SNMP Subagent Example Using mib2c with template mib2c.mfd.conf"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://ADVANTECH-LICENSE.txt;md5=5ce1bdf9929c5c1b0281430c0254159e"

DEPENDS = "net-snmp"

SRCBRANCH ?= "master"
SRC_URI = "git://172.16.9.237/~/code/DMS-SE25/snmp/snmp-rs232-subagent-mfd.git;protocol=ssh;branch=${SRCBRANCH};user=gituser"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"


do_compile () {
	make
}

do_install () {
	install -d ${D}${bindir}
	install -m 755 rs232-daemon-mfd ${D}${bindir}
}


FILES_${PN} += "${bindir}/"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
