SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
DEPENDS_${PN} = "nativesdk-cmake"

LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/webosbrew/native-dev-utils.git;protocol=http;branch=main"
SRCREV = "f2edf980254f88e52189a8e9b068f42fb7181e58"
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
    install -m 644 ${S}/scripts/toolchain-pkg-config.sh ${D}${SDKPATHNATIVE}/environment-setup.d/pkg-config.sh
}

FILES_${PN} = "${SDKPATHNATIVE}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
