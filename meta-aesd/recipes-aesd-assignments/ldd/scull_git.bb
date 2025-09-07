# https://docs.yoctoproject.org/kernel-dev/advanced.html

SUMMARY = "Linux kernel module - scull"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/Incorporating-Out-of-Tree-Modules-in-YOCTO/ta-p/1373825
SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-138bit.git;protocol=ssh;branch=master"
SRC_URI += "file://S98lddmodules"
SRC_URI += "file://0001-fix-scull-makefile.patch"
SRCREV = "515518038f54e27290ec23fc9b37a73118df030c"

#PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git/scull"
EXTRA_OEMAKE += " KERNELDIR=${STAGING_KERNEL_DIR} M=${S}"

inherit module

do_install:append() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/scull_load ${D}${bindir}/scull_load
	install -m 0755 ${S}/scull_unload ${D}${bindir}/scull_unload

#	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
#	install -m 0755 ${S}/scull.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko

	install -d ${D}${sysconfdir}/rcS.d
	install -m 0755 ${S}/../../S98lddmodules ${D}${sysconfdir}/rcS.d/S98lddmodules
}

FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${bindir}/scull_load"
#FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko"
FILES:${PN} += "${sysconfdir}/rcS.d/S98lddmodules"


# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
RPROVIDES:${PN} += "kernel-module-${PN}"
