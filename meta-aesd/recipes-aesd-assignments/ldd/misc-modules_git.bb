# https://docs.yoctoproject.org/kernel-dev/advanced.html

SUMMARY = "Linux kernel module - misc-modules"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/Incorporating-Out-of-Tree-Modules-in-YOCTO/ta-p/1373825

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-138bit.git;protocol=ssh;branch=master"
SRCREV = "515518038f54e27290ec23fc9b37a73118df030c"

S = "${WORKDIR}/git/misc-modules"
EXTRA_OEMAKE += " KERNELDIR=${STAGING_KERNEL_DIR} M=${S}"

inherit module

# build/tmp/work/qemuarm64-poky-linux/misc-modules/git-r0/git/misc-modules/
do_install:append() {
	#install -d ${D}${sysconfdir}/rcS.d
	install -d ${D}${bindir}
	install -m 0755 ${S}/module_load ${D}${bindir}/module_load
	install -m 0755 ${S}/module_unload ${D}${bindir}/module_unload
}

FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${bindir}/module_load"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-misc-modules"
