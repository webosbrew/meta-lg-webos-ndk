# [Try better toolchain with newest compiler!](https://github.com/openlgtv/buildroot-nc4)
[or download Prebuilt NDK Directly](https://github.com/webosbrew/meta-lg-webos-ndk/releases)

# Config Layer to Build NDK For LG webOS TV

This layer overrides some configuration in webOS OSE, to generate a native SDK (unofficial) suitable for LG webOS TV.

Currently, this layer is tested to be applicable webOS OSE 1.0.g. It seems that webOS OSE 1.x is similar to LG webOS 4.x.
Which is running on many devices manufactured back in August 2018.

## What's changed in this layer?

Build configuration for webOS OSE was originally for `raspberrypi3`. Which is compiled with `hardfp` option.
LG's real devices are using armv7a (with arm64 kernel).
So changes were made to build an image with `softfp` option.

And some important system libraries are updated to reflect what's running in the real TV,
in order to maintain compatibility. More specifically:

* GStreamer was updated to 1.14.0, and included.
* SDL2 was updated to 2.0.5. Also, SDL2-image, SDL2-mixer SDL2-net, and SDL2-ttf was included.
* `libwayland-egl.so.1`'s name was changed to `libwayland-egl.so.0`, for linking against correct library name on TV.
* Qt Multimedia was included.

By exploring more and more inside real TV, you may find there're something needs to be added.
Feel free to create an issue, or create a pull request.

## SDK Build Instructions

### System Requirements

I built the NDK with a virtual machine running Ubuntu 16.04, which has 10 GB of RAM and 8 CPU cores. Minimum system requirement would be roughly

* 4 CPU cores
* 8 GB of RAM
* 200 GB of disk storage. 50 GB for fetched sources and more than 100 GB for build files.

On my computer (Ubuntu 16.04 in VirtualBox running on machine i9-9980HK, 32 GB DDR4-2666 and an entry level NVME SSD running Windows 10),
it took around 6 hours without any performance tweaks. The build failed somewhere when I tried to build with Ubuntu 18.04, or Debian 10.

#### Tips: Use Chroot to Fake an Ubuntu 16.04 System

Run following commands as root.

```shell
apt-get install debootstrap
debootstrap --arch=amd64 xenial ./ubuntu-16.04 http://archive.ubuntu.com/ubuntu/
chroot ./ubuntu-16.04
adduser [any username you like]
adduser [username] sudo
echo '[username] ALL=NOPASSWD: ALL' > /etc/sudoers.d/nopasswd # Not a good practice though
echo 'ubuntu' > /etc/debian_chroot # Helps you to distinguish from your host
echo 'deb http://archive.ubuntu.com/ubuntu xenial main universe multiverse restricted' > /etc/apt/sources.list
```

Create `tchroot` in your $PATH. (Credit: https://gist.github.com/schtobia/ea1b786d915415f86099)

```sh
#! /bin/sh
[ ! -d "$1" ] && echo "$1 is not a valid directory." && exit 1;
trap "umount \"${1}\"/tmp \"${1}\"/dev/null \"${1}\"/dev/pts \"${1}\"/dev/random \"${1}\"/dev/shm \"${1}\"/dev/urandom \"${1}\"/proc" EXIT INT TERM HUP PIPE &&
    mount --bind /tmp "${1}/tmp" && \
    mount --bind /dev/null "${1}/dev/null" && \
    mount --bind /dev/pts "${1}/dev/pts" && \
    mount --bind /dev/random "${1}/dev/random" && \
    mount --bind /dev/shm "${1}/dev/shm" && \
    mount --bind /dev/urandom "${1}/dev/urandom" && \
    mount --bind /proc "${1}/proc" && \
    chroot "$@";
```

Then use `sudo tchroot ./ubuntu-16.04 su [username]` as your build system.

After you got your build system ready, let's get started.

### Build Steps

To build the NDK, you'll need to follow instructions provided by webOS OSE:
[Building webOS Open Source Edition](https://www.webosose.org/docs/guides/setup/building-webos-ose/), with few more simple steps to add this layer.

After finishing the step [Cloning the Repository](https://www.webosose.org/docs/guides/setup/building-webos-ose/)

1. Checkout to `builds/master/1` tag, which is their first version:
```bash
git checkout builds/master/1 # Or add `-b [branch-name]` to start your work on a new branch
```

Note: this version was tested and verified to be work with my TV model. But you can try other versions.

2. Add this layer to `weboslayers.py`, please note the highest proprity `99` among all the layers:
```diff
 ('meta-webos-raspberrypi',    51, 'git://github.com/webosose/meta-webosose.git',            '', ''),
+('meta-lg-webos-ndk',         99, 'git://github.com/webosbrew/meta-lg-webos-ndk.git',       'branch=main', ''),
 ]
```

2. Continue building. If you can see Build Configuration below, then this layer is applied correctly.
```
Build Configuration:
BB_VERSION        = "1.32.0"
BUILD_SYS         = "x86_64-linux"
NATIVELSBSTRING   = "Ubuntu-16.04"
TARGET_SYS        = "arm-webos-linux-gnueabi"
MACHINE           = "raspberrypi3"
DISTRO            = "webos"
DISTRO_VERSION    = "1.0.g"
TUNE_FEATURES     = "arm armv7a vfp  neon" # <====================== NOTICE THIS
TARGET_FPU        = "softfp" # <==================================== NOTICE THIS
WEBOS_DISTRO_RELEASE_CODENAME = "webos-master"
WEBOS_DISTRO_BUILD_ID = "unofficial"
WEBOS_DISTRO_TOPDIR_REVISION = "1e7e06b48981fbe723beb48fe3db842ac2e642cf"
WEBOS_DISTRO_TOPDIR_DESCRIBE = "builds/master/1"
DATETIME          = "20201216130922"
meta-lg-webos-ndk = "main:ad3bcf879f12c1249e5126fc708963c05bc02d8d"
meta-webos-raspberrypi = "master:7c8e550398dd3ef7ab26f59023ede177f6f9830c"
meta-raspberrypi  = "morty:2a192261a914892019f4f428d7462bb3c585ebac"
meta-webos
meta-webos-backports-2.5
meta-webos-backports-2.4
meta-webos-backports-2.3 = "master:7c8e550398dd3ef7ab26f59023ede177f6f9830c"
meta-qt5          = "krogoth:f8584d7a7c90afc71484a40279aa3df651d0e04f"
meta-filesystems
meta-python
meta-networking
meta-multimedia
meta-oe           = "morty:b40116cf457b88a2db14b86fda9627fb34d56ae6"
meta              = "morty:1718f0a6c1de9c23660a9bebfd4420e3c4ed37e6"
```

3. After system image built, follow this instruction to build the NDK: 
[Native Development Kit Setup](https://www.webosose.org/docs/guides/setup/setting-up-native-development-kit/)

4. All this should just take some time.

## Development Instructions

Developing programs with this NDK should be similar to other embedded Linux systems.
First you install the NDK: [Run the NDK Installer](https://www.webosose.org/docs/guides/setup/setting-up-native-development-kit/#run-the-ndk-installer). After you installed this NDK, you can use it for later.

### Compile Program by Command Line

In most cases, you'll first need to [Run the Environment Setup Script](https://www.webosose.org/docs/guides/setup/setting-up-native-development-kit/#run-the-environment-setup-script).

```bash
source /opt/webos-sdk-x86_64/1.0.g/environment-setup-armv7a-neon-webos-linux-gnueabi
```

#### CMake

Nothing special. For Qt project, you may need to use [CMake Toolchains](https://cmake.org/cmake/help/latest/manual/cmake-toolchains.7.html#cross-compiling).

```bash
cd project-directory
mkdir build
cd build
cmake .. -DCMAKE_TOOLCHAIN_FILE=/opt/webos-sdk-x86_64/1.0.g/sysroots/x86_64-webossdk-linux/usr/share/cmake/OEToolchainConfig.cmake
make
```

### Use with IDE

#### VS Code

1. Install [CMake Tools](https://marketplace.visualstudio.com/items?itemName=ms-vscode.cmake-tools)
2. Add following kit (please modify the path if you've changed install location of NDK)

```json
[
  {
    "name": "webOS NDK 1.0.g",
    "environmentSetupScript": "/opt/webos-sdk-x86_64/1.0.g/environment-setup-armv7a-neon-webos-linux-gnueabi",
    "toolchainFile": "/opt/webos-sdk-x86_64/1.0.g/sysroots/x86_64-webossdk-linux/usr/share/cmake/OEToolchainConfig.cmake"
  }
]
```
### Useful tips

#### GDB

The GDB shipped with Developer Mode has one library missing: `libreadline.so.6`. You can copy it from NDK, to `/media/developer/lib` on your TV.

#### System library logging

webOS often uses PmLogLib for logging. And you can't see the log in end-user products. `libPmLogLib.so.3` is modified in this NDK so it will print all the logs into `stdout` and `stderr`. You can also copy this into `/media/developer/lib`, and modify `LD_LIBRARY_PATH` environment variable to make executables load this library first.
