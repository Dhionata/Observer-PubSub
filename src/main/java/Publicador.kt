import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import com.google.pubsub.v1.TopicName
import java.util.concurrent.TimeUnit

object Publicador {

    fun publicar(projetoID: String, topicoID: String, mensagem: String) {
        publisherExample(projetoID, topicoID, mensagem)
    }

    private fun publisherExample(projetoID: String?, topicoID: String?, mensagem: String) {
        val nomeDoTopico = TopicName.of(projetoID, topicoID)
        var publicador: Publisher? = null
        try {
            publicador = Publisher.newBuilder(nomeDoTopico).build()

            //publicar mensagem
            val menssagemFormatada =
                publicador.publish(PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(mensagem)).build())
            println("ID da mensagem: ${menssagemFormatada.get()}")
            println("Mensagem Publicada: $mensagem\n-----\n\n")

        } catch (e: Exception) {
            println("Erro ${e.message}")
        } finally {
            if (publicador != null) {
                publicador.shutdown()
                publicador.awaitTermination(1, TimeUnit.MINUTES)
            }
        }
    }
}