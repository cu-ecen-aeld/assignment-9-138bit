# https://docs.yoctoproject.org/kernel-dev/advanced.html

SUMMARY = "Linux kernel module - aesd char driver"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/Incorporating-Out-of-Tree-Modules-in-YOCTO/ta-p/1373825
SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-138bit.git;protocol=ssh;branch=master"
SRC_URI += "file://S97aesdchar"

#SRCREV = "71e69a2d52df7388a821052445d493f8de0c37cd"
SRCREV = "dbb4e69b68dc3051f8b80e743b5005836404e678"

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
