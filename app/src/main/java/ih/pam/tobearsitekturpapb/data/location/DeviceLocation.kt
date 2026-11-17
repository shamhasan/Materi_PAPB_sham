package ih.pam.tobearsitekturpapb.data.location

data class DeviceLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null,
)
