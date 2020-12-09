FILESEXTRAPATHS_prepend := "${THISDIR}/patches:"

SRC_URI_append = " \
    file://mesa-so-version-name.patch \
"