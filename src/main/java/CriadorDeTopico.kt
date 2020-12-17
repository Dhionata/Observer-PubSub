import com.google.api.gax.rpc.ApiException
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.TopicName

object CriadorDeTopico {

    private fun criar(projetoID: String, topico: String) {
        val projetoID = "fasam-1984"
        val topicoID = "teste"

        val nomeTopico = TopicName.of(projetoID, topicoID)
        try {
            val topicAdminClient = TopicAdminClient.create()
            topicAdminClient.createTopic(nomeTopico)
        } catch (e: ApiException) {
            println(e.statusCode.code)
            println(e.isRetryable)
            println(e.message)
        }
        println("Topic ${nomeTopico.topic}")
    }
}