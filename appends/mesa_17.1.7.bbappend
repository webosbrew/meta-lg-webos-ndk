FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESPATH_prepend := "/media/build/build-webos-1.0/BUILD/workspace/sources/mesa/oe-local-files:"

inherit externalsrc
# NOTE: We use pn- overrides here to avoid affecting multiple variants in the case where the recipe uses BBCLASSEXTEND
EXTERNALSRC_pn-mesa = "/media/build/build-webos-1.0/BUILD/workspace/sources/mesa"

# initial_rev: 29459f0e1fdc6610ff6eee2b4fd77686abe075e2
# commit: cf4d755a4351336c0b76db8bd4870834c3596292
# commit: 26ec40e39809adbb809140d497ba2f985ebe0395
# commit: b9c7d8a0fc1d30c30d4871deb89676b701627ac4
# commit: 131acf4a40e63c0ead02281542879b807981e1c4
# commit: c52e22540f81fa0fe83f27d5fec5757f90fb375e
# commit: 9710f99f921957bfa5c2a60ff3d3f15eaa8dc3f1
# commit: f68d57795a64ff9a6dfadfa7e35bf1d79112a39b
# commit: b88b3ef1663d4f1747722b60eb253636c514f8bb
# commit: e084a1c086f90530873f51d26889ad8a92a70916
# commit: 771e3764a95333c3fb06520a67491b9c37b819e8
