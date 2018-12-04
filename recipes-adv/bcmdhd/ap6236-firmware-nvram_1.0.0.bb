DESCRIPTION = "Firmware and NVRAM for Wifi AP6236 Adaptor"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/Wi-Fi_User_Guide_for_Linux_3.X-v03.pdf;md5=cc13116a97d969f3d81c15481f094ece"
INSANE_SKIP_${PN} = "ldflags"

SRC_URI = "file://fw_bcm43436b0_apsta.bin \
           file://fw_bcm43436b0.bin \
	         file://nvram_ap6236.txt \
           file://BCM4343B0.hcd \
           file://wl \
	         file://Wi-Fi_User_Guide_for_Linux_3.X-v03.pdf"


do_install () {
	install -d ${D}${base_libdir}/firmware/bcm/AP6236
	install -m 0644 ${WORKDIR}/fw_bcm43436b0_apsta.bin ${D}${base_libdir}/firmware/bcm/AP6236/
	install -m 0644 ${WORKDIR}/fw_bcm43436b0.bin ${D}${base_libdir}/firmware/bcm/AP6236/
	install -m 0644 ${WORKDIR}/nvram_ap6236.txt ${D}${base_libdir}/firmware/bcm/AP6236/
	install -m 0644 ${WORKDIR}/BCM4343B0.hcd ${D}${base_libdir}/firmware/bcm/AP6236/
  install -m 0644 ${WORKDIR}/wl ${D}${base_libdir}/firmware/bcm/AP6236/

	ln -sf ${base_libdir}/firmware/bcm/AP6236/fw_bcm43436b0_apsta.bin ${D}${base_libdir}/firmware/bcm/fw_bcm43436b0.bin
	ln -sf ${base_libdir}/firmware/bcm/AP6236/nvram_ap6236.txt ${D}${base_libdir}/firmware/bcm/nvram_ap6236.txt
  ln -sf ${base_libdir}/firmware/bcm/AP6236/BCM4343B0.hcd ${D}${base_libdir}/firmware/bcm/BCM4343B0.hcd

  install -d ${D}/${base_bindir}
  cd ${D}/${base_bindir}
  ln -sf  /lib/firmware/bcm/AP6236/wl ${D}${base_bindir}/wl

}

FILES_${PN} += "${base_libdir}/firmware \
                ${base_bindir}/wl"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
