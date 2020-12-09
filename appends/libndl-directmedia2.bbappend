FILESEXTRAPATHS_prepend := "${THISDIR}/patches:"

SRC_URI_append = " \
    file://libndl-directmedia2-ffmpeg-3.3.patch \
"