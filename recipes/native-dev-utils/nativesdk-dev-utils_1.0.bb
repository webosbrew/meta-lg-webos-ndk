SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
DEPENDS_${PN} = "nativesdk-cmake"

LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/webosbrew/native-dev-utils.git;protocol=http;branch=main"
SRCREV = "e2ee60abc20a3120557d3875cdc7c3e0ab229e51"
S = "${WORKDIR}/git"

inherit nativesdk

do_compile() {
     :
}

do_install() {
    mkdir -p ${D}${bindir}
    install -m 755 ${S}/scripts/webos-cmake-wrapper.sh ${D}${bindir}/webos-cmake-wrapper

    mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d
    install -m 644 ${S}/scripts/restore-path.sh ${D}${SDKPATHNATIVE}/environment-setup.d/restore-path.sh
}

FILES_${PN} = "${SDKPATHNATIVE}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"