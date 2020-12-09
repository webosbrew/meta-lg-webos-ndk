SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
DESCRIPTION = "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, \
               mux, demux, stream, filter and play pretty much anything that humans and machines \
               have created. It supports the most obscure ancient formats up to the cutting edge."
HOMEPAGE = "https://www.ffmpeg.org/"
SECTION = "libs"

LICENSE = "LGPLv2.1"

LIC_FILES_CHKSUM = "file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004"

SRC_URI = "https://www.ffmpeg.org/releases/${BP}.tar.xz \
           file://mips64_cpu_detection.patch \
           file://0001-build-fix-for-mips.patch \
           file://CVE-2017-14054.patch \
           file://CVE-2017-14055.patch \
           file://CVE-2017-14056.patch \
           file://CVE-2017-14057.patch \
           file://CVE-2017-14058.patch \
           file://CVE-2017-14059.patch \
           file://CVE-2017-14169.patch \
           file://CVE-2017-14170.patch \
           file://CVE-2017-14171.patch \
           file://CVE-2017-14222.patch \
           file://CVE-2017-14223.patch \
           file://CVE-2017-14225.patch \
          "
SRC_URI[md5sum] = "743dc66ebe67180283b92d029f690d0f"
SRC_URI[sha256sum] = "d2a9002cdc6b533b59728827186c044ad02ba64841f1b7cd6c21779875453a1e"

# Build fails when thumb is enabled: https://bugzilla.yoctoproject.org/show_bug.cgi?id=7717
ARM_INSTRUCTION_SET = "arm"

# Should be API compatible with libav (which was a fork of ffmpeg)
PROVIDES = "libav"

DEPENDS = "xz"

inherit autotools pkgconfig

FFMPEG_CONF = ' \
    --prefix=${prefix} \
    --enable-cross-compile \
    --target-os=linux \
    --arch=${TARGET_ARCH} \
    --cc="${CC}" \
    --disable-asm \
    --disable-yasm \
    --disable-stripping \
    --disable-bzlib \
    --disable-zlib \
    --disable-avdevice \
    --disable-programs \
    --disable-doc \
    --disable-logging \
    --disable-debug \
    --disable-pthreads \
    --disable-w32threads \
    --disable-os2threads \
    --enable-thumb \
    --enable-shared \
    --disable-decoder=msmpeg4v3 \
    '

do_configure () {
    export LD="${CC}"
    ${S}/configure ${FFMPEG_CONF}
}

INSANE_SKIP_${PN}="textrel"
