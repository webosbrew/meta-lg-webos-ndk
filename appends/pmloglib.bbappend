FILESEXTRAPATHS_prepend := "${THISDIR}/patches:"

SRC_URI_append = " \
    file://pmloglib_3.3.0-verbose.patch \
"

TARGET_CFLAGS += "-DDEBUG_ENABLED"