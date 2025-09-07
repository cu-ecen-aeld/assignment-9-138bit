# https://docs.yoctoproject.org/kernel-dev/advanced.html

SUMMARY = "Linux kernel module - aesd char driver"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/Incorporating-Out-of-Tree-Modules-in-YOCTO/ta-p/1373825
SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-138bit.git;protocol=ssh;branch=master"
SRC_URI += "file://S97aesdchar"
SRCREV = "b239f7eab5601a9a0cfcb1070f1503fa3862110e"

#PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git/aesd-char-driver"
EXTRA_OEMAKE += " KERNELDIR=${STAGING_KERNEL_DIR} M=${S}"

RDEPENDS:${PN} += "aesd-assignments"

inherit module

do_install:append() {
        install -d ${D}${sysconfdir}/rcS.d
        install -m 0755 ${S}/../../S97aesdchar ${D}${sysconfdir}/rcS.d/S97aesdchar
}

FILES:${PN} += "${sysconfdir}/rcS.d/S97aesdchar"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
RPROVIDES:${PN} += "kernel-module-${PN}"
