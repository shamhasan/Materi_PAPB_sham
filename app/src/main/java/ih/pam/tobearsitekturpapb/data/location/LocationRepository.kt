package ih.pam.tobearsitekturpapb.data.location

interface LocationRepository {
    suspend fun getCurrentLocation(): Result<DeviceLocation>
}