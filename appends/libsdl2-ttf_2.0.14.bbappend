FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESPATH_prepend := "/media/build/build-webos-1.0/BUILD/workspace/sources/libsdl2-ttf/oe-local-files:"

inherit externalsrc
# NOTE: We use pn- overrides here to avoid affecting multiple variants in the case where the recipe uses BBCLASSEXTEND
EXTERNALSRC_pn-libsdl2-ttf = "/media/build/build-webos-1.0/BUILD/workspace/sources/libsdl2-ttf"
EXTRA_OECONF_pn-libsdl2-ttf = " --disable-sdltest"
# initial_rev: ff17a6978b4b7f0ed97b5edc568621d12e582622
# commit: 2642529ba7d6af5a88f382203c2c877226bce80c