require dpkg_debian.bb

DESCRIPTION = "Dpkg version of update-alternatives"
DEBIAN_SECTION = "admin"
DPR = "0"
DPN = "dpkg"

# Overwrite DEPENDS of dpkg.inc
DEPENDS = ""
PROVIDES_class-native += "virtual/update-alternatives-native"

# Unnecessary for update-alternatives
EXTRA_OECONF += " \
	--without-zlib \
	--without-bz2 \
"

# Only compile materials for update-alternatives
do_compile() {
	cd ${S}/lib && oe_runmake
	cd ${S}/utils && oe_runmake
}
  
do_install() {
	install -d ${D}${sbindir} ${sysconfdir}/alternatives \
			${localstatedir}/lib/dpkg/alternatives
	install -m 0755 ${S}/utils/update-alternatives ${D}${sbindir}
	exit 0
}

PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} = " \
	${sbindir}/update-alternatives \
	${localstatedir}/lib/dpkg/alternatives \
	${sysconfdir}/alternatives \
"
FILES_${PN}-dbg = " \
	${prefix}/src \
	${sbindir}/.debug \
"
