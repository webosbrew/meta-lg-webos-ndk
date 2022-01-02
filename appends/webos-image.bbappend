# Include SDL2 in SDK
TOOLCHAIN_TARGET_TASK += "libsdl2 libsdl2-image libsdl2-mixer libsdl2-net libsdl2-ttf"
# Include GStreamer in SDK
TOOLCHAIN_TARGET_TASK += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good"

# Not available in real device
TOOLCHAIN_TARGET_TASK_remove += "libndl-directmedia2 ffmpeg"