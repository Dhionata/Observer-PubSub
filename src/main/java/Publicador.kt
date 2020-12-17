import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import com.google.pubsub.v1.TopicName
import java.util.concurrent.TimeUnit

object Publicador {

    fun publicar(projetoID: String, topicoID: String) {
        //val projetoID = projetoID//supergrupo
        //val topicoID = topicoID//tópico
        publisherExample(projetoID, topicoID)
    }

    private fun publisherExample(projetoID: String?, topicoID: String?) {
        val nomeDoTopico = TopicName.of(projetoID, topicoID)
        var publicador: Publisher? = null
        try {
            publicador = Publisher.newBuilder(nomeDoTopico).build()
            repeat(10) {
                //mensagem
                val mensagem = "Mensagem número $it"
                //publicar mensagem
                val menssagemFormatada =
                    publicador.publish(PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(mensagem)).build())
                println("Published message ID: ${menssagemFormatada.get()}")
                println("Published message : $mensagem\n-----\n\n")
            }
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