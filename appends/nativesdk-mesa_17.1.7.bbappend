FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESPATH_prepend := "/media/build/build-webos-1.0/BUILD/workspace/sources/nativesdk-mesa/oe-local-files:"

inherit externalsrc
# NOTE: We use pn- overrides here to avoid affecting multiple variants in the case where the recipe uses BBCLASSEXTEND
EXTERNALSRC_pn-nativesdk-mesa = "/media/build/build-webos-1.0/BUILD/workspace/sources/nativesdk-mesa"

# initial_rev: 4df58c204f8d5555beef7fcdc96759396a4c666a
