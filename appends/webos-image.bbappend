# Include SDL2 in SDK
TOOLCHAIN_TARGET_TASK += "libsdl2 libsdl2-image libsdl2-mixer libsdl2-net libsdl2-ttf"
# Include GStreamer in SDK
TOOLCHAIN_TARGET_TASK += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good"
# Include Qt Multimedia in SDK
TOOLCHAIN_TARGET_TASK += "qtmultimedia"

# Add Qt Native tools (e.g. qmake)
TOOLCHAIN_HOST_TASK += "nativesdk-qtbase-tools"